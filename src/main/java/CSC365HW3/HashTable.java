package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Custom HashTable Implementation
 */
class HashTable {
    private WordCount[] HT;
    private int tableSize = 10000;
    private int count = 0;

    /**
     * creates a new Array of CLinkedLists
     */

    HashTable() {
        HT = new WordCount[nextPrime(tableSize)];
    }

    /**
     * Puts a WordCount into the HashTable and will automatically turn collisions into a CLinkedList
     * @param wordCount takes a WordCount to put into the HashTable
     */

    void put(WordCount wordCount) {
        int hash = hHasher(wordCount);

        if(!MostCommonWords.INSTANCE.getCommonWords().contains(wordCount.getKey())) {
            if (indexEmpty(hash)) {
                HT[hash] = wordCount;
                count++;
            } else if (!indexEmpty(hash) && !HT[hash].getKey().equals(wordCount.getKey())) {
                HT[hash].setNext(wordCount);
                count++;
            } else if (get(wordCount)) {
                HT[hash].add(wordCount.getP1(), wordCount.getP2());
            }

            if ((float) (count / tableSize) > 0.66) {
                resize();
            }
        }
    }

    private int hHasher(WordCount w){
        Hashing h = new Hashing(w.getKey(), w.getKey().length());
        return h.hasher() % nextPrime(tableSize);
    }

    /**
     *
     * @param k takes a WordCount and checks to see if the key is within the HashTable
     * @return either true or false
     */

    private boolean get(WordCount k) {
        int hash = hHasher(k);

        if (indexEmpty(hash)) {
            return false;
        }

        WordCount head = HT[hash];

        while (!indexEmpty(hash) && !head.getKey().equals(k.getKey())) {
            head = head.getNext();
            if(head == null){
                return false;
            }
        }

        return true;
    }

    private WordCount[] exposeHT(){
        return HT;
    }

    int getCount(){
        return count;
    }

    void mergeHashTables(HashTable h){
        for(WordCount w : h.exposeHT()){
            while(w != null){
                this.put(new WordCount(w.getKey(), 0, w.getP1()));
                w = w.getNext();
            }
        }
    }

    void mergeArrayList(ArrayList<WordCount> w){
        w.forEach(this::put);
    }

    ArrayList<WordCount> toArrayList(){
        ArrayList<WordCount> k = new ArrayList<>();

        for(WordCount w : HT){
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
        for(WordCount w : HT){
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
        WordCount[] old = HT;

        HT = new WordCount[tableSize];
        count = 0;

        for(WordCount w : old){
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
