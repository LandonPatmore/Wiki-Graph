package CSC365HW3;

import java.io.IOException;
import java.util.ArrayList;

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
        DataPuller d = new DataPuller("https://en.wikipedia.org/wiki/New_Jersey");
        ArrayList<WikiPage> w = d.grabData();
        d.toDOTFile();

        CompareWikiPages c;
        for(WikiPage p : w){
            if(!w.get(0).getURL().equalsIgnoreCase(p.getURL())){
                c = new CompareWikiPages(w.get(0), p);
                System.out.printf("%1$-65s %2$-45s\n", p.getTitle() +" : ", c.compare());
            }
        }



    }
}
