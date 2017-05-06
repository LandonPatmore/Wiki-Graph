package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 4/29/17.
 */
class WikiPage{
    private String URL;
    private String title;
    private ArrayList<String> children;
    private WikiPage parent;
    private HashTable words;
    private boolean childrenCreated;
    private int amountChildren;
    private int amountOfWords;


    WikiPage(String u, String t, WikiPage p){
        this.URL = u;
        this.title = t;
        this.children = new ArrayList<>();
        this.parent = p;
        this.words = new HashTable();
        this.amountChildren = 0;
        this.amountOfWords = 0;
        this.childrenCreated = false;
    }

    String getTitle() {
        return title;
    }

    void addToWords(WordCount w){
        words.put(w);
        this.amountOfWords++;
    }

    HashTable getWords(){
        return words;
    }

    void addChildren(String u){
        children.add(u);
        amountChildren++;
    }

    ArrayList<String> getChildren() {
        return children;
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

    boolean noChildren(){
        return amountChildren == 0;
    }

    int getAmountOfWords(){
        return amountOfWords;
    }
}
