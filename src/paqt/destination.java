package paqt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class destination implements  Initializable{


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     // it is like initstate

        get_state();
        pre_dir.setItems(ls);
        handle_predefined_dir_state();
    }

   @FXML
   private  TextField file_path;
   @FXML
   private AnchorPane anchorPane;

   @FXML
   private TextField folder_name;
   @FXML
   private RadioButton allow_user_to_change_destination;

   @FXML
   private  RadioButton choose_predefined_directory;

   @FXML
   private ComboBox pre_dir;



    @FXML
   private Button browse_button;
    ObservableList<String> ls= FXCollections.observableArrayList("$PROGRAMFILES","$COMMONFILES","$WINDIR","$SYSDIR","$APPDATA","$LOCALAPPDATA");



    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("files.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }


    }
    @FXML

    private void next_page(ActionEvent activity){
        //next page
        Alert a = new Alert(Alert.AlertType.NONE);
        if(!choose_predefined_directory.isSelected() &&(!file_path.getText().isEmpty() && !folder_name.getText().isEmpty()) ) {
            try {
                save_states(file_path.getText(),folder_name.getText(),allow_user_to_change_destination.isSelected(),choose_predefined_directory.isSelected());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("properties.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

            } catch (IOException io) {
                io.printStackTrace();
            }
        } else if(choose_predefined_directory.isSelected() && ( pre_dir.getValue()!=null && !folder_name.getText().isEmpty())){
            try {
                save_states(pre_dir.getValue().toString(),folder_name.getText(),allow_user_to_change_destination.isSelected(),choose_predefined_directory.isSelected());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("properties.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

            } catch (IOException io) {
                io.printStackTrace();
            }

        }
        else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Paqt installer builder engine");
            a.setContentText("""
                   One or more fields or empty please fill all the fields""");

            // show the dialog
            a.show();
        }

    }

    @FXML
    private void file_dir_chooser(ActionEvent activity){
        final DirectoryChooser dir_chooser = new DirectoryChooser();
        dir_chooser.setTitle("Select path");
        Stage stage=(Stage) anchorPane.getScene().getWindow();
        File file=dir_chooser.showDialog(stage);
        if(file!=null){
            file_path.setText(file.getAbsolutePath());
        }

    }

    @FXML
    private String folder_name(){
        return folder_name.getText();
    }
    private boolean is_already_have_entry(){
        String sql2 = "Select * from extraction where sr=?";

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
    sqlite_connection s=new sqlite_connection();

    private  void save_states(String path,String folder_name,boolean can_customized_path,boolean choosing_pre_path){
        db_controller db=new db_controller();
        //@todo i want to add excetion handling
        // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE extraction set path = ?,folder_name=?,can_custom_ext_path=?,choosed_pre_path=? WHERE  sr = 1")) {
                statement.setString(1,path);
                statement.setString(2,folder_name);
                statement.setInt(3,can_customized_path==true?1:0);
                statement.setInt(4,choosing_pre_path==true?1:0);

                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


            String sql = "INSERT INTO extraction(sr,path,folder_name,can_custom_ext_path,choosed_pre_path) VALUES(?,?,?,?,?)";

            try (Connection conn = s.connector();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                //--------------------------------
                statement.setInt(1,1);

                statement.setString(2,path);
                statement.setString(3,folder_name);
                statement.setInt(4,can_customized_path==true?1:0);
                statement.setInt(5,choosing_pre_path==true?1:0);





                statement.executeUpdate();



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }



        }



    }

    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from extraction where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);

                ResultSet res = pstmt.executeQuery();
                file_path.setText(res.getString(2));
                folder_name.setText(res.getString(3));
                allow_user_to_change_destination.setSelected(res.getInt(4)==1?true:false);
                boolean is_predefined_directory=res.getInt(5)==1?true:false;
                 choose_predefined_directory.setSelected(false);
                if(is_predefined_directory){
                   pre_dir.setPromptText(file_path.getText());
                   pre_dir.setValue(file_path.getText());
                    choose_predefined_directory.setSelected(true);



                }




            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }else {
            choose_predefined_directory.setSelected(true);
        }

    }

    @FXML
    private  void handle_predefined_dir_state(){
        if(choose_predefined_directory.isSelected()){
            file_path.setDisable(true);
            browse_button.setDisable(true);

        }else{
            file_path.setDisable(false);
            browse_button.setDisable(false);

        }
    }









}
