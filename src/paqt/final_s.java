package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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


public class final_s implements Initializable{


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     // it is like initstate
        get_state();

    }

   @FXML
   private AnchorPane anchorPane;

   @FXML
   private TextArea msg;




    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("readme.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }
    @FXML

    private void next_page(ActionEvent activity){
        if(!msg.getText().isEmpty()) {
            save_states(msg.getText());
            //next page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ending_config.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

    }
    //---
    sqlite_connection s=new sqlite_connection();
    private boolean is_already_have_entry(){
        String sql2 = "Select * from final_screen where sr=?";

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, 1);


            ResultSet res=pstmt.executeQuery();
            if(res.next()){
                return true;
            }
            else {
                return false;
            }






        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return false;

    }

    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from final_screen where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);

                ResultSet res = pstmt.executeQuery();
                msg.setText(res.getString(2));



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }

    }

    private  void save_states(String msg){
        db_controller db=new db_controller();
        //@todo i want to add excetion handling
        // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE final_screen set msg = ? WHERE  sr = 1")) {
                statement.setString(1,msg);


                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


            String sql = "INSERT INTO final_screen(sr,msg) VALUES(?,?)";

            try (Connection conn = s.connector();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                //--------------------------------
                statement.setInt(1,1);
                statement.setString(2,msg);






                statement.executeUpdate();



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }



        }



    }








}
