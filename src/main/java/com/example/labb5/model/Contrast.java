package com.example.labb5.model;

import java.lang.Math;
import java.text.DecimalFormat;

/**
 * Change the contrast of the image
 */

public class Contrast implements ManipulateImage{
    private int windowValue;
    private int levelValue;
    /**
     * Initialize the data fields
     * @param windowValue is the input value of the window
     * @param levelValue is the input value of the level
     */

    public Contrast(int windowValue, int levelValue){
        this.windowValue = windowValue;
        this.levelValue = levelValue;
    }
    /**
     * This method change the contrast of the image with Window/level method
     * Creates a look up table (LUT) using window and level values
     * The look up table is used to set a new value to the pixel matrix
     *
     * @param pixelMatrix is the matrix of image
     * @return processed pixel matrix
     */

    @Override
    public int[][] imageProcessing(int[][] pixelMatrix) {
        if(this.windowValue == 0 || this.levelValue == 0){
            return pixelMatrix;
        }
        //System.out.println(this.levelValue);
        ImageProcessingModel model = new ImageProcessingModel();
        int[][] colorValues = new int[4][pixelMatrix.length*pixelMatrix[0].length];
        int[][] processedPixelMatrix = new int[pixelMatrix.length][pixelMatrix[0].length];

        int MP = 255;
        int [] LUT = new int[MP+1];
        int a = this.levelValue - this.windowValue/2;
        int i = 0, j = 0;
        float ratio = (float)MP/(float)this.windowValue;

        model.calculateHistogram(pixelMatrix);
        colorValues[0] = model.getaValue();
        colorValues[1] = model.getrValue();
        colorValues[2] = model.getgValue();
        colorValues[3] = model.getbValue();


        if(a > 0) {
            for (; i < a; i++) {
                LUT[i] = 0;
            }
        }

            for (; j < this.windowValue; j++) {
                if(i+j > 255) break;
                LUT[i + j] =  Math.round(ratio * j);
            }

            i += j;
            for (; i < MP + 1; i++) {
                LUT[i] = MP;
            }


        int f = 1;
        while(f < 4){
            for(int t = 0; t < pixelMatrix.length*pixelMatrix[f].length; t++ ){
                colorValues[f][t] = LUT[colorValues[f][t]];
            }
            f++;
        }

        int m = 0;
        for(int x = 0; x < pixelMatrix.length; x++){
            for(int y = 0; y < pixelMatrix[0].length; y++){
                int color = ((colorValues[0][m] << 24)) | ((colorValues[1][m] << 16)) | ((colorValues[2][m] <<8))|
                        ((colorValues[3][m]));
                processedPixelMatrix[x][y] = color;
                m++;
            }
        }

        return processedPixelMatrix;

    }
}
