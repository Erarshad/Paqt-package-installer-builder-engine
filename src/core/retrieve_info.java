package core;

import paqt.destination;
import paqt.properties;
import paqt.sqlite_connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class retrieve_info {
    public  String[]title=new String[1];
    public  String[]author=new String[1];
    public  String []email=new String[1];
    public  String []website=new String[1];
    public  String copy_right[]=new String[1];
    public  String files_path[]=new String[1];
    public  String executable[]=new String[1];
    public  String path[]=new String[1];
    public  String[] folder_name=new String[1];
    public  boolean[] is_user_allowed_to_change_directory=new boolean[1];
    public  String final_screen_msg[]= new  String[1];
    public  String output_path[]=new String[1];
    public  String icon_path[]=new String[1];
    public  String bmp_path[]=new  String[1];
    public  boolean[] watermark=new boolean[1];
    public  boolean desktop_shortcut_ability[]=new boolean[1];
    public  boolean start_menu_abiltiy[]=new boolean[1];
    public  boolean task_bar_pin_ability[]=new boolean[1];
    public  boolean uninstaller_ability[]=new boolean[1];
    public  String welcome_msg[]=new String[1];
    //public  String executable_name="$INSTDIR\\demo.exe";

    public  String[] executables;
    public  int length=0;
    public  String[] version_setup=new String[1];

    public void init_data() throws Exception{

      get_about(title,author,email,website,copy_right,version_setup);

       get_files(files_path,executable);
       get_destination(path,folder_name,is_user_allowed_to_change_directory);
       for(char i: executable[0].toCharArray()){
           if(i==','){
               length++;
           }
       }


         executables=new String[length];
         executables=executable[0].split(",");

    get_final_screen(final_screen_msg);

     get_output(output_path,icon_path,bmp_path,watermark);

     get_properties(desktop_shortcut_ability,start_menu_abiltiy,task_bar_pin_ability,uninstaller_ability);

     get_welcome_screen(welcome_msg);



    }

    public  void get_about(String[] title,String[] author,String[] email,String[] website,String[] copy_right,String[] version_setup){
        String sql = "Select * from about where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            title[0]=res.getString(2);
            author[0]=res.getString(3);
            email[0]=res.getString(4);
            website[0]= res.getString(5);
            copy_right[0]=res.getString(6);
            version_setup[0]=res.getString(7);





        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }
    public  void get_files(String[] files_path,String[] executable){
        String sql = "Select * from add_file where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            files_path[0]=res.getString(3)+"\\";

            executable[0]=res.getString(2);






        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }


    }
    public  void get_destination(String[] path,String[] folder_name,boolean[] is_user_allowed_to_change_directory){
        String sql = "Select * from extraction where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();

            path[0]=res.getString(2);
            path[0]=path[0].substring(0,path[0].length()-1);
            folder_name[0]=res.getString(3);
            is_user_allowed_to_change_directory[0]=res.getInt(4)==1?true:false;






        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }

    public  void get_final_screen(String [] final_screen_msg){
        String sql = "Select * from final_screen where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            final_screen_msg[0]=res.getString(2);




        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }

    public  void get_output(String[] output_path,String[] icon_path,String[] bmp_path,boolean[] watermark){
        String sql = "Select * from output where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            output_path[0]=res.getString(2);
            icon_path[0]=res.getString(3);
            bmp_path[0]=res.getString(4);
            watermark[0]=res.getInt(5)==1?true:false;





        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }
    public  void get_properties(boolean[] desktop_shortcut_ability,boolean[] start_menu_abiltiy,boolean[] task_bar_pin_ability,boolean[] uninstaller_ability){
        String sql = "Select * from properties where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            desktop_shortcut_ability[0]=res.getInt(2)==1?true:false;
            start_menu_abiltiy[0]=res.getInt(3)==1?true:false;
            task_bar_pin_ability[0]=res.getInt(4)==1?true:false;
            uninstaller_ability[0]=res.getInt(5)==1?true:false;







        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }

    public  void get_welcome_screen(String[] welcome_screen){
        String sql = "Select * from welcom_screen where sr=?";
        sqlite_connection s=new sqlite_connection();

        try (Connection conn = s.connector();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);


            ResultSet res = pstmt.executeQuery();
            welcome_screen[0]=res.getString(2);





        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        }



    }



}
