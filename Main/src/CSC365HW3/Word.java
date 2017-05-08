package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */

/**
 * Custom class to represent a word
 */
class Word {
    private String key;
    private double p1;
    private double p2;

    private Word next;

    Word(String k, double p1, double p2) {
        this.key = k;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     *
     * @return gets the key
     */

    String getKey() {
        return key;
    }

    /**
     *
     * @return gets the next Word for the linked list
     */

    Word getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next Word for the linked list
     */

    void setNext(Word next) {
        this.next = next;
    }

    /**
     * Adds to the count of the word
     */

    void add(double p1, double p2){
        this.p1 += p1;
        this.p2 += p2;
    }

    /**
     *
     * @return page 1 count
     */

    double getP1(){
        return p1;
    }

    /**
     *
     * @return page 2 count
     */

    double getP2(){
        return p2;
    }
}