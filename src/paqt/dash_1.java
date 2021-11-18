package paqt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class dash_1 implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     // it is like initstate
        get_state();
    }
   @FXML
  private TextField title;
   @FXML
   private  TextField author;
   @FXML
   private TextField email;
   @FXML
    private  TextField website;
   @FXML
    private  TextField copyright;
   @FXML
   private  TextField version;


   @FXML

   private  void handle_next_button(ActionEvent activity)
   {
       Alert a = new Alert(Alert.AlertType.NONE);
       if(!title.getText().isEmpty() && !author.getText().isEmpty() && !email.getText().isEmpty() && !website.getText().isEmpty() && !copyright.getText().isEmpty() && !version.getText().isEmpty() ){
           save_states(title.getText(),author.getText(), email.getText(),website.getText(),copyright.getText(),version.getText());
       files_stage(activity);



       }else{
           a.setAlertType(Alert.AlertType.ERROR);
           a.setTitle("Paqt installer builder engine");
           a.setContentText("""
                   One or more fields or empty please fill all the fields""");

           // show the dialog
           a.show();
       }

   }



    private void files_stage(ActionEvent activity){
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
    sqlite_connection s=new sqlite_connection();
    private boolean is_already_have_entry(){
        String sql2 = "Select * from about where sr=?";

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



    private  void save_states(String title,String author,String email,String website,String copy_right,String version){
        db_controller db=new db_controller();
        //@todo i want to add excetion handling
       // System.out.print( db.Isdb_connected());

        if(is_already_have_entry()) {
            try (Connection connection = s.connector();
                 PreparedStatement statement =
                         connection.prepareStatement("UPDATE about set title = ?,author=?,email=?,website=?,copy_right=?,version=? WHERE  sr = 1")) {
                statement.setString(1,title);
                statement.setString(2,author);
                statement.setString(3,email);
                statement.setString(4,website);
                statement.setString(5,copy_right);
                statement.setString(6,version);
                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.print(e.getMessage());

            }


        }else{


                String sql = "INSERT INTO about(sr,title,author,email,website,copy_right,version) VALUES(?,?,?,?,?,?,?)";

                try (Connection conn = s.connector();
                     PreparedStatement statement = conn.prepareStatement(sql)) {

                 //--------------------------------
                    statement.setInt(1,1);

                    statement.setString(2,title);
                    statement.setString(3,author);
                    statement.setString(4,email);
                    statement.setString(5,website);
                    statement.setString(6,copy_right);
                    statement.setString(7,version);






                    statement.executeUpdate();



                } catch (SQLException e) {

                      System.out.println(e.getMessage());
                }



        }



    }

    private  void get_state(){

        if(is_already_have_entry()) {
            String sql = "Select * from about where sr=?";

            try (Connection conn = s.connector();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, 1);


                ResultSet res = pstmt.executeQuery();
                title.setText(res.getString(2));
                author.setText(res.getString(3));
                email.setText(res.getString(4));
                website.setText(res.getString(5));
                copyright.setText(res.getString(6));
                version.setText(res.getString(7));


            } catch (SQLException e) {

                System.out.println(e.getMessage());
            }
        }

    }







}
