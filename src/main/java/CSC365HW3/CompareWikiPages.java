package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 5/1/17.
 */
class CompareWikiPages {

    private WikiPage page1;
    private WikiPage page2;


    CompareWikiPages(WikiPage w1, WikiPage w2){
        this.page1 = w1;
        this.page2 = w2;
    }

    double compare(){
        HashTable wordFrequencies = page1.getWords();
        wordFrequencies.mergeHashTables(page2.getWords());
        ArrayList<WordFrequency> k = wordFrequencies.toArrayList();

        double[] p1 = new double[k.size()];
        double[] p2 = new double[k.size()];

        for(int i = 0; i < p1.length; i++){
            p1[i] = k.get(i).p1Count();
            p2[i] = k.get(i).p2Count();
        }

        return cosineSimilarity(p1, p2);
    }

    private double cosineSimilarity(double[] p1, double[] p2) {
        double dotProduct = 0.0;
        double magp1 = 0.0;
        double magp2 = 0.0;
        for (int i = 0; i < p1.length; i++) {
            dotProduct += p1[i] * p2[i];
            magp1 += Math.pow(p1[i], 2);
            magp2 += Math.pow(p2[i], 2);
        }
        return dotProduct / (Math.sqrt(magp1) * Math.sqrt(magp2));
    }
}
