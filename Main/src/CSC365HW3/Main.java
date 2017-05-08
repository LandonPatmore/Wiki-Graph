package CSC365HW3;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) throws Exception {
        Dijkstra dj = new Dijkstra();
        if(!dj.checkSerializedExists()) {
            MostCommonWords.INSTANCE.readCommonWords();
            DataPuller d = new DataPuller("https://en.wikipedia.org/wiki/Formula_One");
            ArrayList<WikiPage> w = d.grabData();
            CompareWikiPages c = new CompareWikiPages();

            w.forEach(page -> {
                if (page.getParent() != null) {
                    WikiPage parent = page.getParent();

                    dj.addPage(page.getTitle());
                    dj.addPage(parent.getTitle());
                    dj.addEdge(parent.getTitle(), page.getTitle(), c.compare(parent, page));
                }
            });

            dj.serializePages();
        }
        System.out.println();
        dj.pathFinder("Formula One", "Gasoline");




    }
}
