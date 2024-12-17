package com.example.penitenciarv1;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Function;

class DatabaseConnector {
    private String user = "root";
    private String url = "jdbc:mysql://127.0.0.1:3306/penitenciar";
    private String password = "Baze_De_Date-2224";
    public Connection conn;

    public DatabaseConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = java.sql.DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("Database connection error");
            e.printStackTrace();
        }
    }
}

public class DynamicScalingAppIntGardianDetinut extends Application {

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

        public StringProperty idProperty() { return id; }
        public StringProperty nameProperty() { return nume; }
        public StringProperty sentenceProperty() { return sentinta; }
        public StringProperty cellProperty() { return celula; }
        public StringProperty professionProperty() { return profesie; }
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

        treeTableView.getColumns().addAll(col1, col2, col3, col4, col5);

        // Autosize: Listener pentru ajustarea automată a lățimii coloanelor
        treeTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            col1.setPrefWidth(totalWidth * 0.10);
            col2.setPrefWidth(totalWidth * 0.30);
            col3.setPrefWidth(totalWidth * 0.20);
            col4.setPrefWidth(totalWidth * 0.20);
            col5.setPrefWidth(totalWidth * 0.20);
        });

        // Încărcare date din baza de date
        DatabaseConnector dbConnector = new DatabaseConnector();
        try (Statement statement = dbConnector.conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id_detinut, nume, id_celula FROM Detinut");

            while (resultSet.next()) {
                String id = resultSet.getString("id_detinut");
                String name = resultSet.getString("nume");
                String sentence = "Necunoscut";  // Valoare implicită
                String cell = resultSet.getString("id_celula");
                String profession = "Necunoscut"; // Valoare implicită

                TreeItem<Person> item = new TreeItem<>(new Person(id, name, sentence, cell, profession));
                rootItem.getChildren().add(item);
            }

            System.out.println("Numarul de elemente incarcare: " + rootItem.getChildren().size());
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        // Layout
        AnchorPane.setTopAnchor(treeTableView, 10.0);
        AnchorPane.setLeftAnchor(treeTableView, 10.0);
        AnchorPane.setRightAnchor(treeTableView, 10.0);
        AnchorPane.setBottomAnchor(treeTableView, 10.0);
        root.getChildren().add(treeTableView);

        // Scenă
        Scene scene = new Scene(root, 800, 600);
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
