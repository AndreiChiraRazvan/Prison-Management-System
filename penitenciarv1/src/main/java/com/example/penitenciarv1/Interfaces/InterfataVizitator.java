package com.example.penitenciarv1.Interfaces;

import com.example.penitenciarv1.Controllers.ListItem;
import com.example.penitenciarv1.Database.DatabaseConnector;

import com.example.penitenciarv1.Entities.Inmates;
import com.example.penitenciarv1.Entities.User;
import com.example.penitenciarv1.Entities.Visit;
import com.example.penitenciarv1.HelloApplication;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class InterfataVizitator extends Application {
    @FXML
    private AnchorPane programareTab;

    @FXML
    private VBox mainVbox;
    @FXML
    TabPane tabPane;
    @FXML
    private Tab backButton;
    @FXML
    private ListView<Inmates>listViewDetinut;
    @FXML
    private AnchorPane parentOfListView;

    TreeTableView<Visit> treeTableView;

    public InterfataVizitator(){

    }
    public InterfataVizitator(Stage stage2, DatabaseConnector databaseConnector,  User newUser) {
        System.out.println("damn");
        start(stage2, databaseConnector, newUser);
        // here we can change everything, the new design should be here

        treeTableView.setRoot(null);

    }

    public void start(Stage primaryStage, DatabaseConnector databaseConnector, User newUser) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("interfatavizitator.fxml"));
            Parent root = fxmlLoader.load();

            primaryStage.setTitle("Vizitator -Meniu");
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.show();
//          StackPane root2 = new StackPane();
            root.setId("pane");
            Scene scene2 = new Scene(root, 600, 450);
            //HelloApplication.class.getResource("demo.css");
            scene2.getStylesheets().addAll(this.getClass().getResource("demo.css").toExternalForm());
            //changeBackground(scene2, "pozavizitator");

            primaryStage.setScene(scene2);
            //pentru setare minim si maxim
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(500);

            primaryStage.show();
            //TO DO add information off inmate
            // tree table view for sentences
            mainVbox = (VBox) scene2.lookup("#mainVBox");
            programareTab = (AnchorPane) scene2.lookup("#programareTab");
            tabPane = (TabPane) scene2.lookup("#mainContainer");
            backButton = new Tab("Back");
            listViewDetinut = (ListView<Inmates>) scene2.lookup("#listViewDetinut");
            parentOfListView = (AnchorPane) scene2.lookup("#parentOfListView");

            tabPane.getTabs().add(backButton);
            // we just added/recognized all the needed object

            treeTableView = new TreeTableView<>();
            String css = getClass().getResource("tableViewVizitatori.css").toExternalForm();
            treeTableView.getStylesheets().add(css);



            resizeWindowWidth(tabPane, treeTableView, programareTab, mainVbox, scene2.getWidth());
            resizeWindowHeight(tabPane, treeTableView, programareTab, mainVbox, scene2.getHeight());
            resizeTable(treeTableView, 650);
            // we initialized the table

            programareTab.setStyle("-fx-padding: 20; -fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");
            parentOfListView.setStyle(" -fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");


            backButton.setOnSelectionChanged(event -> {
                HelloApplication newApplication = new HelloApplication();
                Stage newStage = new Stage();
                primaryStage.close();
                newApplication.start(newStage);
            });
            // on going back

            primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                // Do whatever you wantr
                resizeWindowWidth(tabPane, treeTableView, programareTab, mainVbox, newVal);
            });

            primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
                // Do whatever you want
                resizeWindowHeight(tabPane, treeTableView, programareTab, mainVbox, newVal);
            });



            // Set data
            // now we add it to the panel
            setUpDetaliiProgramari(primaryStage, treeTableView, databaseConnector, newUser);
            setUpDetaliiDetinut(databaseConnector, newUser);


            programareTab.setPrefWidth(scene2.getWidth());
            programareTab.setPrefHeight(scene2.getHeight());

            programareTab.getChildren().add(treeTableView);
            //programareTab.getChildren().add(table);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading FXML file. Ensure the file path is correct and the file exists.");
        }
    }

    private void setUpDetaliiProgramari(Stage primaryStage, TreeTableView<Visit> treeTableView, DatabaseConnector databaseConnector, User newUser) {

        treeTableView.widthProperty().addListener((obs, oldVal, newVal) -> {
            resizeTable(treeTableView, newVal);
        });

        treeTableView.setFixedCellSize(60.0);
        TreeItem<Visit> rootItem = new TreeItem<>(new Visit("", "", "", "", ""));
        rootItem.setExpanded(true);
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);
        // dau resize-ul acum

        // Coloane

        TreeTableColumn<Visit, String> col3 = createColumn("Start Time", person -> person.getStartTime());
        TreeTableColumn<Visit, String> col4 = createColumn("End Time", person -> person.getEndTime());
        TreeTableColumn<Visit, String> col5 = createColumn("Name of Inmate", person -> person.getInmateName());


        treeTableView.getColumns().addAll(col3, col4, col5);
        ArrayList<Visit> data = getDataFromDatabase(databaseConnector, newUser);
        addDataToTreeTable(rootItem, data);

        AnchorPane.setTopAnchor(treeTableView, 25.5);
        AnchorPane.setLeftAnchor(treeTableView, 10.0);
        AnchorPane.setRightAnchor(treeTableView, 10.0);
        AnchorPane.setBottomAnchor(treeTableView, 10.0);
    }

    private void addDataToTreeTable(TreeItem<Visit> rootItem, ArrayList<Visit> data) {
        for(int i = 0; i < data.size(); i++) {

            TreeItem<Visit> item = new TreeItem<>(data.get(i));

            rootItem.getChildren().add(item);
        }
    }

    private ArrayList<Visit> getDataFromDatabase(DatabaseConnector databaseConnector, User newUser) {
        int idVizitator = databaseConnector.getIdVizitatorPentruUtilizator(newUser.getId());
        ArrayList<Visit> newList = new ArrayList<>();
        newList = databaseConnector.getVisits(newUser.getId());
        return newList;

    }

    private void resizeWindowHeight(TabPane tabPane, TreeTableView<Visit> table, AnchorPane programareTab, VBox mainVbox, Number newVal) {
        tabPane.setPrefHeight(newVal.doubleValue());
        programareTab.setPrefHeight(newVal.doubleValue());
        mainVbox.setPrefHeight(newVal.doubleValue());
        double actualHeigth = newVal.doubleValue();


        parentOfListView.prefHeightProperty().bind(mainVbox.prefHeightProperty());

        table.setPrefHeight(newVal.doubleValue());
    }

    private void resizeWindowWidth(TabPane tabPane, TreeTableView<Visit> table, AnchorPane programareTab, VBox mainVbox, Number width) {
        // we resize everything based on width
        tabPane.setPrefWidth(width.doubleValue());
        table.setPrefWidth(width.doubleValue());
        // need to do this for the padding to be the same regardless if you change width or height
        //listViewDetinut.setPadding(new Insets(listViewDetinut.getHeight() * 0.05, width.doubleValue() * 0.1, listViewDetinut.getHeight() * 0.1, width.doubleValue() * 0.1));
        programareTab.setPrefWidth(width.doubleValue());
        mainVbox.setPrefWidth(width.doubleValue());

        parentOfListView.prefWidthProperty().bind(mainVbox.widthProperty());
    }
    private void setUpDetaliiDetinut(DatabaseConnector databaseConnector, User newUser){


        listViewDetinut.getItems().add(new Inmates());
        getElementsToListViewFromDatabase(databaseConnector, newUser);

        listViewDetinut.prefHeightProperty().bind(parentOfListView.prefHeightProperty());
        listViewDetinut.prefWidthProperty().bind(parentOfListView.prefWidthProperty());


        listViewDetinut.setCellFactory( param -> new ListItem(listViewDetinut, databaseConnector));


    }

    private void getElementsToListViewFromDatabase(DatabaseConnector databaseConnector, User newUser) {
        ArrayList<Inmates> inmates = databaseConnector.getVisitedInmates(newUser.getId());
        listViewDetinut.getItems().clear();
        listViewDetinut.getItems().addAll(inmates);

    }

    private void changeBackground(Scene scene, String imageName) {
        String imagePath = HelloApplication.class.getResource("images/" + imageName + ".png").toExternalForm();
        scene.getRoot().setStyle(String.format(
                "-fx-background-image: url('%s'); " +
                        "-fx-background-position: center; " +
                        "-fx-background-repeat: no-repeat;",
                imagePath
        ));
    }

    private TreeTableColumn<Visit, String> createColumn(String title, Function<Visit, ObservableValue<String>> mapper) {
        TreeTableColumn<Visit, String> column = new TreeTableColumn<>(title);
        column.setCellValueFactory(cellData -> mapper.apply(cellData.getValue().getValue()));
        return column;
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    // we resize the width
    private void resizeTable(TreeTableView table, Number newVal) {
        double totalWidth = newVal.doubleValue();
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        for(int i = 0; i < table.getColumns().size(); i++) {
            TreeTableColumn tableColumn = (TreeTableColumn) table.getColumns().get(i);
            tableColumn.setPrefWidth(totalWidth * 0.15);
        }

    }
}
