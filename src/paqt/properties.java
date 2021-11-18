package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
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


public class properties implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     // it is like initstate
        get_state();
    }


   @FXML
   private AnchorPane anchorPane;
   @FXML
   private RadioButton r1;

   @FXML
   private  RadioButton r2;

   @FXML
   private  RadioButton r3;
   @FXML
   private  RadioButton r4;







    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("destination.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }
    @FXML

    private void next_page(ActionEvent activity){
        int desktop_shortcut=0;
        int start_menu_pin=0;
        int taskbar_pin=0;
        int uninstaller_mechanism=0;
        if(r1.isSelected()){
            desktop_shortcut=1;

        }

        if(r2.isSelected()){
            start_menu_pin=1;

        }
        if(r3.isSelected()){
            taskbar_pin=1;
        }


        if(r4.isSelected()){
            uninstaller_mechanism=1;
        }
        save_states(desktop_shortcut,start_menu_pin,taskbar_pin,uninstaller_mechanism);

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
    //--
    sqlite_connection s=new sqlite_connection();
    private boolean is_already_have_entry(){
        String sql2 = "Select * from properties where sr=?";

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
    private  void save_states(int  desktop_shortcut,int start_menu_pin,int task_bar_pin,int uninstaller_mechanism){
        db_controller db=new db_controller();
        //@todo i want to add excetion handling
        // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE properties set Desktop_shortcut = ?,start_menu_pin=?,taskbar_pin=?,uninstaller_mechanism=? WHERE sr = 1")) {
                statement.setInt(1,desktop_shortcut);
                statement.setInt(2,start_menu_pin);
                statement.setInt(3,task_bar_pin);
                statement.setInt(4,uninstaller_mechanism);

                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


            String sql = "INSERT INTO properties(sr,Desktop_shortcut,start_menu_pin,taskbar_pin,uninstaller_mechanism) VALUES(?,?,?,?,?)";

            try (Connection conn = s.connector();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                //--------------------------------
                statement.setInt(1,1);
                statement.setInt(2,desktop_shortcut);
                statement.setInt(3,start_menu_pin);
                statement.setInt(4,task_bar_pin);
                statement.setInt(5,uninstaller_mechanism);







                statement.executeUpdate();



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }



        }




    }

    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from properties where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);

                ResultSet res = pstmt.executeQuery();
                int row_1=res.getInt(2);
                int row_2=res.getInt(3);
                int row_3=res.getInt(4);
                int row_4=res.getInt(5);
                if(row_1==1){
                    r1.setSelected(true);



                }

                r2.setSelected(row_2==1?true:false);
                if(row_3==1){
                    r3.setSelected(true);
                }

                r4.setSelected(row_4==1?true:false);


            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }

    }










}
