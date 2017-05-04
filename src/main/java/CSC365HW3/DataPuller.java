package CSC365HW3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by landon on 4/29/17.
 */
class DataPuller {

    private Random R_G = new Random();
    private static final int AMOUNT_LINKS = 20;
    private static final int DEPTH = 10;
    private String START_URL;
    private int counter = 0;
    private ArrayList<WikiPage> w = new ArrayList<>();

    DataPuller(String start) throws Exception {
        this.START_URL = start;
    }

    private WikiPage pullData(String url, WikiPage parent, boolean outerChild) throws Exception {
        WikiPage wp = null;
        try {
            ArrayList<String> pageLinks = new ArrayList<String>();

            String decodedURL = URLDecoder.decode(url, "UTF-8");
            Document doc = Jsoup.connect(decodedURL).get();
            Elements links = doc.select("a");
            Elements paragraphs = doc.select("p");
            Element t = doc.select("title").first();

            StringBuilder words = new StringBuilder();
            String title = t.text().replace(" - Wikipedia", "");

            for (Element e : paragraphs) {
                words.append(e.text().toLowerCase());
            }

            for (Element e : links) {
                String a = e.attr("href");
                if (a.length() >= 5 && a.substring(0, 5).equals("/wiki") && containsChecker(a)) {
                    if (!outerChild)
                        pageLinks.add(a);
                }
            }

            wp = new WikiPage(url, title, parent);
            if (!outerChild) {
                for (int i = 0; i < AMOUNT_LINKS; i++) {
                    int rnd = R_G.nextInt(pageLinks.size());
                    String BASE_URL = "https://en.wikipedia.org";
                    String decode = URLDecoder.decode(BASE_URL + pageLinks.get(rnd), "UTF-8");
                    wp.setChildren(decode);
                }
            }

            String[] splitWords = words.toString().replaceAll("[_$&+,:;=?@#|'<>.^*()%!\\[\\]\\-\"/{}]", " ").split(" ");
            for (String s : splitWords) {
                if (s.length() >= 1) {
                    wp.addToWords(new WordCount(s, 1, 0));
                }
            }

            System.out.printf("%1$-10s %2$-45s\n", counter, title);
            counter++;

        } catch (SocketException e){
            System.out.println("WIKI KICKED US OFF :( CONTINUE ANYWAYS! ->");
        } catch (Exception e) {
            System.out.println("URL ERROR");
        }

        return wp;

    }

    ArrayList<WikiPage> grabData() throws Exception {
        return allWikiPages(0, START_URL, null, DEPTH);
    }

    private ArrayList<WikiPage> allWikiPages(int i ,String page, WikiPage parent, int depth) throws Exception {
        if(depth == 0){
            for(int j =0; j < w.size(); j++){
                if(!w.get(j).childrenCreated() && !w.get(j).noChildren()){
                    for(String s : w.get(j).getChildren()){
                        w.add(pullData(s, w.get(j), true));
                    }
                    w.get(j).setChildrenCreated();
                }
            }
            System.out.println("Done");
            return w;
        }

        if(i == 0){
            w.add(pullData(page, parent, false));
        }
        for(String s : w.get(i).getChildren()){
            w.add(pullData(s, w.get(i), false));
        }
        w.get(i).setChildrenCreated();
        i++;

        return allWikiPages(i, w.get(i).getURL(), w.get(i), depth - 1);
    }

    void toDOTFile() throws IOException {
        RandomAccessFile f = new RandomAccessFile("test.dot", "rw");
        f.setLength(0);
        f.writeBytes("digraph G{\n");
        Set<String> t = new HashSet<>();
        w.forEach(s -> {
            if (s.getParent() != null) {
                try {
                    if(!t.contains(s.getParent().getTitle() + "><" + s.getTitle())) {
                        t.add(s.getParent().getTitle() + "><" + s.getTitle());
                        f.writeBytes("\"" + s.getParent().getTitle() + "\" -> \"" + s.getTitle() + "\";\n");
                    }

                } catch (IOException e) { e.printStackTrace(); }
            }
        });
        f.writeBytes("}");
    }

    private void debugPages() {
        for (int i = 0; i < w.size(); i++) {
            WikiPage s = w.get(i);
            if (s.getParent() != null) {
                System.out.printf("%1$-10s %2$-45s %3$-45s\n", i, s.getParent().getTitle(), s.getTitle());
            }
        }
    }

    private boolean containsChecker(String a) {
        return !a.contains("Wikipedia") && !a.contains("File") && !a.contains("Help") && !a.contains("Portal") && !a.contains("Special") && !a.contains("Talk") && !a.contains("Category") && !a.contains("Template") && !a.contains("disambiguation");
    }


}
