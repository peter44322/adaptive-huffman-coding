package com.company;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Gui extends Application  {
    Button compressButton;
    Button deCompressButton;
    FileChooser fileChooser;
    Label compressLabel;
    Label deCompressLabel;
    FileHandler file=new FileHandler();

    public Gui(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Adaptive Huffman");
        compressButton=new Button("  Compress   ");
        deCompressButton=new Button("De-Compress");
        compressLabel=new Label("  Compressed Text Will be shown here");
        deCompressLabel=new Label("De-Compressed Text Will be shown here");
        compressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser=new FileChooser();
                File f=fileChooser.showOpenDialog(primaryStage);




                String data=file.readFile(f.getAbsolutePath());
                file.writeFile("compressed.txt",data);

                String[] result = data.split("-");

                compressLabel.setText(result[0]);
            }
        });

        deCompressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser=new FileChooser();
                File f=fileChooser.showOpenDialog(primaryStage);
                //deCompressLabel.setText(ReadFile(f.getAbsolutePath()));
            }
        });

        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);


        GridPane.setConstraints(compressButton,0,0);
        GridPane.setConstraints(deCompressButton,0,1);
        GridPane.setConstraints(compressLabel,6,0);
        GridPane.setConstraints(deCompressLabel,6,1);
        gridPane.getChildren().addAll(compressButton,compressLabel,deCompressButton,deCompressLabel);


        Scene scene=new Scene(gridPane,400,120);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
