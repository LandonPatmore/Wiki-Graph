package CSC365HW3;

/**
 * Created by landon on 4/29/17.
 */
class WikiPage{
    private String URL;
    private String title;
    private String[] children;
    private int amountChildren;
    private WikiPage parent;
    private boolean seen;
    private boolean childrenCreated;
    private HashTable words;


    WikiPage(String u, String t, WikiPage p, int c){
        this.URL = u;
        this.title = t;
        this.children = new String[c];
        this.amountChildren = 0;
        this.parent = p;
        this.seen = false;
        this.childrenCreated = false;
        this.words = new HashTable();
    }

    String getURL() {
        return URL;
    }

    String getTitle() {
        return title;
    }

    void setWordsVector(String[] w){
        for (String s : w) {
            if (!(s.length() <= 1)) {
                words.put(new WordFrequency(s, 1, 0));
            }
        }
    }

    HashTable getWords(){
        return words;
    }

    void setChildren(String u){
        children[amountChildren] = u;
        amountChildren++;
    }

    String[] getChildren() {
        return children;
    }

    boolean noChildren(){
        return amountChildren == 0;
    }

    WikiPage getParent(){
        return parent;
    }

    boolean isSeen() {
        return seen;
    }

    void setSeen(boolean seen) {
        this.seen = seen;
    }

    boolean areChildrenCreated() {
        return childrenCreated;
    }

    void setChildrenCreated() {
        this.childrenCreated = true;
    }
}
