package CSC365HW3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        DataPuller d = new DataPuller();
        d.pullData("https://en.wikipedia.org/wiki/Scala_(programming_language)");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
