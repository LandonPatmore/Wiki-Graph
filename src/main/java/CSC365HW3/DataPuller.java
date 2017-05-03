package CSC365HW3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by landon on 4/29/17.
 */
class DataPuller {

    private Random R_G;
    private int childrenAmount;

    DataPuller() {
        R_G = new Random();
        this.childrenAmount = 20;
    }

    private WikiPage pullData(String url, WikiPage parent, boolean dangler) throws IOException {
        ArrayList<String> pageLinks = new ArrayList<String>();

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a");

        Elements paragraphs = doc.select("p");
        StringBuilder words = new StringBuilder();

        Element title = doc.select("title").first();
        String t = title.text().replace(" - Wikipedia", "");

        for (Element e : paragraphs) {
            words.append(e.text().toLowerCase());
        }

        String[] splitWords = words.toString().replaceAll("[_$&+,:;=?@#|'<>.^*()%!\\[\\]\\-\"/{}]", " ").split(" ");
        for (Element e : links) {
            String a = e.attr("href");
            if (a.length() >= 5 && a.substring(0, 5).equals("/wiki") && containsChecker(a) && !dangler) {
                pageLinks.add(a);
            }
        }

        WikiPage w = new WikiPage(url, t, parent);
        if (!dangler) {
            for (int i = 0; i < childrenAmount; i++) {
                int rnd = R_G.nextInt(pageLinks.size());
                String BASE_URL = "https://en.wikipedia.org";
                String decode = URLDecoder.decode(BASE_URL + pageLinks.get(rnd), "UTF-8");
                w.setChildren(decode);
            }
        }

        for (String s : splitWords) {
            if (s.length() >= 1) {
                w.addToWords(new WordCount(s, 1, 0));
            }
        }

        return w;

    }

    ArrayList<WikiPage> allWikiPages(String startingPage) throws IOException {
        double cTime = System.nanoTime();
        ArrayList<WikiPage> w = new ArrayList<WikiPage>();

        w.add(pullData(startingPage, null, false));
        try {
            for (int i = 0; i < 1; i++) {
                WikiPage wp = w.get(i);
                for (String child : wp.getChildren()) {
                    w.add(pullData(child, wp, false));
                }
                wp.setChildrenCreated();
            }
            for (int i = 0; i < w.size(); i++) {
                WikiPage wp = w.get(i);
                if (!wp.noChildren() && !wp.childrenCreated()) {
                    for (String child : wp.getChildren()) {
                        w.add(pullData(child, wp, true));
                    }
                    wp.setChildrenCreated();
                }
            }

            debugPages(w);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double seconds = ((System.nanoTime() - cTime) / 1000000) / 1000;
        System.out.println("Done grabbing WikiPages in: " + seconds + " seconds.");
        return w;
    }

    private void debugPages(ArrayList<WikiPage> w) {
        System.out.printf("\n%1$-10s %2$-45s %3$-45s\n---------------------------------------------------------------------------------------\n", "Index", "Parent Node", "Child Node");
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
