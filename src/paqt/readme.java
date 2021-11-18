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


public class readme implements Initializable {
    custom_io io=new custom_io();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        path = System.getProperty("user.dir")+"\\"+"custom_action";


        // it is like initstate
        int val=io.create_file("license.txt",path);
        if(val==2){
            String text = io.read_file("license.txt",path);
            msg.setText(text);


        }
    }


   @FXML
   private AnchorPane anchorPane;

   @FXML
   private TextArea msg;
   public  String path="";




    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("custom_script.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }
    Alert a = new Alert(Alert.AlertType.ERROR);
    @FXML

    private void next_page(ActionEvent activity){
        if(!msg.getText().isEmpty()) {
            //next page
            io.save_in_file(msg.getText(),"license.txt",path);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("final.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }else{
            a.setTitle("Paqt installer builder engine");
            a.setContentText("One or more fields or empty please fill all the fields");

            // show the dialog
            a.show();

        }

    }












}
