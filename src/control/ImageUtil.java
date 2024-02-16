package control;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.IImage;
import model.IPixel;
import model.Image;
import model.Pixel;

/**
 * This class contains utility methods to read and write PPM images.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename  the path of the file.
   * @param imageName the name of the image to create.
   * @return the newly created image.
   * @throws java.io.FileNotFoundException if the file is not found.
   * @throws IOException                   if the file is not a valid PPM image.
   */
  public static IImage readPPM(String filename, String imageName) throws IOException {
    return readPPM(new InputStreamReader(new FileInputStream(filename)), imageName);
  }

  /**
   * Reads an image in PPM format from the given Readable.
   *
   * @param file      The Readable to output the image data to.
   * @param imageName The name of the image to create.
   * @return The newly created image.
   * @throws InvalidImageException if the image is not correctly formatted as PPM.
   */
  public static IImage readPPM(Readable file, String imageName) throws InvalidImageException {
    Scanner sc = new Scanner(file);

    StringBuilder builder = new StringBuilder();

    // Read the file line by line, and populate a string
    while (sc.hasNextLine()) {
      String s = sc.nextLine();

      // This will throw away any comment lines
      if (s.charAt(0) != '#') {
        builder.append(s);
        builder.append(System.lineSeparator());
      }
    }

    // Set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    IPixel[][] imageArray;

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new InvalidImageException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    if (width < 0 || height < 0) {
      throw new InvalidImageException("Invalid image dimensions");
    }
    imageArray = new IPixel[height][width];

    int maxValue = sc.nextInt();
    if (maxValue != IPixel.MAX_VALUE) {
      throw new InvalidImageException("Unsupported maximum PPM value");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!sc.hasNext()) {
          throw new InvalidImageException("Not enough pixel values");
        }

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        IPixel p;

        try {
          p = new Pixel(r, g, b);
        } catch (IllegalArgumentException e) {
          throw new InvalidImageException("Invalid pixel value");
        }

        imageArray[i][j] = p;
      }
    }

    return new Image(width, height, imageArray, imageName);
  }

  /**
   * This method will load an image of varying formats into the program.
   *
   * @param filename  the name of the file.
   * @param imageName the name of the image of which it will be referred to.
   * @return an {@code IImage} of the image being loaded in.
   * @throws IOException if the bufferedImage is unable to be read.
   */
  public static IImage loadImage(String filename, String imageName) throws IOException {
    if (filename.endsWith(".ppm")) {
      return readPPM(filename, imageName);
    } else {
      BufferedImage image = ImageIO.read(new File(filename));
      return bufferedImageToImage(image, imageName);
    }
  }

  /**
   * This is a helper method in order to turn a buffered image to an {@code IImage}.
   *
   * @param image     the BufferedImage that is being converted to an IImage.
   * @param imageName the name of the image.
   * @return an {@code IImage}.
   */
  public static IImage bufferedImageToImage(BufferedImage image, String imageName) {
    int width = image.getWidth();
    int height = image.getHeight();

    IPixel[][] pixelArray = new IPixel[height][width];

    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        int rgb = image.getRGB(x, y);
        pixelArray[y][x] = new Pixel((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
      }
    }

    return new Image(width, height, pixelArray, imageName);
  }

  /**
   * Saves the given image to a PPM file.
   *
   * @param filename The filename to write the image to.
   * @param image    The image to write to a PPM file.
   * @throws IOException if the file cannot be opened for writing
   */
  public static void savePPM(String filename, IImage image) throws IOException {
    FileWriter output = new FileWriter(filename);
    savePPM(output, image);
    output.close();
  }

  /**
   * Saves the given image in PPM format to the given Appendable.
   *
   * @param output The Appendable to write image data to.
   * @param image  The image to write to the Appendable.
   * @throws IOException if the image data cannot be appended to the Appendable.
   */
  public static void savePPM(Appendable output, IImage image) throws IOException {
    output.append("P3\n");
    output.append(String.format("%d %d\n", image.getWidth(), image.getHeight()));
    output.append(String.format("%d\n", IPixel.MAX_VALUE));

    for (int y = 0; y < image.getHeight(); ++y) {
      for (int x = 0; x < image.getWidth(); ++x) {
        IPixel pixel = image.getPixel(x, y);
        output.append(
                String.format("%d\n%d\n%d\n", pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
      }
    }
  }

  /**
   * Saves the image in the program to the desired format.
   *
   * @param filename the name of the file.
   * @param image    the image to be saved.
   * @throws IOException if the image cannot be rendered.
   */
  public static void saveImage(String filename, IImage image) throws IOException {
    RenderedImage renderedImage = imageToBufferedImage(image);
    String[] filenameParts = filename.split("\\.");
    String formatName = filenameParts[filenameParts.length - 1];
    if (formatName.equals("ppm")) {
      savePPM(filename, image);
    }
    ImageIO.write(renderedImage, formatName, new File(filename));
  }

  /**
   * A helper method to convert an {@code IImage} to a RenderedImage.
   *
   * @param image the image to be converted to a rendered image.
   * @return a RenderedImage.
   */
  public static BufferedImage imageToBufferedImage(IImage image) {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        IPixel pixel = image.getPixel(x, y);
        int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | (pixel.getBlue());
        bufferedImage.setRGB(x, y, rgb);
      }
    }

    return bufferedImage;
  }

  /**
   * An exception that represents an error in parsing an image.
   */
  public static class InvalidImageException extends IOException {
    public InvalidImageException(String message) {
      super(message);
    }
  }
}
