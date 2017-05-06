package CSC365HW3;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {

//    @Override
//    public void start(Stage primaryStage) throws Exception {
////        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
////        primaryStage.setTitle("Hello World");
////        primaryStage.setScene(new Scene(root, 300, 275));
////        primaryStage.show();
////    }
//        }


    public static void main(String[] args) throws Exception {
//        launch(args);

        MostCommonWords.INSTANCE.readCommonWords();
        DataPuller d = new DataPuller("https://en.wikipedia.org/wiki/Formula_One");
        ArrayList<WikiPage> w = d.grabData();
        Graph g = new Graph(w);
        g.getMap();
        System.out.println();
        g.showEdges();
        System.out.println();
        g.dotGraph();

//        Graph.Edge e = new Graph.Edge("hi", "bye", 1);
//        Graph.Edge f = new Graph.Edge("hi", "bye", 56);
//        System.out.println(e.compareTo(f));
//        System.out.println(e.equals(f));
//        HashSet<Graph.Edge> edges = new HashSet<>();
//        edges.add(e);
//        edges.add(f);
//        edges.forEach(q -> System.out.println(q.getSrc()));




    }
}
