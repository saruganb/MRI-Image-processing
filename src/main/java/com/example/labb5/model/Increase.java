package com.example.labb5.model;

/**
 * This class increases the intensity of either r, g or b
 */
public class Increase implements ManipulateImage{
   private int r,g,b;

    /**
     * Constructs the values depending on users choice
     * @param r red
     * @param g green
     * @param b blue
     */
   public Increase (int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
   }
    /**
     * Increase the intensity amount of rgb depending on user choice
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
                int color = ((colorValues[0][m] << 24)) | (this.r*(colorValues[1][m] << 16)) | (this.g*(colorValues[2][m] <<8))|
                        (this.b*(colorValues[3][m]));
                processedPixelMatrix[x][y] = color;
                m++;
            }
        }

        return processedPixelMatrix;




    }
}
