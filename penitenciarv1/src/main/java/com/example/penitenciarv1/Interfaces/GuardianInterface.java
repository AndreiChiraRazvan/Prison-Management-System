package com.example.penitenciarv1.Interfaces;

import com.example.penitenciarv1.Listeners.DynamicScalingAppIntGardianDetinut;
import com.example.penitenciarv1.Listeners.DynamicScalingAppIntGardianColegiBloc;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuardianInterface extends Application {

    private int idUserGardian;

    public GuardianInterface() {

    }
    public GuardianInterface(int idGardian) {
        this.idUserGardian = idGardian;
    }

    @Override
    public void start(Stage primaryStage) {

        // Pane 1 - Root with MenuBar and StackPane
        VBox root = new VBox();
        root.setPrefSize(600, 400);
        AnchorPane anchorPaneVbox1 = new AnchorPane();

        // Pane for Switching
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(600, 400);

        // MenuBar
        MenuBar menuBar = new MenuBar();
        Menu coleagues = new Menu("Coleagues");
        MenuItem sameDetentionBlock = new MenuItem("In the same detention block");
        coleagues.getItems().add(sameDetentionBlock);
        MenuItem wholePrison = new MenuItem("In the whole prison");
        coleagues.getItems().add(wholePrison);
        MenuItem substituteColeagues = new MenuItem("Substitute Coleagues");
        coleagues.getItems().add(substituteColeagues);
        Menu inmates = new Menu("Inmates");
        MenuItem prisonersOnShift = new MenuItem("Manage inmates");
        inmates.getItems().add(prisonersOnShift);

        Menu account = new Menu("Account");
        menuBar.getMenus().addAll(coleagues, inmates, account);

        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        anchorPaneVbox1.getChildren().add(menuBar);

        //Go to prisoners list
        prisonersOnShift.setOnAction(e -> {
            DynamicScalingAppIntGardianDetinut newInterfataPrisoners = new DynamicScalingAppIntGardianDetinut(idUserGardian);
            Stage newStage = new Stage();
            primaryStage.close();
            newInterfataPrisoners.start(newStage);
        });

        sameDetentionBlock.setOnAction(e -> {
            DynamicScalingAppIntGardianColegiBloc newInterfataColegi =  new DynamicScalingAppIntGardianColegiBloc(idUserGardian);
            System.out.println("guardian interfata" + idUserGardian);
            Stage newStage = new Stage();
            primaryStage.close();
            newInterfataColegi.start(newStage);
        });

        root.getChildren().addAll(anchorPaneVbox1, stackPane);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Guardian Interface");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// ImageView for Background
//        Image image = new Image(getClass().getResource("/com/example/penitenciarv1/images/cazulcorpA.png").toExternalForm());
//        if (image.isError()) {
//            System.out.println("Error loading image: " + image.getException());
//        }
//
//        ImageView imageView = new ImageView(image);
//        imageView.setPreserveRatio(true); // Maintain the aspect ratio
//        imageView.setSmooth(true);
//
//        // Bind the imageView size to the root pane size
//        imageView.fitWidthProperty().bind(stackPane.widthProperty()); // Bind to root pane's width
//        imageView.fitHeightProperty().bind(stackPane.heightProperty());