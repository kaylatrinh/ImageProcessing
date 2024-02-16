package model;

/**
 * A pixel has a position within an image and a color. A pixel's color is represented by
 * individual components. In this project, it will be represented using the RGB (red-green-blue)
 * model.
 */
public interface IPixel {
  int MAX_VALUE = 255;

  /**
   * Gets the value of the red channel.
   *
   * @return int of the red channel.
   */
  int getRed();

  /**
   * Gets the value of the green channel.
   *
   * @return int of the green channel.
   */
  int getGreen();

  /**
   * Gets the value of the blue channel.
   *
   * @return int of the blue channel.
   */
  int getBlue();
}
