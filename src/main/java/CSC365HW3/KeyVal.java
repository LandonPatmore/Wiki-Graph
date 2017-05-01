package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */
public class KeyVal {
    private String key;
    private int count;

    private KeyVal next;

    /**
     *
     * @param k key string
     * @param c count value
     */
    public KeyVal(String k, int c) {
        key = k;
        count = c;
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

    public void add(){
        this.count++;
    }

    /**
     *
     * @return the amount of the count
     */

    public int getCount(){
        return count;
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