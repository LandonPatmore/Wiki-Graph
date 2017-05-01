package CSC365HW3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by landon on 5/1/17.
 */
public enum MostCommonWords {
    INSTANCE;

    public void commonWords() throws IOException {
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Most_common_words_in_English").get();
        Elements links = doc.select("td");
        for(int i = 0; i < links.size(); i++){
            if(!(i == 0) && !(i == 41) && !(i == 82) && !(i == 123) && !(i == 164) && (i % 2 == 0)){
                System.out.println(links.get(i).text());
            }
        }
    }
}
