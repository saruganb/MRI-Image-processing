package com.example.labb5.view;

import com.example.labb5.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.io.File;

public class ImageProcessingView extends VBox{

    private final ImageProcessingModel model;
    private Image image = null;
    private Stage primaryStage;
    private boolean isImage;
    private BorderPane bp = new BorderPane();
    private FileChooser fileChooser = new FileChooser();
    private File imageFile;
    private LineChart[] lineCharts = new LineChart[3];
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public ImageProcessingView(ImageProcessingModel model, Stage primaryStage) {
        super();

        this.model = model;
        this.primaryStage = primaryStage;
        this.image = null;
        this.isImage = false;

        ImageProcessingController ipc = new ImageProcessingController(this,model);
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.png", ".jpg",
                "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
        MenuBar menu = menu(ipc);
        this.getChildren().addAll(menu,bp);
    }

    protected Pane newImage(){
        FlowPane root = new FlowPane();
        imageFile = fileChooser.showOpenDialog(primaryStage);
        try{
            image = new Image(imageFile.toURI().toString());
        }
        catch (NullPointerException n){
        }
        ImageView iv = new ImageView(image);
        iv.setFitWidth(450);
        iv.setFitHeight(450);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(iv);

        return root;
    }

    private void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }

    public MenuBar menu(ImageProcessingController ipc){
        MenuBar menubar = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuGenerate = new Menu("Generate");

        menubar.getMenus().addAll(menuFile, menuGenerate);

        MenuItem menuItemHistogram = new MenuItem("Histogram");
        MenuItem menuItemIncrease = new MenuItem("Increase");
        MenuItem menuItemInvert = new MenuItem("Invert");
        MenuItem menuItemContrast = new MenuItem("Contrast");

        menuGenerate.getItems().addAll(menuItemHistogram, menuItemIncrease, menuItemInvert, menuItemContrast);

        MenuItem menuItemClose = new MenuItem("Close");
        MenuItem menuItemNewImage = new MenuItem("New Image");
        MenuItem menuItemSaveImage = new MenuItem("Save Image");
        MenuItem menuItemGetOg = new MenuItem("Get original image");

        menuFile.getItems().addAll(menuItemClose,menuItemNewImage,menuItemSaveImage, menuItemGetOg);

        menuItemClose.setOnAction( e ->{
            primaryStage.close();
        });

        menuItemGetOg.setOnAction(e ->{
            if(checkBp()){
                ipc.handleSetOriginalImage();
            }

        });

        menuItemSaveImage.setOnAction(e ->{
            if(checkBp()){
                Label label = new Label("Save as: ");
                TextField textField = new TextField();
                Button bnEnter = new Button("Enter");
                HBox hBox = new HBox(5);

                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().addAll(label, textField,bnEnter);

                bp.setLeft(hBox);

                bnEnter.setOnAction(b ->{
                    ipc.handleSaveImage(image, imageFile,textField.getText());
                });

                setBottomLabel("Save Image");
            }
        });

        menuItemNewImage.setOnAction(e ->{
            bp.setPadding(new Insets(20));
            if(isImage && bp.getChildren().size() > 1){
                int size =  bp.getChildren().size();
                bp.getChildren().subList(0, size).clear();
                if(this.getChildren().size() > 2) this.getChildren().remove(2);

                bp.setRight(newImage());
            }
            else if(isImage){
                if(this.getChildren().size() > 2) this.getChildren().remove(2);
                bp.getChildren().remove(0);
                bp.setRight(newImage());

            }
            else{
                bp.setRight(newImage());
                isImage = true;
            }

            if(image == null) isImage = false;
            else ipc.handlePixel(image);
        });

        menuItemHistogram.setOnAction(e -> {
            if(checkBp()){
                ipc.handleHistogramSelected();
            }
        });

        menuItemIncrease.setOnAction(e -> {
            if(checkBp()){

                HBox hb = new HBox(10);
                Button bnBlue = new Button("Blue");
                Button bnRed = new Button("Red");
                Button bnGreen = new Button("Green");
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().addAll(bnRed, bnGreen, bnBlue);

                bp.setLeft(hb);

                bnRed.setOnAction(c -> {
                    ManipulateImage mi = new Increase(2,1,1);
                    ipc.handleProcessImage(mi);

                });
                bnGreen.setOnAction(c -> {
                    ManipulateImage mi = new Increase(1,2,1);
                    ipc.handleProcessImage(mi);
                });
                bnBlue.setOnAction(c -> {
                    ManipulateImage mi = new Increase(1,1,2);
                    ipc.handleProcessImage(mi);
                });
                setBottomLabel("Increase");
            }
        });

        menuItemInvert.setOnAction(e -> {
            if(checkBp()){
                ManipulateImage mi = new Invert();
                ipc.handleProcessImage(mi);
                setBottomLabel("Inverted");
            }
        });

        menuItemContrast.setOnAction(e -> {
            if(checkBp()){

                Slider windowSl = new Slider();
                Slider levelSl = new Slider();

                createSliders(windowSl,levelSl);

                final double[] windowValue = new double[1];
                final double[] levelValue = new double[1];

                showWAndL(0,0);
                windowSl.valueProperty().addListener(ov -> {
                    windowValue[0] = (int) Math.round(windowSl.getValue());
                    ManipulateImage mi = new Contrast((int) windowValue[0], (int) levelValue[0]);
                    ipc.handleProcessImage(mi);
                    showWAndL((int)windowValue[0],(int)levelValue[0]);
                });

                levelSl.valueProperty().addListener(ov -> {
                    levelValue[0] = (int) Math.round(levelSl.getValue());
                    ManipulateImage mi = new Contrast((int) windowValue[0], (int) levelValue[0]);
                    ipc.handleProcessImage(mi);
                    showWAndL((int)windowValue[0],(int)levelValue[0]);
                });
            }
        });

        return menubar;

    }
    private void setBottomLabel(String name){
        if(this.getChildren().size() > 2) this.getChildren().remove(2);
        this.getChildren().add(new Label(name));

    }
    private void showWAndL(int w, int l){
        if(this.getChildren().size() > 2) this.getChildren().remove(2);
        this.getChildren().add((new Label(" Window: " + w + " Level: " + l)));
    }

    private void createSliders(Slider windowSl, Slider levelSl){
        Label window = new Label("Window");
        Label level = new Label("Level");

        windowSl.setOrientation(Orientation.HORIZONTAL);
        windowSl.setShowTickLabels(true);
        windowSl.setShowTickMarks(true);
        windowSl.setMin(0);
        windowSl.setMinorTickCount(64);
        windowSl.setMax(255);

        levelSl.setOrientation(Orientation.HORIZONTAL);
        levelSl.setShowTickLabels(true);
        levelSl.setShowTickMarks(true);
        levelSl.setMin(0);
        levelSl.setMajorTickUnit(64);
        levelSl.setMax(255);


        VBox vb = new VBox(10);
        vb.setAlignment(Pos.TOP_LEFT);
        vb.getChildren().addAll(window, windowSl, level, levelSl);
        vb.setStyle("-fx-background-color: DAE6F3;");

        bp.setCenter(vb);

    }

    private boolean checkBp() {
        if (isImage) {
            if (bp.getChildren().size() > 1) {
                int size = bp.getChildren().size();
                if (size > 1) {
                    bp.getChildren().subList(1, size).clear();
                }
            }
            if(this.getChildren().size() > 2) this.getChildren().remove(2);
            return true;
        }
        showAlert("No image");
        return false;
    }

    protected void updateGraphView(){
        Button update = new Button("Update");
        bp.setBottom(update);
        drawHistogramGraph();
        update.setOnAction(e ->{
            LineChart lineChart = getNextLineCharts();
            FlowPane fp = new FlowPane();
            fp.setAlignment(Pos.CENTER);
            fp.getChildren().add(lineChart);
            fp.setStyle("-fx-background-color: DAE6F3;");
            bp.setLeft(fp);
            UpdateLineChartOrder();
            setBottomLabel("Histogram Generated");
        });
    }

    private LineChart getNextLineCharts() {
        return lineCharts[0];
    }

    private void UpdateLineChartOrder() {
        LineChart temp = lineCharts[2];
        lineCharts[2] = lineCharts[0];
        lineCharts[0] = lineCharts[1];
        lineCharts[1] = temp;
    }

    private void drawHistogramGraph(){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();

        lineCharts[0] = new LineChart<Number, Number>(xAxis, yAxis);
        lineCharts[1] = new LineChart<Number, Number>(xAxis1, yAxis1);
        lineCharts[2] = new LineChart<Number, Number>(xAxis2, yAxis2);

        XYChart.Series seriesR = new XYChart.Series();
        XYChart.Series seriesG = new XYChart.Series();
        XYChart.Series seriesB = new XYChart.Series();
        seriesR.setName("red");
        seriesG.setName("green");
        seriesB.setName("blue");

        int[] tmpR = model.getCountOfPixels(0);
        int[] tmpG = model.getCountOfPixels(1);
        int[] tmpB = model.getCountOfPixels(2);

        for (int i = 0; i < 256; i++) {
            seriesR.getData().add(new XYChart.Data(i, tmpR[i]));
            seriesG.getData().add(new XYChart.Data(i, tmpG[i]));
            seriesB.getData().add(new XYChart.Data(i, tmpB[i]));
        }

        lineCharts[0].getData().add(seriesR);
        lineCharts[1].getData().add(seriesG);
        lineCharts[2].getData().add(seriesB);

    }
    protected void updateImage(Image image){

        this.image = image;
        ImageView iv = new ImageView(image);
        iv.setFitWidth(450);
        iv.setFitHeight(450);
        FlowPane p = new FlowPane();
        p.setAlignment(Pos.CENTER);
        p.getChildren().add(0,iv);

        BorderPane f = new BorderPane();
        bp.getChildren().remove(0);
        bp.setRight(p);
        if(bp.getChildren().size() > 1){
            f.getChildren().add(bp.getChildren().get(0));
            bp.getChildren().add(f);
        }

    }
}
