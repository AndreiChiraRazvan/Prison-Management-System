package com.example.penitenciarv1.Listeners;

import com.example.penitenciarv1.Database.DatabaseConnector;
import com.example.penitenciarv1.Interfaces.GuardianInterface;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Function;


public class DynamicScalingAppIntGardianDetinut extends Application {

    private int idUserGardian;

    public DynamicScalingAppIntGardianDetinut() {

    }
    public DynamicScalingAppIntGardianDetinut(int idUserGardian) {
        this.idUserGardian = idUserGardian;
    }

    public static class Person {
        private final StringProperty id;
        private final StringProperty nume;
        private final StringProperty sentinta;
        private final StringProperty celula;
        private final StringProperty profesie;

        public Person(String id, String name, String sentence, String cell, String profession) {
            this.id = new SimpleStringProperty(id);
            this.nume = new SimpleStringProperty(name);
            this.sentinta = new SimpleStringProperty(sentence);
            this.celula = new SimpleStringProperty(cell);
            this.profesie = new SimpleStringProperty(profession);
        }

        public StringProperty idProperty() {
            return id;
        }

        public StringProperty nameProperty() {
            return nume;
        }

        public StringProperty sentenceProperty() {
            return sentinta;
        }

        public StringProperty cellProperty() {
            return celula;
        }

        public StringProperty professionProperty() {
            return profesie;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();

        // Configurare TreeTableView
        TreeTableView<Person> treeTableView = new TreeTableView<>();
        TreeItem<Person> rootItem = new TreeItem<>(new Person("", "", "", "", ""));
        rootItem.setExpanded(true);
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);

        // Coloane
        TreeTableColumn<Person, String> col1 = createColumn("ID", person -> person.idProperty());
        TreeTableColumn<Person, String> col2 = createColumn("Nume și Prenume", person -> person.nameProperty());
        TreeTableColumn<Person, String> col3 = createColumn("Sentința", person -> person.sentenceProperty());
        TreeTableColumn<Person, String> col4 = createColumn("Celula", person -> person.cellProperty());
        TreeTableColumn<Person, String> col5 = createColumn("Profesia", person -> person.professionProperty());
        TreeTableColumn<Person, String> col6 = new TreeTableColumn<>("Actions");
        col6.setCellFactory(param -> new TreeTableCell<Person, String>() {
            final Button addToSolitude = new Button("Add to Solitude");
            final Button cancelVisit = new Button("Cancel Visit");
            final Button addTask = new Button("Add Task");
            final Button moveToAnotherCell = new Button("Move To Another Cell");

            // HBox ca sa tina mai multe butoane
            final HBox buttonContainer = new HBox(10);  // Horizontal box with 10px spacing
            {
                buttonContainer.getChildren().addAll(addToSolitude, cancelVisit, addTask, moveToAnotherCell);
                buttonContainer.setSpacing(5);  // Optional spacing between buttons
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    addToSolitude.setOnAction(event -> {
                        Person person = getTreeTableRow().getItem();
                        if (person != null) {
                            System.out.println("Action for: " + person.nameProperty().get());
                        }
                    });
                    setGraphic(addToSolitude);
                    setText(null);

                    cancelVisit.setOnAction(event -> {
                        Person person = getTreeTableRow().getItem();
                        if (person != null) {
                            System.out.println("Action for: " + person.nameProperty().get());
                        }
                    });
                    setGraphic(buttonContainer);
                    setText(null);
                }
            }
        });

        treeTableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        // Autosize: Listener pentru ajustarea automată a lățimii coloanelor
        treeTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            col1.setPrefWidth(totalWidth * 0.10);
            col2.setPrefWidth(totalWidth * 0.15);
            col3.setPrefWidth(totalWidth * 0.20);
            col4.setPrefWidth(totalWidth * 0.10);
            col5.setPrefWidth(totalWidth * 0.15);
            col6.setPrefWidth(totalWidth * 0.30);
        });


        // Încărcare date din baza de date
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Statement statement = dbConnector.conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id_detinut, nume, fK_id_celula,profesie FROM Detinut");

            while (resultSet.next()) {
                String id = resultSet.getString("id_detinut");
                String name = resultSet.getString("nume");
                // aici apeleaz un query care imi zice suma ramasa in datetime a sentintei
                String sentence = String.valueOf(dbConnector.getRemainingSentence(Integer.valueOf(id)));  // Valoare implicită
                String cell = resultSet.getString("fk_id_celula");
                String profession = resultSet.getString("profesie");; // Valoare implicită

                TreeItem<Person> item = new TreeItem<>(new Person(id, name, sentence, cell, profession));
                rootItem.getChildren().add(item);
            }

            System.out.println("Numarul de elemente incarcare: " + rootItem.getChildren().size());
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println("Adauga Procedura din getRemainingSentence");
            e.printStackTrace();
        }

        // Layout
        AnchorPane.setTopAnchor(treeTableView, 25.5);
        AnchorPane.setLeftAnchor(treeTableView, 10.0);
        AnchorPane.setRightAnchor(treeTableView, 10.0);
        AnchorPane.setBottomAnchor(treeTableView, 10.0);

        Button goBack = new Button("Go Back");
        goBack.setOnAction(event -> {
            GuardianInterface gi = new GuardianInterface(idUserGardian);
            Stage newStage = new Stage();
            primaryStage.close();
            gi.start(newStage);
        });
        goBack.setAlignment(Pos.TOP_LEFT);
        goBack.setPrefHeight(20);

        root.getChildren().addAll(treeTableView, goBack);

        // Scenă
        Scene scene = new Scene(root, 1400, 600);
        primaryStage.setTitle("Tabel cu Deținuți (Autosize)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TreeTableColumn<Person, String> createColumn(String title, Function<Person, ObservableValue<String>> mapper) {
        TreeTableColumn<Person, String> column = new TreeTableColumn<>(title);
        column.setCellValueFactory(cellData -> mapper.apply(cellData.getValue().getValue()));
        return column;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
