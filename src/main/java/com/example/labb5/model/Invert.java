package com.example.labb5.model;
/**
 * This class is used to invert the color of the image
 */
public class Invert implements ManipulateImage{

    public Invert(){

    }
    /**
     * Changes the order of rgb values and places it back to the pixel matrix
     *
     * @param pixelMatrix
     * @return a processed pixel matrix
     */
    @Override
    public int[][] imageProcessing(int[][] pixelMatrix) {

        ImageProcessingModel model = new ImageProcessingModel();
        int[][] colorValues = new int[4][pixelMatrix.length*pixelMatrix[0].length];
        int[][] processedPixelMatrix = new int[pixelMatrix.length][pixelMatrix[0].length];
        model.calculateHistogram(pixelMatrix);
        colorValues[0] = model.getaValue();
        colorValues[1] = model.getrValue();
        colorValues[2] = model.getgValue();
        colorValues[3] = model.getbValue();

        int m = 0;
        for(int x = 0; x < pixelMatrix.length; x++){
            for(int y = 0; y < pixelMatrix[0].length; y++){
                int color = ((colorValues[0][m] << 24)) | ((colorValues[3][m] << 16)) | ((colorValues[2][m] <<8))|
                        ((colorValues[1][m]));
                processedPixelMatrix[x][y] = color;
                m++;
            }
        }

        return processedPixelMatrix;

    }
}
