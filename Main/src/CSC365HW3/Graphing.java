package CSC365HW3;

import java.io.*;
import java.util.*;

/**
 * Created by landon on 5/7/17.
 */
class Graphing {

    private HashMap<String, Page> pages;
    private CompareWikiPages comparer = new CompareWikiPages();
    private HashMap<String, WikiPage> wikiPages;
    private ArrayList<String> list;
    private Set<String> pageChecker;

    Graphing() {
        this.pages = new HashMap<>();
        this.wikiPages = new HashMap<>();
        this.list = new ArrayList<>();
        this.pageChecker = new HashSet<>();
    }

    /**
     * Adds all the Wikipages from an ArrayList to the Graph
     * @param wlist ArrayList<WikiPage></>
     */

    void addAll(ArrayList<WikiPage> wlist){
        wlist.forEach(w ->{
            if(!pageChecker.contains(w.getTitle())){
                pageChecker.add(w.getTitle());
                list.add(w.getTitle());
            }

            wikiPages.put(w.getTitle(), w);
            if(w.getParent() != null) {
                addPage(w.getTitle());
                addPage(w.getParent().getTitle());
                addEdge(w.getParent().getTitle(), w.getTitle(), comparer.compare(w.getParent(), w));
            }
        });
    }

    /**
     * Adds page to HashMap
     * @param title title of page
     */

    private void addPage(String title){
        if(!pages.containsKey(title)){
            pages.put(title, new Page(title));
        }
    }

    /**
     *Creates an edge between two pages by making sure they both exist and then adding p2 to the neightbors of p1 and also the weight between them
     * @param p1 page 1
     * @param p2 page 2
     * @param weight weight
     */
    private void addEdge(String p1, String p2, double weight){
        if(!pages.containsKey(p1) && !pages.containsKey(p2)){
            System.out.println("Both Pages need to exist to create an edge between.");
            return;
        }
        pages.get(p1).children.add(new Edge(pages.get(p2), weight));
        pages.get(p2).children.add(new Edge(pages.get(p1), weight));
    }

    /**
     * Class to represent a single page
     */

    private static class Page implements Serializable{
        private final String title;
        private Page previousPage;
        private double distance;
        private List<Edge> children;

        public Page(String title){
            this.title = title;
            this.previousPage = null;
            this.distance = Integer.MAX_VALUE;
            children = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Page{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    /**
     * Represents the distance between two different pages.  The source point will have a list of edges to destination pages.
     */

    private static class Edge implements Serializable{
        private Page dest;
        private double weight;

        Edge(Page dest, double weight){
            this.dest = dest;
            this.weight = weight;
        }
    }

    /**
     *
     * @return whether or not the serialized file already exists
     */

    boolean checkSerializedExists() {
        File checkPages = new File("CachedPages.ser");

        if(checkPages.exists()){
            System.out.println("Cache of pages exists!");
            readPages();
            return true;
        }
        System.out.println("Cache of pages does not exist");
        return false;
    }

    /**
     * Serializes the hashmap of pages
     */

    void serializePages(){

        try{
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("CachedPages.ser"));
            o.writeObject(pages);
            o.writeObject(wikiPages);
            o.writeObject(list);
            o.close();
        } catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     *
     * @return the read file and a hashmap that is recreated from the serialized file
     */

    @SuppressWarnings("unchecked") // Don't like this :(
    void readPages(){
        try {
            ObjectInputStream i = new ObjectInputStream(new FileInputStream("CachedPages.ser"));
            pages = (HashMap<String, Page>) i.readObject();
            wikiPages = (HashMap<String, WikiPage>) i.readObject();
            list = (ArrayList<String>) i.readObject();
            i.close();
        } catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * Kruskal's algorithm to find a MST
     * @return weight of MST
     */

    double kruskal(){
        Queue<Page> notVisited = new PriorityQueue<>(pages.size(), Comparator.comparingDouble(o -> o.distance));
        notVisited.addAll(pages.values());
        Page current = notVisited.poll();
        Set<String> visited = new HashSet<>();
        double MST = 0;
        while(notVisited.size() != 0) {
            for (Edge e : current.children) {
                if (!visited.contains(e.dest.title)) {
                    visited.add(e.dest.title);
                    MST += e.weight;
                }
            }
            current = notVisited.poll();
        }
        return MST;
    }

    ArrayList<String> getList(){
        return list;
    }



    /**
     * Method to find the path between two pages using Graphing's Algorithm on the graph created from all the wikipages
     * @param source source page
     * @param destination destination page
     */

    ArrayList<String> dijkstra(String source, String destination){
        ArrayList<String> pather = new ArrayList<>();
        //Priority Queue that sorts based on the known page distances
        Queue<Page> notVisited = new PriorityQueue<>(pages.size(), Comparator.comparingDouble(o -> o.distance));

        //Now add all the pages to the queue while they all have a distance of Integer.MAX_VALUE
        notVisited.addAll(pages.values());

        Page current = pages.get(source);
        Page dest = pages.get(destination);

        current.distance = 0;
        notVisited.remove(current);

        while (true){
            for(Edge e: current.children){
                final double newDistance = current.distance + e.weight;
                final double oldDistance = e.dest.distance;
                if(newDistance < oldDistance){
                    e.dest.distance = newDistance;
                    e.dest.previousPage = current;

                    //Remove then re-add Page once distance is updated
                    notVisited.remove(e.dest);
                    notVisited.add(e.dest);
                }
            }

            if(notVisited.size() == 0 || notVisited.peek().distance == Integer.MAX_VALUE){
                System.out.println("Reached the end!");
                break;
            }

            current = notVisited.poll();
            if(current == dest){
                System.out.println("Found Destination!");
                break;
            }

        }

        //Create the path using a Stack

        Stack<String> path = new Stack<>();

        current = dest;
        while(true){
            if(current == null){
                break;
            }
            path.push(current.title + " " + current.distance);
            current = current.previousPage;
        }

        System.out.println("Showing Path:");
        System.out.println("Path length: " + dest.distance);
        while (!path.isEmpty()){
            String partPath = path.pop();
            System.out.println(partPath);
            pather.add(partPath);
        }

        return pather;
    }

    /**
     *
     * @param src source pages
     * @param destination destination page
     * @return array of source and destination most similar pages
     */

    String[] getMostSimilar(String src, String destination){
        String[] sims = {src, destination};
        String[] ans = new String[2];
        for(int i = 0; i < 2; i++) {
            double similar = 0.0;
            for (WikiPage dest : wikiPages.values()) {
                double compared = comparer.compare(wikiPages.get(sims[i]), dest);
                if (compared > similar && !src.equals(dest.getTitle())) {
                    similar = compared;
                    ans[i] = dest.getTitle();
                }
            }
        }

        return ans;
    }

}
