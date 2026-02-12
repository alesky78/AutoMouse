package it.spaghettisource.automouse.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ImageIconFactory provides utility methods for loading and scaling images and icons for the application.
 * It supports loading images by name and size, and provides icons for buttons and the application window.
 *
 * @author alesky
 */
public class ImageIconFactory {

    /**
     * Logger for the ImageIconFactory class.
     */
    private static Log log = LogFactory.getLog(ImageIconFactory.class.getName());
    /**
     * Default icon size for buttons.
     */
    public  static final int ICON_SIZE_BUTTON = 30;

    /**
     * Private constructor to prevent instantiation.
     */
    private ImageIconFactory(){
    }

    /**
     * Loads an image by resource name and scales it to the specified size.
     *
     * @param name the resource name of the image
     * @param size the desired size in pixels
     * @return the scaled Image instance
     * @throws RuntimeException if the image cannot be loaded
     */
    private static Image getImageByNameAndSize(String name,int size) {

        try {
            BufferedImage bufferedImage;
            bufferedImage = ImageIO.read(ImageIconFactory.class.getResourceAsStream(name));
            return bufferedImage.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        } catch (IOException e) {
            String message = "error loading the immange "+name+" for:"+e.getMessage();
            log.error(message,e );
            throw new RuntimeException(message,e);
        }

    }

    /**
     * Loads an image as an ImageIcon by resource name and size.
     *
     * @param name the resource name of the image
     * @param size the desired size in pixels
     * @return the ImageIcon instance
     */
    private static ImageIcon getImageIconByNameAndSize(String name,int size) {
        return new ImageIcon(getImageByNameAndSize(name,size));
    }

    /**
     * Returns an ImageIcon for a button, scaled to the default button icon size.
     *
     * @param name the resource name of the image
     * @return the ImageIcon instance for the button
     */
    public static ImageIcon getForButton(String name) {
        return getImageIconByNameAndSize(name, ICON_SIZE_BUTTON);
    }

    /**
     * Loads and returns the application icon image.
     *
     * @return the application Image instance
     * @throws RuntimeException if the image cannot be loaded
     */
    public static Image getAppImage() {
        try {
            return ImageIO.read(ImageIconFactory.class.getResourceAsStream("/app.png"));
        } catch (IOException e) {
            log.error("error loading the app image for:"+e.getMessage(),e );
            throw new RuntimeException(e);
        }

    }

}
