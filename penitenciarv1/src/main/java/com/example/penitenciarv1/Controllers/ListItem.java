package com.example.penitenciarv1.Controllers;

import com.example.penitenciarv1.Entities.Inmates;
import com.example.penitenciarv1.HelloApplication;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ListItem extends ListCell<Inmates> {
    private final ImageView imageView = new ImageView(){
        @Override public double minHeight(double width) { return 80; }; //some hard stop value
        @Override public double minWidth(double height) { return 80; };
    }; // i've added this so the imageview can be resized
    // JavaFx ImageView won't resize on fitWidthProperty bind to VBox
    // on stackoverflow
    // we make a treetabelview with the sentences
    private final Label textLabel = new Label();
    private final Label iconLabel = new Label();
    // put everything up of here in a Vbox
    private final VBox imageBox = new VBox(imageView);
    private final VBox detailsDetinutHBox = new VBox(imageBox, textLabel, iconLabel);
    private final VBox sentinteDetinutVBox = new VBox();
    private final HBox layout = new HBox(detailsDetinutHBox, sentinteDetinutVBox);
    ;
    String imagePath = HelloApplication.class.getResource("images/pozadetinut.png").toExternalForm();
    Image image = new Image(imagePath);

    public ListItem(ListView<Inmates> listView) {
        super();
        iconLabel.setFont(Font.font(15.0)); // Configure font size
        layout.setMaxHeight(350);
        layout.setMinHeight(350);
        layout.prefWidthProperty().bind(listView.widthProperty()/*.divide(10/9)*/);

        //i want each element to be fixed vertical and resizeable horizontal
        modifyLayouts();
    }

    public void modifyLayouts(){

        detailsDetinutHBox.prefWidthProperty().bind(layout.widthProperty().multiply(0.3));
        sentinteDetinutVBox.prefWidthProperty().bind(layout.widthProperty().multiply(0.7));
        // set heights
        detailsDetinutHBox.prefHeightProperty().bind(layout.heightProperty());
        sentinteDetinutVBox.prefHeightProperty().bind(layout.heightProperty());
        // we finished with the big layouts
        // we got to what is in each
        detailsDetinutHBox.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        // for debug reasons only
        // we test if the boxes are in parallel
        textLabel.prefHeightProperty().bind(detailsDetinutHBox.heightProperty().multiply(0.15));
        textLabel.prefWidthProperty().bind(detailsDetinutHBox.widthProperty());


        iconLabel.prefHeightProperty().bind(detailsDetinutHBox.heightProperty().multiply(0.15));
        iconLabel.prefWidthProperty().bind(detailsDetinutHBox.widthProperty());
        iconLabel.setWrapText(true);

        // se modifica toate dupa layout size
        imageBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        imageBox.prefWidthProperty().bind(detailsDetinutHBox.widthProperty());
        imageBox.prefHeightProperty().bind(detailsDetinutHBox.heightProperty().multiply(0.7));


        imageView.fitWidthProperty().bind(imageBox.widthProperty());
        imageView.fitHeightProperty().bind(imageBox.heightProperty());
        imageView.setPreserveRatio(true);


        imageView.setStyle("-fx-alignment: center");


        // imaginea va fi 1/2 din detaliiDetinutVBox
        layout.getStylesheets().add(getClass().getResource("ListItem.css").toExternalForm());
        iconLabel.getStyleClass().add(getClass().getResource("ListItem.css").toExternalForm());
        textLabel.getStyleClass().add(getClass().getResource("ListItem.css").toExternalForm());

    }
    @Override
    protected void updateItem(Inmates person, boolean empty) {
        super.updateItem(person, empty);

        if (empty || person == null) {
            setText(null);
            setGraphic(null);

        } else {
            textLabel.setText(person.getName().get());



            imageView.setImage(image);
            iconLabel.setText("Sentence left: (" + person.getSentenceRemained().get() + ")");
            modifyLayouts();
            setGraphic(layout); // Set the VBox as the cell's graphic
        }
    }
}


