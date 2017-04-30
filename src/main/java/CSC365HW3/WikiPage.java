package CSC365HW3;

/**
 * Created by landon on 4/29/17.
 */
public class WikiPage {
    private String URL;
    private String title;
    private String words;
    private String[] children;
    private int amountChildren;
    private WikiPage parent;
    private boolean seen;
    private boolean childrenCreated;


    public WikiPage(String u, String t, String w, WikiPage p){
        this.URL = u;
        this.title = t;
        this.words = w;
        this.children = new String[4];
        this.amountChildren = 0;
        this.parent = p;
        this.seen = false;
        this.childrenCreated = false;
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

    public void setChildren(String u){
        children[amountChildren] = u;
        amountChildren++;
    }

    public String[] getChildren() {
        return children;
    }

    public WikiPage getParent(){
        return parent;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isChildrenCreated() {
        return childrenCreated;
    }

    public void setChildrenCreated(boolean childrenCreated) {
        this.childrenCreated = childrenCreated;
    }
}
