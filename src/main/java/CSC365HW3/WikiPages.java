package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 4/30/17.
 */
public class WikiPages {
    private ArrayList<WikiPage> w;

    public WikiPages(){
        w = new ArrayList<WikiPage>();
    }

    public void addWikiPage(WikiPage wiki){
        w.add(wiki);
    }

    public int size(){
        return w.size();
    }

    public ArrayList<WikiPage> expose(){
        return w;
    }

    public void showPageTitle(){
        for(int i = 0; i < w.size(); i++){
            System.out.println(w.get(i).getTitle());
        }
    }
}
