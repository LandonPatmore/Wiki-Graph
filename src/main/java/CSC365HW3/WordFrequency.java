package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */
class WordFrequency {
    private String key;
    private double count1;
    private double count2;

    private WordFrequency next;

    WordFrequency(String k, double c1, double c2) {
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
     * @return gets the next WordFrequency for the CLinkedList
     */

    WordFrequency getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next WordFrequency for the CLinkedList
     */

    void setNext(WordFrequency next) {
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