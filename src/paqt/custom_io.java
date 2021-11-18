package paqt;

import java.io.*;
import java.util.Scanner;

public class custom_io {


    public int create_file(String file_name,String path) {
        try {
            File myObj = new File(path+"\\"+file_name);
            if (myObj.createNewFile()) {
                 return 1;
            } else {
                return 2;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return  -1;
    }
    public void save_in_file(String txt,String file_name,String path) {
        try {
            FileWriter myWriter = new FileWriter(path+"\\"+file_name);
            myWriter.write(txt);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String  read_file(String file_name,String path){
        String out="";
        try {
            File myObj = new File(path+"\\"+file_name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                out+=data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return out;
    }
    public void clearTheFile(String name,String path) throws IOException {
        FileWriter fwOb = new FileWriter(path+"\\"+name, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }





}
