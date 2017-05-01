package CSC365HW3;

/**
 * Created by landon on 4/30/17.
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * Custom HashTable Implementation
 */
public class HashTable {
    private KeyVal[] HT;
    private int tableSize = 10000;
    private int count = 0;

    /**
     * creates a new Array of CLinkedLists
     */

    public HashTable() {
        HT = new KeyVal[nextPrime(tableSize)];
    }

    /**
     * Puts a KeyVal into the HashTable and will automatically turn collisions into a CLinkedList
     * @param keyVal takes a KeyVal to put into the HashTable
     */

    public void put(KeyVal keyVal) {
        Hashing h = new Hashing(keyVal.getKey(), keyVal.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            HT[hash] = keyVal;
            count++;
        } else if (!indexEmpty(hash) && !HT[hash].getKey().equals(keyVal.getKey())) {
            HT[hash].setNext(keyVal);
            count++;
        } else if(get(keyVal)){
            HT[hash].add(keyVal.getCount1(), keyVal.getCount2());
        }

        if((float)(count / tableSize) > 0.66){
            resize();
        }
    }

    /**
     *
     * @param k takes a KeyVal and checks to see if the key is within the HashTable
     * @return either true or false
     */

    public boolean get(KeyVal k) {
        Hashing h = new Hashing(k.getKey(), k.getKey().length());
        int hash = h.hasher() % nextPrime(tableSize);

        if (indexEmpty(hash)) {
            return false;
        }

        KeyVal head = HT[hash];

        while (!indexEmpty(hash) && !head.getKey().equals(k.getKey())) {
            head = head.getNext();
        }

        return true;
    }

    public KeyVal[] exposeHT(){
        return HT;
    }

    private int size(){
        return HT.length;
    }

    public void mergeHashTables(HashTable h){
        for(int i = 0; i < h.size(); i++){
            if(h.exposeHT()[i] != null){
                h.exposeHT()[i].setCount2(h.exposeHT()[i].getCount1());
                h.exposeHT()[i].setCount1(0);
                this.put(h.exposeHT()[i]);
            }
        }
    }

    public ArrayList<KeyVal> toArrayList(){
        ArrayList<KeyVal> k = new ArrayList<KeyVal>();

        for(int i = 0; i < HT.length; i++){
            if(HT[i] != null){
                KeyVal kv = HT[i];
                k.add(kv);
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    k.add(kv);
                }
            }
        }


        return k;
    }



    /**
     * Testing method to see how the HashTable was placing KeyVals
     */

    public void displayHash(){
        for(int i = 0; i < HT.length; i++){
            if(HT[i] != null){
                System.out.print(i + " ");
                KeyVal kv = HT[i];
                System.out.print(kv.getKey() + " ");
                while(kv.getNext() != null){
                    kv = kv.getNext();
                    System.out.print(kv.getKey() + " ");
                }
                System.out.println();
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
        KeyVal[] old = HT;

        HT = new KeyVal[tableSize];
        count = 0;

        for(int i = 0; i < old.length; i++){
            if(old[i] != null){
                KeyVal kv = old[i];
                put(kv);

                while (kv.getNext() != null) {
                    kv = kv.getNext();
                    put(kv);
                }
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
