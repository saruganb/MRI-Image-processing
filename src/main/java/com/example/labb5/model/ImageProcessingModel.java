package com.example.labb5.model;

import java.awt.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
/**
 * This class is used to convert pixel matrix to and from an image and to calculate histogram
 */
public class ImageProcessingModel {
    final int INTENSITY = 256;
    private int[] rValue;
    private int[] aValue;
    private int[] gValue;
    private int[] bValue;

    public ImageProcessingModel(){

    }
    /**
     * This method converts an Image to a pixel matrix
     *
     * @param image the image
     * @return a pixel matrix of image
     */

    public static int[][] getPixelFromImage(Image image){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        int[][] pixelMatrix = new int[width][height];
        PixelReader reader = image.getPixelReader();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // store alpha, red, green, blue stored in an int
                pixelMatrix[x][y] = reader.getArgb(x, y);

            }
        }
        return pixelMatrix;
    }

    /**
     * This method converts a pixel matrix to an Image
     *
     * @param pixelMatrix the pixel matrix
     * @return a image
     */

    public static Image getImageFromPixel(int [][] pixelMatrix){
        WritableImage image = new WritableImage(pixelMatrix.length,pixelMatrix[0].length);
        PixelWriter pixelWriter = image.getPixelWriter();
        for(int x = 0; x < pixelMatrix.length; x++){
            for(int y = 0; y < pixelMatrix[0].length; y++){

                int color = pixelMatrix[x][y];
                pixelWriter.setArgb(x,y,color);
            }
        }
        return image;
    }

    /**
     * Extract the color intensity of argb from pixel matrix
     * Stores each in seperated arrays
     * @param pixelMatrix
     */

    public void calculateHistogram(int[][] pixelMatrix){

        rValue = new int [pixelMatrix.length*pixelMatrix[0].length];
        aValue = new int [pixelMatrix.length*pixelMatrix[0].length];
        bValue = new int [pixelMatrix.length*pixelMatrix[0].length];
        gValue = new int [pixelMatrix.length*pixelMatrix[0].length];
        int i = 0;
        for (int x = 0; x < pixelMatrix.length; x++) {
            for (int y = 0; y < pixelMatrix[0].length; y++) {
                int color = pixelMatrix[x][y];
                this.aValue[i] = getA(color);
                this.rValue[i] = getR(color);
                this.gValue[i] = getG(color);
                this.bValue[i] = getB(color);
                i++;
            }
        }

    }
    /**
     * Calculate the frequency of every intensity between 0-255
     * return an int array
     */


    public int[] getCountOfPixels(int num){
        int[] colorValues;
        if(num == 0) colorValues = getrValue();
        else if(num == 1) colorValues = getbValue();
        else colorValues = getgValue();

        int[] count = new int[INTENSITY];
        for(int i = 0; i < INTENSITY; i++){
            for(int j = 0; j < colorValues.length; j++){
                if(colorValues[j] == i){
                    count[i]++;
                }
            }
        }

        return count;
    }
    /**
     * Mask out alpha
     * @param color the color in pixel
     * @return alpha intensity
     */

    public int getA(int color){

       return ((color >> 24) & 0xff);

    }
    /**
     * Mask out red
     * @param color the color in pixel
     * @return red intensity
     */
    public int getR(int color){
        return ((color >> 16) & 0xff);
    /**
    * Mask out green
    * @param color the color in pixel
     * @return green intensity
    */

    }
    public int getG(int color){
        return  ((color >> 8) & 0xff);
    /**
    * Mask out blue
    * @param color the color in pixel* @return blue intensity
    */
    }
    public int getB(int color){
        return  ((color) & 0xff);

    }

    public int[] getrValue() {
        return rValue;
    }

    public int[] getaValue() {
        return aValue;
    }

    public int[] getgValue() {
        return gValue;
    }

    public int[] getbValue() {
        return bValue;
    }

}
