//package CSC365HW3;
//
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.*;
//
///**
// * Created by landon on 4/29/17.
// */
//class Graph {
//    private HashSet<Edge> graph;
//    private HashSet<String> destinations;
//    private HashMap<String, ArrayList<Edge>> map;
//
//    Graph(ArrayList<WikiPage> wikiPages){
//        this.graph = new HashSet<>();
//        this.destinations = new HashSet<>();
//        this.map = new HashMap<>();
////        createGraph(wikiPages);
//    }
//
////    private void createGraph(ArrayList<WikiPage> wikiPages){
////        wikiPages.forEach(w ->{
////            if(w.getParent() != null){
////                Edge e = new Edge(w.getParent().getTitle(), w.getTitle(), 0);
////                if(!graph.contains(e)) {
////                    CompareWikiPages c = new CompareWikiPages(w.getParent(), w);
////                    double weight = c.compare();
////                    e.setWeight(weight);
////                    graph.add(e);
////                    addToMap(w.getTitle(), e);
////                }
////            }
////        });
////    }
//
//    private void addToMap(String s, Edge e){
//        if(map.containsKey(s)){
//            ArrayList<Edge> l = map.get(s);
//            l.add(e);
//        } else {
//            ArrayList<Edge> l = new ArrayList<>();
//            l.add(e);
//            map.put(s, l);
//        }
//    }
//
//    void showEdges(){
//        System.out.println();
//        System.out.printf("%1$-45s %2$-45s %3$-75s\n", "Source", "Dest", "Weight");
//        graph.forEach(e -> System.out.printf("%1$-45s %2$-45s %3$-75s\n", e.getSrc(), e.getDest(), e.getWeight()));
//    }
//
//    void dotSpanningTree() throws IOException {
//        RandomAccessFile f = new RandomAccessFile("Spanning_Tree.dot", "rw");
//        f.setLength(0);
//        f.writeBytes("graph G{\n");
//        graph.forEach(e -> {
//            try {
//                f.writeBytes("\"" + e.getSrc() + "\" -- \"" + e.getDest() + "\";\n");
//
//            } catch (IOException error) { error.printStackTrace(); }
//        });
//        f.writeBytes("}");
//    }
//
//    void dotGraph() throws IOException {
//        RandomAccessFile f = new RandomAccessFile("Graph.dot", "rw");
//        f.setLength(0);
//        f.writeBytes("digraph G{\n");
//        Set<String> t = new HashSet<>();
//        graph.forEach(e -> {
//                try {
//                    if (!t.contains(e.src + "><" + e.dest)) {
//                        t.add(e.src + "><" + e.dest);
//                        f.writeBytes("\"" + e.getSrc() + "\" -> \"" + e.getDest() + "\";\n");
//                    }
//
//                } catch (IOException error) { error.printStackTrace(); }
//        });
//        f.writeBytes("}");
//    }
//
////    private void createSpanningTree(ArrayList<WikiPage> wikiPages){
////        wikiPages.forEach(w ->{
////            if(w.getParent() != null){
////                CompareWikiPages c = new CompareWikiPages(w.getParent(), w);
////                double weight = c.compare();
////                if(!destinations.contains(w.getTitle())) {
////                    destinations.add(w.getTitle());
////                    graph.add(new Edge(w.getParent().getTitle(), w.getTitle(), weight));
////                }
////            }
////        });
////    }
//
//
//    public static class Edge implements Comparable<Edge>{
//        private String src, dest;
//        private double weight;
//
//        Edge(String src, String dest, double weight){
//            this.src = src;
//            this.dest = dest;
//            this.weight = weight;
//        }
//
//        double getWeight(){
//            return weight;
//        }
//
//        String getSrc(){
//            return src;
//        }
//
//
//        String getDest(){
//            return dest;
//        }
//
//        void setWeight(double weight){
//            this.weight = weight;
//        }
//
//        @Override
//        public int hashCode() {
//            return src.hashCode() ^ dest.hashCode();
//        }
//
//
//
//        @Override
//        public boolean equals(Object o) {
//            return o == this || o instanceof Edge && (this.getSrc().equals(((Edge) o).getSrc()) && this.getDest().equals(((Edge) o).getDest()));
//
//        }
//
//
//        @Override
//        public int compareTo(Edge e) {
//            return Double.compare(this.getWeight(), e.getWeight());
//        }
//    }
//}
