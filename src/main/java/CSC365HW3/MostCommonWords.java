package CSC365HW3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by landon on 5/2/17.
 */
public enum MostCommonWords {
    INSTANCE;

    private String[] commonWords = new String[97];
    private int count = 0;

    void readCommonWords() throws FileNotFoundException {
        try {
            BufferedReader b = new BufferedReader(new FileReader("commonWords.txt"));
            String currentLine;
            while ((currentLine = b.readLine()) != null){
                add(currentLine);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void add(String s){
        commonWords[count] = s;
        count++;
    }

    String[] getCommonWords(){
        return commonWords;
    }
}
