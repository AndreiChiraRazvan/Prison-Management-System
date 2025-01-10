package com.example.penitenciarv1.Database;

import com.example.penitenciarv1.Entities.*;
import com.example.penitenciarv1.Listeners.DynamicScallingAppIntPrisonerFutureTasks;
import eu.hansolo.toolbox.time.DateTimes;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Guardian> getGuardianColleaguesSameBlock(int idGuardian) {
        ArrayList<Guardian> guardian = new ArrayList<>();
        try{
            CallableStatement cs = conn.prepareCall("{call penitenciar.GetColegiiGardianului(?)}");
            cs.setInt(1, idGuardian);
            boolean hasResults = cs.execute();
            if(hasResults) {
                ResultSet rs = cs.getResultSet();

                while (rs.next()) {
                    Guardian newGuardian = new Guardian();
                    newGuardian.setId(new SimpleStringProperty(rs.getString(1)));
                    newGuardian.setUsername(new SimpleStringProperty(rs.getString(2)));
                    newGuardian.setFloor(new SimpleStringProperty(rs.getString(3)));
                    newGuardian.setDetentionBlock(new SimpleStringProperty(rs.getString(4)));
                    guardian.add(newGuardian);
                }
                rs.close();
            }else
                System.out.printf("No results found");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return guardian;
    }

    public ArrayList<Inmates> getInmatesOnShift(int idGardian) {
        ArrayList<Inmates> inmates = new ArrayList<>();
        try{
            CallableStatement cs = conn.prepareCall("{call penitenciar.GetInmatesOnShift(?)}");
            cs.setInt(1, idGardian);
            boolean hasResults = cs.execute();
            if(hasResults) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Inmates newInmate = new Inmates();
                    newInmate.setid(new SimpleStringProperty(rs.getString(1)));
                    newInmate.setName(new SimpleStringProperty(rs.getString(2)));
                    newInmate.setIdCelula(new SimpleStringProperty(rs.getString(3)));
                    newInmate.setProfession(new SimpleStringProperty(rs.getString(4)));
                    String remainedSentence = getRemainingSentence(Integer.parseInt(newInmate.getid().getValue()));
                    newInmate.setSentenceRemained(new SimpleStringProperty(remainedSentence));
                    inmates.add(newInmate);
                }
                rs.close();
            }else{
                System.out.println("No results found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return inmates;
    }

    public ArrayList<String> getEmptyCells(int idGardian) {
        ArrayList<String> emptyCells = new ArrayList<>();
        try{
            CallableStatement callableStatement = conn.prepareCall("{call penitenciar.GetCellsOnShift(?)}");
            System.out.println(idGardian);
            callableStatement.setInt(1, idGardian);
            callableStatement.execute();
            ResultSet rs = callableStatement.getResultSet();
            if(rs == null){
                System.out.println("No results found");
            }else {
                while (rs.next()) {
                    emptyCells.add(rs.getString(1));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return emptyCells;
    }

    public void updateCellSize(int idCell, int modifyValue){
        try {
            String updateInitialCell = "UPDATE penitenciar.celula SET locuri_ramase = locuri_ramase + ? WHERE id_celula = ?";
            CallableStatement csUpdateCell = conn.prepareCall(updateInitialCell);
            csUpdateCell.setInt(1, modifyValue);
            csUpdateCell.setInt(2, idCell);
            csUpdateCell.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateInmateCell(int idInmate, int newCell) {
        try {
            //get the fk_id_cell from detinut table
            String getFK_ID_Cell = "SELECT detinut.fk_id_celula FROM penitenciar.detinut WHERE detinut.id_detinut = ?";
            CallableStatement csFKCell = conn.prepareCall(getFK_ID_Cell);
            csFKCell.setInt(1, idInmate);
            csFKCell.executeQuery();
            ResultSet rsFKCell = csFKCell.getResultSet();
            int fk_id;
            if(rsFKCell.next())
                fk_id = rsFKCell.getInt(1);
            else {
                fk_id = -1;
                System.out.println("No results found");
            }

            //get id of the initial cell the inmate was in
            String getInitialCell = "SELECT celula.id_celula FROM penitenciar.celula WHERE celula.id_celula = ?";
            CallableStatement csInitialCell = conn.prepareCall(getInitialCell);
            csInitialCell.setInt(1, fk_id);
            csInitialCell.executeQuery();
            ResultSet rsInitialCell = csInitialCell.getResultSet();
            int initialCell;
            if(rsInitialCell.next())
                initialCell = rsInitialCell.getInt(1);
            else {
                initialCell = -1;
                System.out.println("No results found");
            }

            //update the size of each cell
            updateCellSize(initialCell, -1);
            updateCellSize(newCell, 1);

            String theQuery = "UPDATE penitenciar.detinut SET fk_id_celula = ? WHERE id_detinut = ?";
            CallableStatement callableStatement = conn.prepareCall(theQuery);
            callableStatement.setInt(1, newCell);
            callableStatement.setInt(2, idInmate);
            callableStatement.execute();
        }catch (Exception e){
            System.out.println("Update error");
            e.printStackTrace();
        }
    }

    public ArrayList<Guardian> getGuardianColleaguesWholePrison(int idGuardian) {
        ArrayList<Guardian> guardian = new ArrayList<>();
        try{
            CallableStatement cs = conn.prepareCall("{call penitenciar.GetTotiColegiiGardianului(?)}");
            cs.setInt(1, idGuardian);
            boolean hasResult = cs.execute();
            if(hasResult) {
                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    Guardian newGuardian = new Guardian();
                    newGuardian.setId(new SimpleStringProperty(cs.getResultSet().getString(1)));
                    newGuardian.setUsername(new SimpleStringProperty(cs.getResultSet().getString(2)));
                    newGuardian.setFloor(new SimpleStringProperty(cs.getResultSet().getString(3)));
                    newGuardian.setDetentionBlock(new SimpleStringProperty(cs.getResultSet().getString(4)));
                    guardian.add(newGuardian);
                }
            }else
                System.out.printf("No results found");


        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return guardian;
    }

    public int getGuardianId(int idGuardian) {
        try{
            System.out.println("calling a query that gives the guardian id based on the user id");
            CallableStatement cs = conn.prepareCall("SELECT id FROM penitenciar.gardian WHERE fk_id_utilizator = ?");
            cs.setInt(1, idGuardian);
            cs.executeQuery();
            if(cs.getResultSet().next()) {
                return cs.getResultSet().getInt(1);
            }else{
                System.out.println("No results found in getGuardian");
                return -1;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public List<DynamicScallingAppIntPrisonerFutureTasks.Task> getFutureTasks(String detinutUsername) {
        List<DynamicScallingAppIntPrisonerFutureTasks.Task> tasks = new ArrayList<>();
        try {
            CallableStatement cs = conn.prepareCall("{CALL GetTaskuriViitoare(?)}");
            cs.setString(1, detinutUsername); // Setăm parametru de intrare
            ResultSet rs = cs.executeQuery(); // Executăm și obținem rezultatul

            while (rs.next()) {
                String idTask = rs.getString("ID_Task");
                String description = rs.getString("Descriere");
                String difficulty = rs.getString("Dificultate");
                String startTime = rs.getString("Inceput");
                String endTime = rs.getString("Sfarsit");

                tasks.add(new DynamicScallingAppIntPrisonerFutureTasks.Task(idTask, description, difficulty, startTime, endTime));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing GetTaskuriViitoare procedure", e);
        }
        return tasks;
    }

    public int[] getCarceraIdAndOcupationStatus(int idInmate) {
        int [] carceraInfo = new int[2];
        try{
            CallableStatement cs = conn.prepareCall("{CALL penitenciar.GetCarceraPrizonier(?)}");
            cs.setInt(1, idInmate);
            ResultSet rs = cs.executeQuery();
            if(rs.next()) {
                carceraInfo[0] = rs.getInt(2);
                carceraInfo[1] = rs.getInt(3);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return carceraInfo;
    }

    public ArrayList<Sentence> getAllSentencesOfOneInmate(int idInmate) {
        try{
            ///  caut sentintele fiecariu detinut
            ArrayList<Sentence> sentences = new ArrayList<>();
            CallableStatement cs = conn.prepareCall("SELECT * FROM penitenciar.sentinta where fk_id_detinut = "+ idInmate +" ;");
            
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                    String categorie = rs.getString("categorie");
                    String motivSpecific = rs.getString("motiv_specific");
                    String start_time = rs.getString("start_time");
                    String end_time = rs.getString("end_time");
                    sentences.add(new Sentence(categorie, motivSpecific, start_time, end_time));
            }


            return sentences;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Visit> getVisits(int idVisitor){
        try{
            CallableStatement cs = conn.prepareCall("{CALL GetProgramariByVizitator(?)}");
            cs.setInt(1, idVisitor);
            ResultSet rs = cs.executeQuery();
            ArrayList<Visit> visits = new ArrayList<>();
            while (rs.next()) {

                String startTime = (rs.getString("startTime"));
                String endTime = (rs.getString("endTime"));
                String nume = (rs.getString("nume"));
                System.out.println(nume);
                Visit visit = new Visit(startTime, endTime, nume);
                visits.add(visit);
            }
            return visits;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public int getIdVizitatorPentruUtilizator(int id) {
        try{
            CallableStatement cs = conn.prepareCall("select * from vizitator where vizitator.fk_id_utilizator = ? limit 1");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }
            System.out.println("This user is not a visitor");
            return -1;
        }
        catch (Exception e){
            System.out.println("Vizitatorul cu acest id de utilizator nu exista");
            throw new RuntimeException(e);
        }


    }

    public ArrayList<Inmates> getVisitedInmates(int idVisitor) {
        try{
            CallableStatement cs = conn.prepareCall("{CALL get_data_detinut_from_vizitor(?)}");
            cs.setInt(1, idVisitor);
            ResultSet rs = cs.executeQuery();
            ArrayList<Inmates> inmates = new ArrayList<>();
            while (rs.next()) {
                String idInmate = rs.getString("fk_id_prizioner");
                String numeInmate = rs.getString("nume");
                String remainingSentence = rs.getString("sentence_remained");
                inmates.add(new Inmates(numeInmate, idInmate, remainingSentence));

            }
            return inmates;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return this.conn;
    }
}
