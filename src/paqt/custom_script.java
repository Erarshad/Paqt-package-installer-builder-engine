package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class custom_script implements  Initializable{
    custom_io io=new custom_io();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        path = System.getProperty("user.dir")+"\\"+"custom_action";
     // it is like initstate
        int val=io.create_file("custom.bat",path);
        if(val==2){
         String text = io.read_file("custom.bat",path);
         editor.setText(text);


        }


    }


   @FXML
   private AnchorPane anchorPane;

   @FXML
   private TextArea editor;
   public  String path="";




    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome_screen.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }

    @FXML

    private void next_page(ActionEvent activity){
        Alert a = new Alert(Alert.AlertType.NONE);
        //next page
        if(!editor.getText().isEmpty()) {
            io.save_in_file(editor.getText(),"custom.bat",path);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("readme.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }else{
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Paqt installer builder engine");
            a.setContentText("""
                   One or more fields or empty please fill all the fields \n write echo 1 in case, If you do not wanted to perform any script""");

            // show the dialog
            a.show();
        }



    }

    @FXML
    private  void run_attempt_cmd(){
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!editor.getText().isEmpty()) {
            io.save_in_file(editor.getText(),"custom.bat",path);

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd " +path +"&&"+"custom.bat");

            builder.redirectErrorStream(true);
            try {
                Process p = builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Paqt installer builder engine");
            a.setContentText("""
                   Please first write bat script""");

            // show the dialog
            a.show();
        }



    }








}
