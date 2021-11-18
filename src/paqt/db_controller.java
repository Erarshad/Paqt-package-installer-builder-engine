package paqt;

import paqt.sqlite_connection;

import java.sql.Connection;
import java.sql.SQLException;

public class db_controller {
    Connection connection;
    public  db_controller(){
        connection= sqlite_connection.connector();
        if(connection==null){
            System.exit(1);
        }


    }
    public boolean  Isdb_connected(){
        try {
            return !connection.isClosed();

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;

        }



    }
}
