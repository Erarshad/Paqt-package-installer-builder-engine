package paqt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;


public class save_config implements  Initializable{
    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
        try {
            thread_2();
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    @FXML
    public TextArea terminal;


    private  void thread_2() throws  Exception{
        compiling_thread comp=new compiling_thread();

        comp.start();





    }
    public void appendText(String str) {
        Platform.runLater(() -> terminal.appendText(str));
    }












    @FXML
    private  void abort_process(ActionEvent activity){
        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ending_config.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }

    }





}
