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
public class DataPuller {

    private String BASE_URL = "https://en.wikipedia.org";
    private Random R_G;
    private WikiPage w;
    private ArrayList<String> pageLinks;

    public DataPuller() {
        R_G = new Random();
        pageLinks = new ArrayList<String>();
    }

    public WikiPage pullData(String url, WikiPage parent, boolean dangler) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a");

        Elements paragraphs = doc.select("p");
        StringBuilder words = new StringBuilder();

        Element title = doc.select("title").first();
        String t = title.text().replace(" - Wikipedia", "");

        for(Element e: paragraphs){
            words.append(e.text().toLowerCase());
        }

        String[] splitWords = words.toString().replaceAll("[_$&+,:;=?@#|'<>.^*()%!\\[\\]\\-\"/{}]", " ").split(" ");

        for (Element e : links) {
            String a = e.attr("href");
            if (a.length() >= 5) {
                if (a.substring(0, 5).equals("/wiki")) {
                    if (!a.contains("Wikipedia") && !a.contains("File") && !a.contains("Help") && !a.contains("Portal") && !a.contains("Special") && !a.contains("Talk") && !a.contains("Category") && !a.contains("Template") && !a.contains("disambiguation")) {
                        w = new WikiPage(url, t, parent);
                        if(!dangler) {
                            pageLinks.add(a);
                        }
                    }
                }
            }
        }

        if(!dangler) {
            for (int i = 0; i < 4; i++) {
                int rnd = R_G.nextInt(pageLinks.size());
                String decode = URLDecoder.decode(BASE_URL + pageLinks.get(rnd), "UTF-8");
                w.setChildren(decode);
            }
        }

        w.setWordsVector(splitWords);

        return w;

    }


}
