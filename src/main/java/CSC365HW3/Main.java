package CSC365HW3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        WikiPages w = new WikiPages();
        DataPuller d = new DataPuller();
        w.addWikiPage(d.pullData("https://en.wikipedia.org/wiki/Formula_One"));

        for (int i = 0; i < w.size(); i++) {
            try {
                System.out.println("#" + i + " " + w.expose().get(i).getTitle());
                for (int j = 0; j < w.expose().get(i).getChildren().length; j++) {
                    w.addWikiPage(d.pullData(w.expose().get(i).getChildren()[j]));
                }
            } catch (Exception e){
                System.out.println("URL DOES NOT EXIST <---------------------");
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
