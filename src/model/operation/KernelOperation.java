package model.operation;

import java.util.Objects;

import model.IImage;
import model.IPixel;
import model.Pixel;

/**
 * A class that implements the methods of {@code IKernelOperation}. The methods here will be able
 * to filter images through the use of 2d arrays.
 */
public class KernelOperation implements IKernelOperation {
  private final double[][] kernelArray;
  private final int size;

  /**
   * A constructor for the {@code KernelOperation} that initializes the size as the length of the
   * array (the kernel is a square) and a kernel array.
   *
   * @param kernelArray the 2d array that will be applied to the image.
   */
  public KernelOperation(double[][] kernelArray) {
    Objects.requireNonNull(kernelArray);

    if (kernelArray.length % 2 != 1) {
      throw new IllegalArgumentException("The kernel size must be a positive, odd number");
    }
    this.size = kernelArray.length;

    this.kernelArray = new double[this.size][this.size];

    for (int i = 0; i < this.size; ++i) {
      if (kernelArray[i].length != this.size) {
        throw new IllegalArgumentException("Kernel arrays must be square!");
      }

      System.arraycopy(kernelArray[i], 0, this.kernelArray[i], 0, this.size);
    }
  }

  @Override
  public IPixel apply(int x, int y, IImage image) {
    double red = 0;
    double green = 0;
    double blue = 0;

    for (int ky = 0; ky < this.size; ++ky) {
      for (int kx = 0; kx < this.size; ++kx) {
        int ix = x - ((this.size - 1) / 2) + kx;
        int iy = y - ((this.size - 1) / 2) + ky;

        if (ix >= 0 && ix < image.getWidth() && iy >= 0 && iy < image.getHeight()) {
          IPixel pixel = image.getPixel(ix, iy);
          double scalar = this.kernelArray[ky][kx];

          red += pixel.getRed() * scalar;
          green += pixel.getGreen() * scalar;
          blue += pixel.getBlue() * scalar;
        }
      }
    }

    return new Pixel(this.clamp(red), this.clamp(green), this.clamp(blue));
  }

  private int clamp(double value) {
    return (int) Math.min(Math.max(value, 0), IPixel.MAX_VALUE);
  }
}
