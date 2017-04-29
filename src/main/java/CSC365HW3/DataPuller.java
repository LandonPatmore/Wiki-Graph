package CSC365HW3;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by landon on 4/29/17.
 */
public class DataPuller {

    public DataPuller(){

    }

    public void pullData(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements link = doc.select("a");

        for(Element e : link){
            String a = e.attr("href");
            if(a.contains("/wiki")) {
                if(a.substring(0, 5).matches("/wiki")) {
                    if(!a.contains("Wikipedia") && !a.contains("File") && !a.contains("Help") && !a.contains("Portal") && !a.contains("Special") && !a.contains("Talk") && !a.contains("Category")) {
                        System.out.println(a);
                    }
                }
            }
        }


    }



}
