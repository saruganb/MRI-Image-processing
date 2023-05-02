package com.example.labb5.view;

import com.example.labb5.model.Contrast;
import com.example.labb5.model.FileIO;
import com.example.labb5.model.ImageProcessingModel;
import com.example.labb5.model.ManipulateImage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import java.io.File;
import java.io.IOException;

public class ImageProcessingController {
    private final ImageProcessingModel model;
    private final ImageProcessingView view;
    private int[][] originalPixelMatrix;
    private int[][] pixelMatrix;
    private LineChart[] lineCharts = new LineChart[3];

    public ImageProcessingController(ImageProcessingView view, ImageProcessingModel model) {
        this.model = model;
        this.view = view;
    }

    public void handlePixel(Image image) {
        this.pixelMatrix = new int[(int) image.getWidth()][(int) image.getHeight()];
        this.pixelMatrix = ImageProcessingModel.getPixelFromImage(image);
        this.originalPixelMatrix = this.pixelMatrix;
    }

    public void handleHistogramSelected() {
        this.model.calculateHistogram(pixelMatrix);
        view.updateGraphView();
    }

    public void handleProcessImage(ManipulateImage mi) {
        this.pixelMatrix = mi.imageProcessing(this.pixelMatrix);
        Image image = ImageProcessingModel.getImageFromPixel(this.pixelMatrix);
        view.updateImage(image);

    }

    public void handleSetOriginalImage(){
        this.pixelMatrix = originalPixelMatrix;
        Image image = ImageProcessingModel.getImageFromPixel(this.pixelMatrix);
        view.updateImage(image);
    }

    public void handleSaveImage(Image image, File imageFile, String name) {
        try{
            FileIO.writeToFile(image, imageFile, name);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
