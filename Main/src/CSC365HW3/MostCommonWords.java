package CSC365HW3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by landon on 5/2/17.
 */

/**
 * Custom class to read a file of the 100 most common words
 */
enum MostCommonWords {
    INSTANCE;

    private HashSet<String> commonWords= new HashSet<>();

    void readCommonWords() throws FileNotFoundException {
        try {
            BufferedReader b = new BufferedReader(new FileReader("commonWords.txt"));
            String currentLine;
            while ((currentLine = b.readLine()) != null){
                commonWords.add(currentLine);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    HashSet<String> getCommonWords(){
        return commonWords;
    }
}
