package model.operation;

import model.IPixel;
import model.Pixel;

/**
 * Represents an operation which makes a new pixel by taking each component of
 * an existing pixel,
 * multiplying each by some scalar, taking the sum, and setting the new pixel's
 * red, green, and
 * blue to that sum. This class handles cases when trying to visualize one of
 * the three
 * components, the intensity, or the luma.
 */
public class ColorTransformationOperation implements IPixelOperation {
  private final double[][] transformation;

  /**
   * Construct a pixel operation which scales the red, green, and blue pixels
   * by the given constants.
   *
   * @param redScale   The constant to scale the red component by before summing.
   * @param greenScale The constant to scale the green component by before
   *                   summing.
   * @param blueScale  The constant to scale the blue component by before summing.
   */
  public ColorTransformationOperation(double redScale, double greenScale, double blueScale) {
    this.transformation = new double[3][3];

    for (int i = 0; i < 3; ++i) {
      this.transformation[i][0] = redScale;
      this.transformation[i][1] = greenScale;
      this.transformation[i][2] = blueScale;
    }
  }

  /**
   * Construct a pixel operation which uses the provided 3x3 matrix of scalars to determine each
   * component of the resulting pixel.
   *
   * @param transformation A 2D array of doubles representing the transformation matrix.
   */
  public ColorTransformationOperation(double[][] transformation) {
    this.transformation = new double[3][3];

    if (transformation.length != 3) {
      throw new IllegalArgumentException("Color transformation array must have length 3");
    }

    for (int i = 0; i < 3; ++i) {
      if (transformation[i].length != 3) {
        throw new IllegalArgumentException("Rows of color transformation array must have length 3");
      }

      System.arraycopy(transformation[i], 0, this.transformation[i], 0, 3);
    }
  }

  private int getComponent(int row, IPixel original) {
    double value = this.transformation[row][0] * original.getRed()
            + this.transformation[row][1] * original.getGreen()
            + this.transformation[row][2] * original.getBlue();

    return Math.max(0, Math.min(255, (int) value));
  }

  @Override
  public IPixel apply(IPixel original) {
    int red = this.getComponent(0, original);
    int green = this.getComponent(1, original);
    int blue = this.getComponent(2, original);
    return new Pixel(red, green, blue);
  }
}
