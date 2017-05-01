package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */
public class KeyVal {
    private String key;
    private int count1;
    private int count2;

    private KeyVal next;

    public KeyVal(String k, int c1, int c2) {
        this.key = k;
        this.count1 = c1;
        this.count2 = c2;
    }

    /**
     *
     * @return gets the key
     */

    public String getKey() {
        return key;
    }

    /**
     *
     * @return gets the next KeyVal for the CLinkedList
     */

    public KeyVal getNext() {
        return next;
    }

    /**
     *
     * @param next sets the next KeyVal for the CLinkedList
     */

    public void setNext(KeyVal next) {
        this.next = next;
    }

    /**
     * Adds to the count of the word
     */

    public void add(int c1, int c2){
        this.count1 += c1;
        this.count2 += c2;
    }

    /**
     *
     * @return the amount of the count
     */

    public int getCount1(){
        return count1;
    }

    public int getCount2(){
        return count2;
    }

    public void setCount1(int c){
        this.count1 = c;
    }

    public void setCount2(int c){
        this.count2 = c;
    }


    /**
     *
     * @return custom toString method for GUI
     */

    @Override
    public String toString() {
        return key;
    }
}