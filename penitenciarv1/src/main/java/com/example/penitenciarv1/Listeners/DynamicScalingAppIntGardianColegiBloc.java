package com.example.penitenciarv1.Listeners;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Entities.Guardian;
import com.example.penitenciarv1.Interfaces.GuardianInterface;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;

public class DynamicScalingAppIntGardianColegiBloc extends Application {
    private int idUserGardian;
    public DynamicScalingAppIntGardianColegiBloc() {

    }

    public DynamicScalingAppIntGardianColegiBloc(int idGardian) {
        this.idUserGardian = idGardian;
    }


    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();

        TreeTableView<Guardian>treeTableView = new TreeTableView<>();
        TreeItem<Guardian> rootItem = new TreeItem<>(new Guardian("", "", "", ""));
        rootItem.setExpanded(true);
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);

        TreeTableColumn<Guardian, String> idColumn = createColumn("ID", guardian -> guardian.getId());
        TreeTableColumn<Guardian, String> usernameColumn = createColumn("Username", guardian -> guardian.getUsername());
        TreeTableColumn<Guardian, String> floorColumn = createColumn("Floor", guardian -> guardian.getFloor());
        TreeTableColumn<Guardian, String> blockColumn = createColumn("Detention Block", guardian -> guardian.getDetentionBlock());

        treeTableView.getColumns().addAll(idColumn, usernameColumn, floorColumn, blockColumn);

        //dimensiune coloane
        treeTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            idColumn.setPrefWidth(totalWidth * 0.20);
            usernameColumn.setPrefWidth(totalWidth * 0.30);
            floorColumn.setPrefWidth(totalWidth * 0.20);
            blockColumn.setPrefWidth(totalWidth * 0.30);
        });

        DatabaseConnector dbConnector = new DatabaseConnector();
        try(Statement statement = dbConnector.conn.createStatement()){
            int idGuardian = dbConnector.getGuardianId(idUserGardian);
            System.out.printf("idGuardian = %d\n", idGuardian);
            ArrayList<Guardian> guardians = dbConnector.getGuardianColleagues(idGuardian);
            if(guardians.size() == 0){
                System.out.println("No guardians found");
            }
            for(Guardian guardian : guardians){
                StringProperty id = guardian.getId();
                StringProperty username = guardian.getUsername();
                StringProperty floor = guardian.getFloor();
                StringProperty block = guardian.getDetentionBlock();

                TreeItem<Guardian> treeItem = new TreeItem<>(new Guardian(id.get(), username.get(), floor.get(), block.get()));
                rootItem.getChildren().add(treeItem);
            }

            System.out.println("Numarul de elemente incarcate: " + rootItem.getChildren().size());
        }catch (Exception e){
            System.out.println("Error in connecting to the database");
            e.printStackTrace();
        }

        AnchorPane.setTopAnchor(treeTableView, 25.5);
        AnchorPane.setLeftAnchor(treeTableView, 10.0);
        AnchorPane.setRightAnchor(treeTableView, 10.0);
        AnchorPane.setBottomAnchor(treeTableView, 10.0);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(event -> {
            GuardianInterface gin = new GuardianInterface(idUserGardian);
            Stage stage = new Stage();
            primaryStage.close();
            gin.start(stage);
        });
        goBackButton.setAlignment(Pos.TOP_LEFT);
        goBackButton.setPrefHeight(20);

        root.getChildren().addAll(treeTableView, goBackButton);

        Scene scene = new Scene(root, 1400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private TreeTableColumn<Guardian, String> createColumn(String title, Function<Guardian, ObservableValue<String>> mapper) {
        TreeTableColumn<Guardian, String> column = new TreeTableColumn<>(title);
        column.setCellValueFactory(cellData -> mapper.apply(cellData.getValue().getValue()));
        return column;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


