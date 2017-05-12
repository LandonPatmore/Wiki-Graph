package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Custom HashTable Implementation
 */
class HashTable implements Serializable{
    private Word[] HT;
    private int tableSize = 10000;
    private int count = 0;

    /**
     * creates a new Array of CLinkedLists
     */

    HashTable() {
        HT = new Word[nextPrime(tableSize)];
    }

    /**
     * Puts a Word into the HashTable and will automatically turn collisions into a CLinkedList
     * @param word takes a Word to put into the HashTable
     */

    void put(Word word) {
        int hash = hHasher(word);

        if(!MostCommonWords.INSTANCE.getCommonWords().contains(word.getKey())) {
            if (indexEmpty(hash)) {
                HT[hash] = word;
                count++;
            } else if (!indexEmpty(hash) && !HT[hash].getKey().equals(word.getKey())) {
                HT[hash].setNext(word);
                count++;
            } else if (get(word)) {
                HT[hash].add(word.getP1(), word.getP2());
            }

            if ((float) (count / tableSize) > 0.66) {
                resize();
            }
        }
    }

    /**
     *
     * @param w Word object
     * @return the hashvalue of the Word object
     */

    private int hHasher(Word w){
        Hashing h = new Hashing(w.getKey(), w.getKey().length());
        return h.hasher() % nextPrime(tableSize);
    }

    /**
     *
     * @param k takes a Word and checks to see if the key is within the HashTable
     * @return either true or false
     */

    private boolean get(Word k) {
        int hash = hHasher(k);

        if (indexEmpty(hash)) {
            return false;
        }

        Word head = HT[hash];

        while (!indexEmpty(hash) && !head.getKey().equals(k.getKey())) {
            head = head.getNext();
            if(head == null){
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return the internal array
     */

    private Word[] exposeHT(){
        return HT;
    }

    /**
     *
     * @param h another hashtable to be merged into the current hashtable and then creates a new wordcount for each word to be put into the array so when compared against another page, the word frequencies are not wrong
     */

    void mergeHashTables(HashTable h){
        for(Word w : h.exposeHT()){
            while(w != null){
                this.put(new Word(w.getKey(), 0, w.getP1()));
                w = w.getNext();
            }
        }
    }

    /**
     *
     * @param w Arraylist of words to be put into the hashtable
     */

    void addWordsFromArrayList(ArrayList<Word> w){
        w.forEach(this::put);
    }

    /**
     *
     * @return takes the hashtable and creates an Arraylist out of it
     */

    ArrayList<Word> toArrayList(){
        ArrayList<Word> k = new ArrayList<>();

        for(Word w : HT){
            while(w != null){
                k.add(w);
                w = w.getNext();
            }
        }

        return k;
    }

    /**
     * Testing method to see how the HashTable was placing KeyVals
     */

    void displayHash(){
        for(Word w : HT){
            while (w != null){
                System.out.printf("%1$-45s %2$-45s %3$-45s\n",w.getKey(), w.getP1(), w.getP2());
                w = w.getNext();
            }
        }
    }

    /**
     *
     * @param h takes the hashValue % tableSize
     * @return if the index is actually null or not
     */

    private boolean indexEmpty(int h) { //checks to see if the index is actually empty
        return HT[h] == null;
    }

    /**
     *
     * @param n takes an integer
     * @return if the requested integer is actually a prime or not
     */

    private boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param n takes an integer
     * @return the next possible prime
     */

    private int nextPrime(int n) {
        for (int i = n; true; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
    }

    /**
     * Auto resizes the HashTable when the HashTable is filled 0.75 or larger
     */

    private void resize(){
        tableSize = 2 * tableSize;
        tableSize = nextPrime(tableSize);
        Word[] old = HT;

        HT = new Word[tableSize];
        count = 0;

        for(Word w : old){
            while (w != null){
                put(w);
                w = w.getNext();
            }
        }
    }

    /**
     * Custom hashing class to hash the keys for placement in the HashTable
     */

    private class Hashing {
        private String hashableKey;
        private int hashed;
        private int length;

        /**
         *
         * @param hk a key
         * @param l the length of the key requested
         */

        private Hashing(String hk, int l) {
            hashableKey = hk;
            length = l;
        }

        /**
         * Jenkins One-At-A-Time Hash Function implementation
         * @return the hash value of the given key
         */
        private int hasher() {
            int i = 0;
            int hash = 0;
            while (i != length) {
                hash += hashableKey.charAt(i++);
                hash += hash << 10;
                hash ^= hash >> 6;
            }
            hash += hash << 3;
            hash ^= hash >> 11;
            hash += hash << 15;
            return getHash(hash);
        }

        /**
         *
         * @param h takes the hash value
         * @return the actual hash value by not allowing any negatives
         */

        private int getHash(int h) {
            hashed = (h & 0x7FFFFFFF);
            return hashed;
        }
    }
}
