package model.operation;

import model.IPixel;
import model.Pixel;

/**
 * Represents an operation which creates a grayscale pixel from an existing pixel by setting
 * each component of the new pixel to the maximum of the red, green, and blue components of the
 * original pixel.
 */
public class ValueOperation implements IPixelOperation {

  @Override
  public IPixel apply(IPixel original) {
    int max = Math.max(Math.max(original.getRed(), original.getGreen()), original.getBlue());
    return new Pixel(max, max, max);
  }
}
