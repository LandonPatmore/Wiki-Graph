package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */

import java.util.ArrayList;

/**
 * Custom HashTable Implementation
 */
class HashTable {
    private WordFrequency[] HT;
    private int tableSize = 10000;
    private int count = 0;

    /**
     * creates a new Array of CLinkedLists
     */

    HashTable() {
        HT = new WordFrequency[nextPrime(tableSize)];
    }

    /**
     * Puts a WordFrequency into the HashTable and will automatically turn collisions into a CLinkedList
     * @param wordFrequency takes a WordFrequency to put into the HashTable
     */

    void put(WordFrequency wordFrequency) {
        Hashing h = new Hashing(wordFrequency.getKey(), wordFrequency.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            HT[hash] = wordFrequency;
            count++;
        } else if (!indexEmpty(hash) && !HT[hash].getKey().equals(wordFrequency.getKey())) {
            HT[hash].setNext(wordFrequency);
            count++;
        } else if(get(wordFrequency)){
            HT[hash].add(wordFrequency.p1Count(), wordFrequency.p2Count());
        }

        if((float)(count / tableSize) > 0.66){
            resize();
        }
    }

    /**
     *
     * @param k takes a WordFrequency and checks to see if the key is within the HashTable
     * @return either true or false
     */

    boolean get(WordFrequency k) {
        Hashing h = new Hashing(k.getKey(), k.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            return false;
        }

        WordFrequency head = HT[hash];

        while (!indexEmpty(hash) && !head.getKey().equals(k.getKey())) {
            head = head.getNext();
        }

        return true;
    }

    private WordFrequency[] exposeHT(){
        return HT;
    }

    int length(){
        return count;
    }

    void mergeHashTables(HashTable h){
        for(WordFrequency w : h.exposeHT()){
            while(w != null){
                w.setCount2(w.p1Count());
                w.zeroCount1();
                this.put(w);
                w = w.getNext();
            }
        }
    }

    ArrayList<WordFrequency> toArrayList(){
        ArrayList<WordFrequency> k = new ArrayList<WordFrequency>();

        for(WordFrequency w : HT){
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
        for(WordFrequency w : HT){
            while (w != null){
                System.out.printf("%1$-45s %2$-45s %3$-45s\n",w.getKey(), w.p1Count(), w.p2Count());
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
        WordFrequency[] old = HT;

        HT = new WordFrequency[tableSize];
        count = 0;

        for(WordFrequency w : old){
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
