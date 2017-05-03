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
    private boolean seen;
    private boolean childrenCreated;
    private HashTable words;


    WikiPage(String u, String t, WikiPage p){
        this.URL = u;
        this.title = t;
        this.children = new ArrayList<String>();
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

    void addToWords(WordCount w){
        words.put(w);
    }

    HashTable getWords(){
        return words;
    }

    void setChildren(String u){
        if(!this.title.equalsIgnoreCase(u) && !children.contains(u)) { //makes sure that the pages added aren't the parent and also not already inside the children list
            children.add(u);
            amountChildren++;
        }
    }

    void showChildren(){
        for(String s : children){
            System.out.println(s);
        }
    }

    ArrayList<String> getChildren() {
        return children;
    }

    boolean noChildren(){
        return amountChildren == 0;
    }

    int getAmountChildren(){
        return amountChildren;
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

    boolean childrenCreated() {
        return childrenCreated;
    }

    void setChildrenCreated() {
        this.childrenCreated = true;
    }
}
