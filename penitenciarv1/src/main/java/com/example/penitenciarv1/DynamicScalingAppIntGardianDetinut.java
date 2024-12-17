package com.example.penitenciarv1;
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.scene.Scene;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TreeItem;
//import javafx.scene.control.TreeTableCell;
//import javafx.scene.control.TreeTableColumn;
//import javafx.scene.control.TreeTableView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.function.Function;
//import javafx.beans.value.ObservableValue;
//
//public class DynamicScalingApp extends Application {
//
//
//    public static class Person {
//        private final StringProperty id;
//        private final StringProperty name;
//        private final StringProperty sentence;
//        private final StringProperty cell;
//        private final StringProperty profession;
//
//        public Person(String id, String name, String sentence, String cell, String profession) {
//            this.id = new SimpleStringProperty(id);
//            this.name = new SimpleStringProperty(name);
//            this.sentence = new SimpleStringProperty(sentence);
//            this.cell = new SimpleStringProperty(cell);
//            this.profession = new SimpleStringProperty(profession);
//        }
//
//        public StringProperty idProperty() { return id; }
//        public StringProperty nameProperty() { return name; }
//        public StringProperty sentenceProperty() { return sentence; }
//        public StringProperty cellProperty() { return cell; }
//        public StringProperty professionProperty() { return profession; }
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        AnchorPane root = new AnchorPane();
//
//
//        TreeTableView<Person> treeTableView = new TreeTableView<>();
//
//
//        TreeItem<Person> rootItem = new TreeItem<>(new Person("", "", "", "", ""));
//        rootItem.setExpanded(true);
//        treeTableView.setRoot(rootItem);
//        treeTableView.setShowRoot(false);
//
//
//        TreeTableColumn<Person, String> col1 = createTextAreaColumn("ID", person -> person.idProperty());
//        TreeTableColumn<Person, String> col2 = createTextAreaColumn("Nume și Prenume", person -> person.nameProperty());
//        TreeTableColumn<Person, String> col3 = createTextAreaColumn("Sentința", person -> person.sentenceProperty());
//        TreeTableColumn<Person, String> col4 = createTextAreaColumn("Celula", person -> person.cellProperty());
//        TreeTableColumn<Person, String> col5 = createTextAreaColumn("Profesia", person -> person.professionProperty());
//
//
//        treeTableView.getColumns().addAll(col1, col2, col3, col4, col5);
//
//
//        for (int i = 1; i <= 10; i++) {
//            TreeItem<Person> item = new TreeItem<>(new Person(
//                    String.valueOf(i),
//                    "Nume Prenume " + i,
//                    "Sentința " + i,
//                    "Celula " + i,
//                    "Profesia " + i
//            ));
//            rootItem.getChildren().add(item);
//        }
//
//
//        treeTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
//            double totalWidth = newWidth.doubleValue();
//            col1.setPrefWidth(totalWidth * 0.10); // 10% din lățime
//            col2.setPrefWidth(totalWidth * 0.30); // 30% din lățime
//            col3.setPrefWidth(totalWidth * 0.20); // 20% din lățime
//            col4.setPrefWidth(totalWidth * 0.20); // 20% din lățime
//            col5.setPrefWidth(totalWidth * 0.20); // 20% din lățime
//        });
//
//
//        AnchorPane.setTopAnchor(treeTableView, 10.0);
//        AnchorPane.setLeftAnchor(treeTableView, 10.0);
//        AnchorPane.setRightAnchor(treeTableView, 10.0);
//        AnchorPane.setBottomAnchor(treeTableView, 10.0);
//
//        root.getChildren().add(treeTableView);
//
//
//        Scene scene = new Scene(root, 800, 600);
//
//
//        primaryStage.setTitle("Dynamic Scaling Table with TextAreas");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//
//    private TreeTableColumn<Person, String> createTextAreaColumn(String title, Function<Person, ObservableValue<String>> mapper) {
//        TreeTableColumn<Person, String> column = new TreeTableColumn<>(title);
//        column.setCellValueFactory(cellData -> mapper.apply(cellData.getValue().getValue()));
//
//        column.setCellFactory(col -> new TreeTableCell<>() {
//            private final TextArea textArea = new TextArea();
//
//            {
//                textArea.setWrapText(true);
//                textArea.setPrefHeight(Double.MAX_VALUE); // Face TextArea să ocupe toată înălțimea celulei
//            }
//
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null) {
//                    setGraphic(null);
//                } else {
//                    textArea.setText(item);
//
//
//                    textArea.textProperty().addListener((obs, oldValue, newValue) -> {
//                        TreeItem<Person> treeItem = getTreeTableRow().getTreeItem();
//                        if (treeItem != null && treeItem.getValue() != null) {
//                            Person person = treeItem.getValue();
//                            if (getTableColumn().getText().equals("ID")) {
//                                person.idProperty().set(newValue);
//                            } else if (getTableColumn().getText().equals("Nume și Prenume")) {
//                                person.nameProperty().set(newValue);
//                            } else if (getTableColumn().getText().equals("Sentința")) {
//                                person.sentenceProperty().set(newValue);
//                            } else if (getTableColumn().getText().equals("Celula")) {
//                                person.cellProperty().set(newValue);
//                            } else if (getTableColumn().getText().equals("Profesia")) {
//                                person.professionProperty().set(newValue);
//                            }
//                        }
//                    });
//
//                    setGraphic(textArea);
//                }
//            }
//        });
//
//        return column;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//////////////////////////////////////////////////////
//TODO: DIN BAZA DE DATE
///////////////////////////////////////////////////////
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Function;
import javafx.beans.value.ObservableValue;

public class DynamicScalingApp extends Application {


    public static class Person {
        private final StringProperty id;
        private final StringProperty name;
        private final StringProperty sentence;
        private final StringProperty cell;
        private final StringProperty profession;

        public Person(String id, String name, String sentence, String cell, String profession) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.sentence = new SimpleStringProperty(sentence);
            this.cell = new SimpleStringProperty(cell);
            this.profession = new SimpleStringProperty(profession);
        }

        public StringProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public StringProperty sentenceProperty() { return sentence; }
        public StringProperty cellProperty() { return cell; }
        public StringProperty professionProperty() { return profession; }
    }

    @Override
    public void start(Stage primaryStage) {

        AnchorPane root = new AnchorPane();


        TreeTableView<Person> treeTableView = new TreeTableView<>();


        TreeItem<Person> rootItem = new TreeItem<>(new Person("", "", "", "", ""));
        rootItem.setExpanded(true);
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);


        TreeTableColumn<Person, String> col1 = createTextAreaColumn("ID", person -> person.idProperty());
        TreeTableColumn<Person, String> col2 = createTextAreaColumn("Nume și Prenume", person -> person.nameProperty());
        TreeTableColumn<Person, String> col3 = createTextAreaColumn("Sentința", person -> person.sentenceProperty());
        TreeTableColumn<Person, String> col4 = createTextAreaColumn("Celula", person -> person.cellProperty());
        TreeTableColumn<Person, String> col5 = createTextAreaColumn("Profesia", person -> person.professionProperty());


        treeTableView.getColumns().addAll(col1, col2, col3, col4, col5);


        populateTableFromDatabase(rootItem);



        treeTableView.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            col1.setPrefWidth(totalWidth * 0.10); // 10% din lățime
            col2.setPrefWidth(totalWidth * 0.30); // 30% din lățime
            col3.setPrefWidth(totalWidth * 0.20); // 20% din lățime
            col4.setPrefWidth(totalWidth * 0.20); // 20% din lățime
            col5.setPrefWidth(totalWidth * 0.20); // 20% din lățime
        });

        // Adaugare tabel in AnchorPane
        AnchorPane.setTopAnchor(treeTableView, 10.0);
        AnchorPane.setLeftAnchor(treeTableView, 10.0);
        AnchorPane.setRightAnchor(treeTableView, 10.0);
        AnchorPane.setBottomAnchor(treeTableView, 10.0);

        root.getChildren().add(treeTableView);

        // Creare scena
        Scene scene = new Scene(root, 800, 600);

        // Configurare stage
        primaryStage.setTitle("Dynamic Scaling Table with TextAreas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metoda pentru popularea tabelului din baza de date
    private void populateTableFromDatabase(TreeItem<Person> rootItem) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_detinut, nume, sentinta, cell, profession FROM persons")) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String sentence = resultSet.getString("sentence");
                String cell = resultSet.getString("cell");
                String profession = resultSet.getString("profession");

                TreeItem<Person> item = new TreeItem<>(new Person(id, name, sentence, cell, profession));
                rootItem.getChildren().add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metoda pentru crearea unei coloane cu TextArea
    private TreeTableColumn<Person, String> createTextAreaColumn(String title, Function<Person, ObservableValue<String>> mapper) {
        TreeTableColumn<Person, String> column = new TreeTableColumn<>(title);
        column.setCellValueFactory(cellData -> mapper.apply(cellData.getValue().getValue()));

        column.setCellFactory(col -> new TreeTableCell<>() {
            private final TextArea textArea = new TextArea();

            {
                textArea.setWrapText(true);
                textArea.setPrefHeight(Double.MAX_VALUE); // Face TextArea să ocupe toată înălțimea celulei
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    textArea.setText(item);

                    // Listener pentru actualizarea valorilor din modelul de date
                    textArea.textProperty().addListener((obs, oldValue, newValue) -> {
                        TreeItem<Person> treeItem = getTreeTableRow().getTreeItem();
                        if (treeItem != null && treeItem.getValue() != null) {
                            Person person = treeItem.getValue();
                            if (getTableColumn().getText().equals("ID")) {
                                person.idProperty().set(newValue);
                            } else if (getTableColumn().getText().equals("Nume și Prenume")) {
                                person.nameProperty().set(newValue);
                            } else if (getTableColumn().getText().equals("Sentința")) {
                                person.sentenceProperty().set(newValue);
                            } else if (getTableColumn().getText().equals("Celula")) {
                                person.cellProperty().set(newValue);
                            } else if (getTableColumn().getText().equals("Profesia")) {
                                person.professionProperty().set(newValue);
                            }
                        }
                    });

                    setGraphic(textArea);
                }
            }
        });

        return column;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
