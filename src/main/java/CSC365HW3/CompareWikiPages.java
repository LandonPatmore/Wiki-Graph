package CSC365HW3;

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



        return 0.0;
    }
}
