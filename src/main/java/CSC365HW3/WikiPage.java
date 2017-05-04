package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 4/29/17.
 */
class WikiPage{
    private String URL;
    private String title;
    private ArrayList<String> children;
    private int amountChildren;
    private WikiPage parent;
    private boolean childrenCreated;
    private HashTable words;


    WikiPage(String u, String t, WikiPage p){
        this.URL = u;
        this.title = t;
        this.children = new ArrayList<>();
        this.amountChildren = 0;
        this.parent = p;
        this.childrenCreated = false;
        this.words = new HashTable();
    }

    String getURL() {
        return URL;
    }

    String getTitle() {
        return title;
    }

    void addToWords(WordCount w){
        words.put(w);
    }

    HashTable getWords(){
        return words;
    }

    void setChildren(String u){
        children.add(u);
        amountChildren++;
    }

    ArrayList<String> getChildren() {
        return children;
    }

    boolean noChildren(){
        return amountChildren == 0;
    }

    WikiPage getParent(){
        return parent;
    }

    boolean childrenCreated() {
        return childrenCreated;
    }

    void setChildrenCreated() {
        this.childrenCreated = true;
    }
}
