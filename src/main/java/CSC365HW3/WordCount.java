package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */
class WordCount {
    private String key;
    private double count1;
    private double count2;

    private WordCount next;

    WordCount(String k, double c1, double c2) {
        this.key = k;
        this.count1 = c1;
        this.count2 = c2;
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

    void add(double c1, double c2){
        this.count1 += c1;
        this.count2 += c2;
    }

    /**
     *
     * @return the amount of the count
     */

    double p1Count(){
        return count1;
    }

    double p2Count(){
        return count2;
    }

    void zeroCount1(){
        this.count1 = 0;
    }

    void setCount2(double c){
        this.count2 = c;
    }
}