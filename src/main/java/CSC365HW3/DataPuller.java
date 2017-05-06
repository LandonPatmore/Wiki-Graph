package CSC365HW3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by landon on 4/29/17.
 */
class DataPuller {

    private Random R_G = new Random();
    private static final int AMOUNT_LINKS = 5;
    private static final int DEPTH = 3;
    private String START_URL;
    private int counter = 0;
    private ConcurrentLinkedQueue<WikiPage> WikiPagesList = new ConcurrentLinkedQueue<>();

    DataPuller(String start) throws Exception {
        this.START_URL = start;
    }

    private WikiPage pullData(String url, WikiPage parent) {
        WikiPage wp;
        try {

            String decodedURL = URLDecoder.decode(url, "UTF-8");
            Document doc = Jsoup.connect(decodedURL).get();
            Elements links = doc.select("a");
            Elements paragraphs = doc.select("p");
            Element t = doc.select("title").first();

            StringBuilder words = new StringBuilder();
            String title = t.text().replace(" - Wikipedia", "");

            paragraphs.forEach(e -> {
                words.append(e.text().toLowerCase());
            });

            wp = new WikiPage(url, title, parent);

            for (int i = 0; i < AMOUNT_LINKS; i++) {
                boolean properLink = false;
                while (!properLink) {
                    int rnd = R_G.nextInt(links.size());
                    String a = links.get(rnd).attr("href");
                    if (a.length() >= 5 && a.substring(0, 5).equals("/wiki") && containsChecker(a)) {
                        String BASE_URL = "https://en.wikipedia.org";
                        String decode = URLDecoder.decode(BASE_URL + a, "UTF-8");
                        wp.addChildren(decode);
                        properLink = true;
                    }
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

        } catch (Exception e) {
            System.out.println("URL ERROR");
            return null;
        }

        return wp;

    }

    ArrayList<WikiPage> grabData() throws Exception {
        WikiPagesList.add(pullData(START_URL, null));
        ArrayList<WikiPage> children = new ArrayList<>();
        for (String s : WikiPagesList.peek().getChildren()) {
            children.add(pullData(s, WikiPagesList.peek()));
        }
        WikiPagesList.addAll(children);
        ArrayList<RecursiveTask<Boolean>> list = new ArrayList<>();
        children.forEach(link -> {
            list.add(new RecursiveTask<Boolean>() {
                         @Override
                         protected Boolean compute() {
                             allWikiPages(link, DEPTH);
                             return true;
                         }
                     }
            );
            list.get(list.size() - 1).fork();
        });

        list.forEach(ForkJoinTask::join);
        ArrayList<WikiPage> pages = new ArrayList<>();
        pages.addAll(WikiPagesList);
        return pages;
    }

    private ConcurrentLinkedQueue<WikiPage> allWikiPages(WikiPage parent, int depth) {
        parent.setChildrenCreated();
        if (depth == 0) {
            return WikiPagesList;
        }

        ArrayList<WikiPage> children = new ArrayList<>();
        parent.getChildren().forEach(link -> {
            children.add(pullData(link, parent));
        });

        children.stream().filter(Objects::nonNull).forEach(WikiPagesList::add);

        children.stream().filter(Objects::nonNull).forEach(link -> allWikiPages(link, depth - 1));
        return WikiPagesList;
    }

    private boolean containsChecker(String a) {
        return !a.contains("Wikipedia") && !a.contains("File") && !a.contains("Help") && !a.contains("Portal") && !a.contains("Special") && !a.contains("Talk") && !a.contains("Category") && !a.contains("Template") && !a.contains("disambiguation");
    }


}
