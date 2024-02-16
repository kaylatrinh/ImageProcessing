package model.operation;

import model.IPixel;

/**
 * Represents an operation which can be used to create a new pixel from
 * some existing pixel.
 */
public interface IPixelOperation {
  /**
   * Apply the operation to the given pixel, and return the resulting pixel.
   *
   * @param original the pixel to apply the operation to.
   * @return the resulting pixel.
   */
  IPixel apply(IPixel original);
}
