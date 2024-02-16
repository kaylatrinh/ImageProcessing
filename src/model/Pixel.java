package model;

import java.util.Objects;

/**
 * This class represents a pixel that implements the methods provided in the {@code IPixel}. This
 * class is able to get the position and each individual channel of the pixel.
 */
public class Pixel implements IPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * A constructor for a Pixel whose color is measured on the RGB scale. There are three
   * different components that make up the final color of the pixel. There are 256 different
   * distinct combinations of each channel, so the values can be from 0 to 255 inclusive.
   *
   * @param red   the red component of the pixel.
   * @param green the green component of the pixel.
   * @param blue  the blue component of the pixel.
   * @throws IllegalArgumentException if an individual component does not meet the required valid
   *                                  values (0-255).
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (invalidScale(red) || invalidScale(green) || invalidScale(blue)) {
      throw new IllegalArgumentException("Color values must be a valid positive number.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Determines if the number of the color provided is valid.
   *
   * @param color the number of the color.
   * @return true if the color is valid, false otherwise.
   */
  private boolean invalidScale(int color) {
    return color < 0 || color > IPixel.MAX_VALUE;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pixel pixel = (Pixel) o;
    return red == pixel.red && green == pixel.green && blue == pixel.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  @Override
  public String toString() {
    return "Pixel(" + this.red + ", " + this.green + ", " + this.blue + ")";
  }
}
