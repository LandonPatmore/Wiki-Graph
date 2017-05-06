package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */
class WordCount {
    private String key;
    private double p1;
    private double p2;

    private WordCount next;

    WordCount(String k, double p1, double p2) {
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
     * @return gets the next WordCount for the CLinkedList
     */

    WordCount getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next WordCount for the CLinkedList
     */

    void setNext(WordCount next) {
        this.next = next;
    }

    /**
     * Adds to the count of the word
     */

    void add(double p1, double p2){
        this.p1 += p1;
        this.p2 += p2;
    }

    double getP1(){
        return p1;
    }

    double getP2(){
        return p2;
    }
}