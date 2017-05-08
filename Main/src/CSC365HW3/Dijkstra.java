package CSC365HW3;

import java.io.*;
import java.util.*;

/**
 * Created by landon on 5/7/17.
 */
class Dijkstra {

    private HashMap<String, Page> pages;

    Dijkstra(){
        pages = new HashMap<>();
    }

    //Method to add page to the hashmap
    void addPage(String title){
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
    void addEdge(String p1, String p2, double weight){
        if(!pages.containsKey(p1) && !pages.containsKey(p2)){
            System.out.println("Both Pages need to exist to create an edge between.");
            return;
        }
        pages.get(p1).neighborPages.add(new Edge(pages.get(p2), weight));
        pages.get(p2).neighborPages.add(new Edge(pages.get(p1), weight));
    }

    /**
     * Class to represent a single page
     */

    private static class Page implements Serializable{
        private final String title;
        private Page previousPage;
        private double distance;
        private List<Edge> neighborPages;

        public Page(String title){
            this.title = title;
            this.previousPage = null;
            this.distance = Integer.MAX_VALUE;
            neighborPages = new ArrayList<>();
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
            pages = readPages();
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
    HashMap<String, Page> readPages(){
        try {
            ObjectInputStream i = new ObjectInputStream(new FileInputStream("CachedPages.ser"));
            HashMap<String, Page> h = (HashMap<String, Page>) i.readObject();
            i.close();
            return h;
        } catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to find the path between two pages using Dijkstra's Algorithm on the graph created from all the wikipages
     * @param source source page
     * @param destination destination page
     */

    void pathFinder(String source, String destination){

        if(!pages.containsKey(source) || !pages.containsKey(destination)){
            System.out.println("Source or Destination does not exist.");
            return;
        }

        //Priority Queue that sorts based on the known page distances
        Queue<Page> notVisited = new PriorityQueue<>(pages.size(), Comparator.comparingDouble(o -> o.distance));

        //Now add all the pages to the queue while they all have a distance of Integer.MAX_VALUE
        notVisited.addAll(pages.values());

        Page current = pages.get(source);
        Page dest = pages.get(destination);

        current.distance = 0;
        notVisited.remove(current);

        while (true){
            for(Edge e: current.neighborPages){
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
            path.push(current.title);
            current = current.previousPage;
        }

        System.out.println("Showing Path:");
        System.out.println("Path length: " + dest.distance);
        while (!path.isEmpty()){
            System.out.println(path.pop());
        }

    }

}
