package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 5/1/17.
 */
public class CompareWikiPages {

    private WikiPage page1;
    private WikiPage page2;
    private HashTable h;


    public CompareWikiPages(WikiPage w1, WikiPage w2){
        this.page1 = w1;
        this.page2 = w2;
    }

    public double compare(){
        h = page1.getVector();
        h.mergeHashTables(page2.getVector());
        ArrayList<KeyVal> k = h.toArrayList();

        int[] p1 = new int[k.size()];
        int[] p2 = new int[k.size()];

        for(int i = 0; i < p1.length; i++){
            p1[i] = k.get(i).getCount1();
            p2[i] = k.get(i).getCount2();
        }

        double dp = dotProduct(p1, p2);
        double magp1 = magnitude(p1);
        double magp2 = magnitude(p2);

        return dp/(magp1 * magp2);
    }

    private double dotProduct(int[] p1, int[] p2){
        double dp = 0;

        for(int i = 0; i < p1.length; i++){
            dp += (p1[i] * p2[i]);
        }

        return dp;
    }

    private double magnitude(int[] vector){
        return Math.sqrt(dotProduct(vector, vector));
    }
}
