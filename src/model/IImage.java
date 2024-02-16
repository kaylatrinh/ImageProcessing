package model;

/**
 * This interface is a representation of an Image, which is a list of {@code Pixels}. An image
 * also has a name by which the program will refer to it as.
 */
public interface IImage {

  /**
   * All images have a name by which the program will refer to it as.
   *
   * @return the name of the image.
   */
  String getImageName();

  /**
   * Returns the width of the image, in pixels.
   *
   * @return the width of the image.
   */
  int getWidth();

  /**
   * Returns the height of the image, in pixels.
   *
   * @return the height of the image.
   */
  int getHeight();

  /**
   * Returns the pixel at the given location in the image.
   *
   * @param x the x coordinate of the pixel.
   * @param y the y coordinate of the pixel.
   * @return the {@code Pixel} at the given location.
   */
  IPixel getPixel(int x, int y);
}
