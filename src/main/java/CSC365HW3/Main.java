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
//    }
//
//    public void show(ArrayList<WikiPage> w) {
//        for (int i = 0; i < w.size(); i++) {
//            if (w.get(i).getParent() == null) {
//                System.out.printf("%1$-45s %2$-45s %3$-45s\n", "Head", w.get(i).getTitle(), i);
//            } else {
//                System.out.printf("%1$-45s %2$-45s %3$-45s\n", w.get(i).getParent().getTitle(), w.get(i).getTitle(), i);
//            }
//        }


    public static void main(String[] args) throws IOException {
//        launch(args);

        ArrayList<WikiPage> w = new ArrayList<WikiPage>();
        DataPuller d = new DataPuller();
        w.add(d.pullData("https://en.wikipedia.org/wiki/Formula_One", null, false));

        try {
            for (int i = 0; i < 1; i++) {
                System.out.println(w.get(i).getTitle());
                for (int j = 0; j < w.get(i).getChildren().length; j++) {
                    w.add(d.pullData(w.get(i).getChildren()[j], w.get(i), false));
                }
                w.get(i).setChildrenCreated();
            }

            for (int i = 1; i < w.size(); i++) {
                if (!w.get(i).areChildrenCreated()) {
                    if (!w.get(i).noChildren()) {
                        for (int j = 0; j < w.get(i).getChildren().length; j++) {
                            w.add(d.pullData(w.get(i).getChildren()[j], w.get(i), true));
                        }
                    }
                    System.out.println(w.get(i).getTitle());
                }
            }
            System.out.println("lol");
        } catch (Exception e) {
            e.printStackTrace();
        }

        CompareWikiPages c;
        for(WikiPage p : w){
            if(!w.get(0).getURL().equalsIgnoreCase(p.getURL())){
                c = new CompareWikiPages(w.get(0), p);
                System.out.printf("%1$-65s %2$-45s\n", p.getTitle() +" : ", c.compare());
            }
        }



    }
}
