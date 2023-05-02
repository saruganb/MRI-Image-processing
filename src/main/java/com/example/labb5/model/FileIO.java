package com.example.labb5.model;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;
/**
 * This class represents the Saving process of an image
 */
public class FileIO {
    /**
     * An image and the users selected path gets accessed
     * The image then saves in the project
     * @param image the image
     * @param imageFile the image path
     * @param name the name of the file
     * @throws IOException if an error occur
     */
    public static void writeToFile(Image image, File imageFile,String name) throws IOException{
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        int lenght = imageFile.getAbsolutePath().length();
        String str = imageFile.getAbsolutePath().substring(lenght - 3, lenght);
        ImageIO.write(bufferedImage, str, new File(name + "." + str));
    }


}