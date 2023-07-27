package com.Accio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    static Connection connection  = null;
    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }

        String user  = "root";
        String pwd  = "Abhishek@123";
        String db = "SearchEngineApp";

        return getConnection(user  , db  , pwd);
    }

    private static Connection getConnection(String user  , String db  , String pwd){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        }
        catch(SQLException | ClassNotFoundException sqlException){
            sqlException.printStackTrace();
        }
        return connection;
    }
}