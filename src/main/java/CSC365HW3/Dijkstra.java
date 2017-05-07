package CSC365HW3;

import java.util.*;

/**
 * Created by landon on 5/7/17.
 */
public class Dijkstra {

    private HashMap<String, Page> pages;

    public Dijkstra(){
        pages = new HashMap<>();
    }

    //Method to add page to the hashmap
    public void addPage(String title){
        if(pages.containsKey(title)){
            System.out.println("Page already exists!");
            return;
        }
        pages.put(title, new Page(title));
    }

    /**
     *Creates an edge between two pages by making sure they both exist and then adding p2 to the neightbors of p1 and also the weight between them
     * @param p1 page 1
     * @param p2 page 2
     * @param weight weight
     */
    public void addEdge(String p1, String p2, int weight){
        if(!pages.containsKey(p1) && !pages.containsKey(p2)){
            System.out.println("Both Pages need to exist to create an edge between.");
            return;
        }
        pages.get(p1).neighborPages.add(new Edge(pages.get(p2), weight));
        pages.get(p2).neighborPages.add(new Edge(pages.get(p1), weight));
    }

    //Singular Point on the graph that represents a single Wikipedia page
    public static class Page{
        private final String title;
        private Page previousPage;
        private int distance;
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
     * Represents the distance between two different pages.  The source point will have the edge list, so there is no point in requiring the edge class to have a list of edges.
     */

    public static class Edge{
        private Page dest;
        private int weight;

        public Edge(Page dest, int weight){
            this.dest = dest;
            this.weight = weight;
        }
    }

    public void pathFinder(String source, String destination){

        //Priority Queue that sorts based on the known page distances
        Queue<Page> notVisited = new PriorityQueue<>(pages.size(), Comparator.comparingInt(o -> o.distance));

        //Now add all the pages to the queue while they all have a distance of Integer.MAX_VALUE
        notVisited.addAll(pages.values());

        Page current = pages.get(source);
        Page dest = pages.get(destination);

        current.distance = 0;
        notVisited.remove(current);

        while (true){
            for(Edge e: current.neighborPages){
                final int newDistance = current.distance + e.weight;
                final int oldDistance = e.dest.distance;
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
