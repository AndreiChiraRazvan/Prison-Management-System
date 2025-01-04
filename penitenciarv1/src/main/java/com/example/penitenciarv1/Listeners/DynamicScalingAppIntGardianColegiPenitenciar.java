package com.example.penitenciarv1.Listeners;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Entities.Guardian;
import com.example.penitenciarv1.Interfaces.GuardianInterface;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.Array;
import java.sql.Statement;
import java.util.ArrayList;

public class DynamicScalingAppIntGardianColegiPenitenciar extends Application {

    private int idGardian;

    public DynamicScalingAppIntGardianColegiPenitenciar() {

    }

    public DynamicScalingAppIntGardianColegiPenitenciar(int id) {
        this.idGardian = id;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //VBox principal
        VBox root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20; -fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");

        //Titlu
        Label titleLabel = new Label("Coleagues from whole Prison");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        //Tabel pt vizualizare colegi
        TableView<Guardian> coleaguesTable = new TableView<>();

        //Coloanele
        TableColumn<Guardian, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(guardian -> guardian.getValue().getId());

        TableColumn<Guardian, String> nameCol = new TableColumn<>("Username");
        nameCol.setCellValueFactory(guardian -> guardian.getValue().getUsername());

        TableColumn<Guardian, String> floorCol = new TableColumn<>("Floor");
        floorCol.setCellValueFactory(guardian -> guardian.getValue().getFloor());

        TableColumn<Guardian, String> blockCol = new TableColumn<>("Block");
        blockCol.setCellValueFactory(guardian -> guardian.getValue().getDetentionBlock());

        coleaguesTable.getColumns().addAll(idCol, nameCol, floorCol, blockCol);
        coleaguesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //load guardians from db
        loadColeaguesWholePrison(coleaguesTable);

        //back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: linear-gradient(to right, #42a5f5, #1e88e5);"
                + "-fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-background-radius: 20;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: linear-gradient(to right, #1e88e5, #42a5f5);"
                + "-fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-background-radius: 20;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: linear-gradient(to right, #42a5f5, #1e88e5);"
                + "-fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-background-radius: 20;"));

        backButton.setOnAction(e -> {
            GuardianInterface gin = new GuardianInterface(idGardian);
            Stage stage = new Stage();
            primaryStage.close();
            gin.start(stage);
        });


        //scene layout
        root.getChildren().addAll(titleLabel, coleaguesTable, backButton);
        Scene scene = new Scene(root, 1400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coleagues from whole Prison");
        primaryStage.show();

    }

    private void loadColeaguesWholePrison(TableView<Guardian> table) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        int idGuardian = dbConnector.getGuardianId(idGardian);
        try(Statement statement = dbConnector.conn.createStatement()){
            ArrayList<Guardian> guardians = dbConnector.getGuardianColleaguesWholePrison(idGuardian);
            if(guardians == null){
                System.out.println("Error: Guardians is null");
            }else {
                for(Guardian g : guardians){
                    System.out.println(g.getUsername());
                    table.getItems().add(g);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
