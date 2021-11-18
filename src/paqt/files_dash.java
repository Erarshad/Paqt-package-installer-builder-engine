package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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


public class files_dash implements  Initializable{


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     // it is like initstate
        get_state();
    }

   @FXML
   private  TextField file_path;
   @FXML
   private AnchorPane anchorPane;

   @FXML
   private TextField main_exe;



    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
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

        if(!file_path.getText().isEmpty() && !main_exe.getText().isEmpty()) {
            //next page



            save_states(main_exe.getText(),file_path.getText());





            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("destination.fxml"));
                Stage stage = (Stage) ((Node) activity.getSource()).getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }

        }else {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Paqt installer builder engine");
            a.setContentText("""
                   Your one or more selection is still not filled""");

            // show the dialog
            a.show();
        }

    }

    @FXML
    private void file_dir_chooser(ActionEvent activity){
        final DirectoryChooser dir_chooser = new DirectoryChooser();
        dir_chooser.setTitle("Select directory");
        Stage stage=(Stage) anchorPane.getScene().getWindow();
        File file=dir_chooser.showDialog(stage);
        if(file!=null){
            file_path.setText(file.getAbsolutePath());
        }

    }

    @FXML void exe_choose(ActionEvent activity){
        final FileChooser file_selector=new FileChooser();
        file_selector.setTitle("Choose executable(s)");
       file_selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Application","*.exe"));
        Stage stage=(Stage) anchorPane.getScene().getWindow();
        List<File> f=file_selector.showOpenMultipleDialog(stage);
        String names="";
        for(File file: f){
            names+=file.getName().toString();
            names+=", ";

        }
        main_exe.setText(names);


    }
    sqlite_connection s=new sqlite_connection();
    private boolean is_already_have_entry(){
        String sql2 = "Select * from add_file where sr=?";

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

    //----
    private  void save_states(String exes,String files){
        db_controller db=new db_controller();
        //@todo i want to add exception handling
        // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE add_file set executable=?,total_files=? WHERE  sr = 1")) {
                statement.setString(1,exes);
                statement.setString(2,files);


                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


            String sql = "INSERT INTO add_file(sr,executable,total_files) VALUES(?,?,?)";

            try (Connection conn = s.connector();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                //--------------------------------
                statement.setInt(1,1);

                statement.setString(2,exes);
                statement.setString(3,files);






                statement.executeUpdate();



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }



        }



    }

    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from add_file where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);


                ResultSet res = pstmt.executeQuery();
                file_path.setText(res.getString(3));
                main_exe.setText(res.getString(2));




            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }

    }











}
