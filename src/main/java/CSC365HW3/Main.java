package CSC365HW3;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        MostCommonWords.INSTANCE.commonWords();

//        ArrayList<WikiPage> w = new ArrayList<WikiPage>();
//        DataPuller d = new DataPuller();
//        w.add(d.pullData("https://en.wikipedia.org/wiki/Formula_One", null, false));
//
////        CompareWikiPages c = new CompareWikiPages(w.get(0), w.get(1));
////        System.out.println("Cosine Similarity: " + c.compare());
//
//
//        for (int i = 0; i < 1; i++) {
//            try {
//                System.out.println(w.get(i).getTitle());
//                for (int j = 0; j < w.get(i).getChildren().length; j++) {
//                    w.add(d.pullData(w.get(i).getChildren()[j], w.get(i), false));
//                }
//                w.get(i).setChildrenCreated(true);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        //creates Wikipages for the children that were not created, but were just URLS
//        try {
//            for(int i = 0; i < w.size(); i++){
//                if(!w.get(i).isChildrenCreated()){
//                    System.out.println(w.get(i).getTitle());
//                    for(int j = 0; j < 4; j++) {
//                        if(!w.get(i).noChildren()) {
//                            w.add(d.pullData(w.get(i).getChildren()[j], w.get(i), true));
//                        }
//                    }
//                }
//            }
//            System.out.println("lol");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void show(ArrayList<WikiPage> w){
        for(int i = 0; i < w.size(); i++) {
            if (w.get(i).getParent() == null) {
                System.out.printf("%1$-45s %2$-45s %3$-45s\n", "Head", w.get(i).getTitle(), i);
            } else {
                System.out.printf("%1$-45s %2$-45s %3$-45s\n", w.get(i).getParent().getTitle(), w.get(i).getTitle(), i);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
