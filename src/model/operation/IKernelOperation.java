package model.operation;

import model.IImage;
import model.IPixel;

/**
 * Represents an operation which can be used to create a new pixel from an original pixel that is
 * modified by a kernel.
 */
public interface IKernelOperation {
  /**
   * Apply the kernel operation to the given pixel, and return the resulting pixel.
   *
   * @param x     the x value of the middle of the kernel.
   * @param y     the y value of the middle of the kernel.
   * @param image the original image to apply the operation to.
   * @return the resulting pixel.
   */
  IPixel apply(int x, int y, IImage image);
}
