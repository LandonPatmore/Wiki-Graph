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

    public DataPuller() {
        R_G = new Random();
    }

    public WikiPage pullData(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a");
        ArrayList<String> test = new ArrayList<String>();

        Element title = doc.select("title").first();
        String t = title.text().replace(" - Wikipedia", "");

        for (Element e : links) {
            String a = e.attr("href");
            if (a.contains("/wiki")) {
                if (a.substring(0, 5).matches("/wiki")) {
                    if (!a.contains("Wikipedia") && !a.contains("File") && !a.contains("Help") && !a.contains("Portal") && !a.contains("Special") && !a.contains("Talk") && !a.contains("Category") && !a.contains("Template") && !a.contains("disambiguation")) {
                        w = new WikiPage(url, t, "");
                        test.add(a);
                    }
                }
            }
        }

        for(int i = 0; i < 4; i++){
            int rnd = R_G.nextInt(test.size());
            String decode = URLDecoder.decode(BASE_URL + test.get(rnd), "UTF-8");
            w.setChildren(decode);
        }

        return w;

    }


}
