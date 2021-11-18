package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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


public class ending_config_con  implements  Initializable{


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
   private TextField icon_path;
   @FXML
   private  TextField bmp_path;
   @FXML
           private RadioButton watermark;
    Alert a = new Alert(Alert.AlertType.ERROR);



    @FXML
   private void back_button(ActionEvent activity){

        //next page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("final.fxml"));
            Stage stage = (Stage) ((Node)activity.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
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

    @FXML void png_choose(ActionEvent activity){
        final FileChooser file_selector=new FileChooser();
        file_selector.setTitle("Choose icon");
       file_selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Icon","*.ico"));
        Stage stage=(Stage) anchorPane.getScene().getWindow();
        File f=file_selector.showOpenDialog(stage);
        String names=f!=null?f.getAbsolutePath():"";

        icon_path.setText(names);




    }
    @FXML void bmp_choose(ActionEvent activity){
        final FileChooser file_selector=new FileChooser();
        file_selector.setTitle("Choose bmp");
        file_selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Bitmap image matrix","*.bmp"));
        Stage stage=(Stage) anchorPane.getScene().getWindow();
        File f=file_selector.showOpenDialog(stage);
        String names=f!=null?f.getAbsolutePath():"";

        bmp_path.setText(names);




    }
    @FXML
    void nextPage(ActionEvent activity){

        if(!file_path.getText().isEmpty() && !icon_path.getText().isEmpty() && !bmp_path.getText().isEmpty()) {
            //next page
            save_states(file_path.getText(),icon_path.getText(),bmp_path.getText(),watermark.isSelected());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("save.fxml"));
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

    sqlite_connection s=new sqlite_connection();
    private boolean is_already_have_entry(){
        String sql2 = "Select * from output where sr=?";

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
    private  void save_states(String path_output,String icon_path,String bmp_path,boolean put_watermark){
        db_controller db=new db_controller();
        //@todo i want to add excetion handling
        // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE output set path_output = ?,icon_path=?,bmp_path=?,watermark=? WHERE  sr = 1")) {
                statement.setString(1,path_output);
                statement.setString(2,icon_path);
                statement.setString(3,bmp_path);
                statement.setInt(4,put_watermark==true?1:0);



                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


            String sql = "INSERT INTO output(sr,path_output,icon_path,bmp_path,watermark) VALUES(?,?,?,?,?)";

            try (Connection conn = s.connector();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                //--------------------------------
                statement.setInt(1,1);
                statement.setString(2,path_output);
                statement.setString(3,icon_path);
                statement.setString(4,bmp_path);
                statement.setInt(5,put_watermark==true?1:0);








                statement.executeUpdate();



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }



        }



    }
    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from output where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);

                ResultSet res = pstmt.executeQuery();
                file_path.setText(res.getString(2));
                icon_path.setText(res.getString(3));
                bmp_path.setText(res.getString(4));
                watermark.setSelected(res.getInt(5)==1?true:false);



            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }

    }
    //-------------------






}
