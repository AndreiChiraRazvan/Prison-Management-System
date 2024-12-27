package com.example.penitenciarv1.Database;

import com.example.penitenciarv1.Entities.Inmates;
import com.example.penitenciarv1.DynamicScalingAppIntGardianDetinut;
import com.example.penitenciarv1.Entities.User;
import eu.hansolo.toolbox.time.DateTimes;
import javafx.scene.control.TreeItem;

import java.sql.*;
import java.util.ArrayList;

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

    public void callRandomProcedure() {
        try {
            System.out.println("Calling random procedure");
            CallableStatement callableStatement = conn.prepareCall("SELECT * FROM penitenciar.detinut");
            callableStatement.execute();
            if (callableStatement.getResultSet() == null) {
                System.out.println("No results found");
            }
            while (callableStatement.getResultSet().next()) {
                System.out.println(callableStatement.getResultSet().getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callQueryFromString(String query) {
        ///  we execute the query that is sent
        try {
            conn.prepareCall(query);
            callRandomProcedure();
        } catch (Exception e) {
            System.out.println("Query gresit");
            throw new RuntimeException(e);
        }
    }

    public User checkAndReturnUser(String username, String password) {

        try {
            String theQuery = "SELECT * FROM penitenciar.utilizator " +
                    "WHERE username ='" + username + "' AND password = '" + password + "'";
            CallableStatement callableStatement = conn.prepareCall(theQuery);
            callableStatement.execute();
            System.out.println(callableStatement);
            if (callableStatement.getResultSet() == null) {
                System.out.println("No results found");
                return null;
            }

            User newUser = new User();
            if (callableStatement.getResultSet().next()) {
                newUser.setId(callableStatement.getResultSet().getInt(1));
                System.out.println(newUser.getId());
                newUser.setAccessRights(callableStatement.getResultSet().getInt(4));
                newUser.printUser();
                return newUser;
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query error");
            return null;
        }

    }

    public ArrayList<Inmates> getInmatesFromDatabase(){
        ArrayList<Inmates> inmates = new ArrayList<>();
        try{
            String theQuery = "SELECT * FROM penitenciar.detinut ";

            CallableStatement callableStatement = conn.prepareCall(theQuery);
            callableStatement.execute();
            System.out.println(callableStatement);
            if (callableStatement.getResultSet() == null) {
                System.out.println("No results found");
                return null;
            }
            if (callableStatement.getResultSet().next()) {
                Inmates newInmate = new Inmates();
                newInmate.setid(callableStatement.getResultSet().getInt(1));
                newInmate.setName(callableStatement.getResultSet().getString(2));
                newInmate.setIdCelula(callableStatement.getResultSet().getString(3));
                newInmate.setProfession(callableStatement.getResultSet().getString(5));
                String remainedSentence = getRemainingSentence(newInmate.getid());
                newInmate.setSentenceRemained(remainedSentence);
            }
            return inmates;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public String getRemainingSentence(int idPrizioner) {
        try{
            String perioadaRamasa = "";
            CallableStatement cs = conn.prepareCall("{call penitenciar.remaining_time_based_on_id_inmate( ?, ?)}");
            // we calculated the total time
            cs.setInt(1, idPrizioner);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            if(cs.getString(2) != null)
            {
                perioadaRamasa = cs.getString(2);
                return perioadaRamasa;
            }
            System.out.println(perioadaRamasa);

            return "Necunoscut";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<DateTimes> getAllSentencesOfOneInmate() {
        try{
            ///  caut sentintele fiecariu detinut
            ArrayList<DateTimes> sentences = new ArrayList<>();



            return sentences;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
