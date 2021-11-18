package paqt;

import core.robotus;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class compiling_thread extends Thread{
    String path_of_output="";




    public void write_code() throws IOException {

        String path=System.getProperty("user.dir");
        robotus r=new robotus();
        String trigger="makensis.exe"+" "+path+"\\custom_action\\ide.nsi";

        try {
            r.write();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String null_dir=path+"\\"+"null_compiler\\Bin\\";
        System.out.print(null_dir);
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd " +null_dir +"&&"+trigger);

        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) { break; }



           if(line.contains("(ACP)")){
               System.out.println("Converting your information into application, Please wait");
               continue;
           }
           if(line.contains("Output:")){
               path_of_output+=line.substring(7,line.length());

           }


            System.out.println(line);
           if(line.contains("Total size")){
               Runtime.getRuntime().exec("explorer /select,"+path_of_output);
           }

        }


    }


    public void run(){
        try {
            write_code();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
