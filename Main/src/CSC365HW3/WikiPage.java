package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 4/29/17.
 */

/**
 * Custom class to represent a wikipage
 */
class WikiPage{
    private String title;
    private ArrayList<String> children;
    private WikiPage parent;
    private HashTable words;
    private int amountOfWords;


    WikiPage(String t, WikiPage p){
        this.title = t;
        this.children = new ArrayList<>();
        this.parent = p;
        this.words = new HashTable();
        this.amountOfWords = 0;
    }

    /**
     *
     * @return the title of the wikipage
     */

    String getTitle() {
        return title;
    }

    /**
     *
     * @param w word to be added to a hashtable of words
     */

    void addToWords(Word w){
        words.put(w);
        this.amountOfWords++;
    }

    /**
     *
     * @return hashtable of words
     */

    HashTable getWords(){
        return words;
    }

    /**
     *
     * @param u child url to be added
     */

    void addChildren(String u){
        children.add(u);
    }

    /**
     *
     * @return the ArrayList of children URLS
     */

    ArrayList<String> getChildren() {
        return children;
    }

    /**
     *
     * @return the parent wikipage of this wikipage
     */

    WikiPage getParent(){
        return parent;
    }

    /**
     *
     * @return the amount of words inside the wikipage
     */

    int getAmountOfWords(){
        return amountOfWords;
    }
}
