package com.example.labb5;

import com.example.labb5.model.ImageProcessingModel;
import com.example.labb5.view.ImageProcessingController;
import com.example.labb5.view.ImageProcessingView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ImageProcessingModel model = new ImageProcessingModel();
        ImageProcessingView view = new ImageProcessingView(model,primaryStage);

        Scene sc = new Scene(view, 1000, 580);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Image Processing");
        primaryStage.show();

    }
}
