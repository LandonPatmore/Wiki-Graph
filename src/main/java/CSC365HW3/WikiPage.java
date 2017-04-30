package CSC365HW3;

/**
 * Created by landon on 4/29/17.
 */
public class WikiPage {
    private String URL;
    private String title;
    private String words;
    private WikiPage[] children;


    public WikiPage(String u, String t, String w, WikiPage[] c){
        this.URL = u;
        this.title = t;
        this.words = w;
        this.children = c;
    }

    public String getURL() {
        return URL;
    }

    public String getTitle() {
        return title;
    }

    public String getWords() {
        return words;
    }

    public WikiPage[] getChildren() {
        return children;
    }
}
