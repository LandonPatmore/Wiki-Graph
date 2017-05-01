package CSC365HW3;

/**
 * Created by landon on 4/29/17.
 */
public class WikiPage{
    private String URL;
    private String title;
    private String[] children;
    private int amountChildren;
    private WikiPage parent;
    private boolean seen;
    private boolean childrenCreated;
    private HashTable vector;


    public WikiPage(String u, String t, WikiPage p){
        this.URL = u;
        this.title = t;
        this.children = new String[4];
        this.amountChildren = 0;
        this.parent = p;
        this.seen = false;
        this.childrenCreated = false;
        this.vector = new HashTable();
    }

    public String getURL() {
        return URL;
    }

    public String getTitle() {
        return title;
    }

    public void setWordsVector(String[] w){
        for(int i = 0; i < w.length; i++){
            if(!(w[i].length() <=1)) {
                vector.put(new KeyVal(w[i], 1, 0));
            }
        }
    }

    public HashTable getVector(){
        return vector;
    }

    public void setChildren(String u){
        children[amountChildren] = u;
        amountChildren++;
    }

    public String[] getChildren() {
        return children;
    }

    public boolean noChildren(){
        return amountChildren == 0;
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
