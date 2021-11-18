package paqt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
        primaryStage.setTitle("Paqt builder");
        primaryStage.setScene(new Scene(root, 726, 555));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("assets/packages_folder_badged.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
