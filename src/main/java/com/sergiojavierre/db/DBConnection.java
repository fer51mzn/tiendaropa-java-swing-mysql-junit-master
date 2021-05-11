package com.sergiojavierre.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection = null;
    private Connection connection = null;

    public DBConnection() throws SQLException {
        //datos personalizados para cada BD, usuario, etc.
        String user = "ejemplo";
        String password = "UMJSuRLN80Gz1oeb";
        String host = "80.34.34.150";
        String port = "33060";
        String database = "tiendaRopa";

        connection = DriverManager.getConnection("jdbc:mysql://"+ host +":"+port+"/"+database+"?"+"user="+user+"&password="+password,user,password);
    }

    public static Connection getInstance() throws SQLException{
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection.connection;
    }


}
