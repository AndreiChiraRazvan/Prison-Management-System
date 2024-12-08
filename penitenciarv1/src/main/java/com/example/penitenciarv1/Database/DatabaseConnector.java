package com.example.penitenciarv1.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    private String user = "root";
    private String url = "jdbc:mysql://127.0.0.1:3306/penitenciar";
    private String password = "Baze_De_Date-2224";
    public Connection conn = null;
    public DatabaseConnector() {

        try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = (Connection) DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database");

        } catch (Exception e) {
            System.out.println("eroare");
            throw new RuntimeException(e);
        }
    }

    public void callRandomProcedure(){
        try{
            System.out.println("Calling random procedure");
            CallableStatement callableStatement = conn.prepareCall("SELECT * FROM penitenciar.detinut");
            callableStatement.execute();
            if(callableStatement.getResultSet() == null){
                System.out.println("No results found");
            }
            while(callableStatement.getResultSet().next()){
                System.out.println(callableStatement.getResultSet().getString(1));
            }
    }
        catch(Exception e){
            e.printStackTrace();}
        }
}
