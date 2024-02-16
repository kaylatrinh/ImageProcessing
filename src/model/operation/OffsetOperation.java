package model.operation;

import model.IPixel;
import model.Pixel;

/**
 * Represents an operation which produces a new pixel by adding some (signed) offset to each
 * component of the pixel.
 */
public class OffsetOperation implements IPixelOperation {
  private final int offset;

  /**
   * Constructs an operation that applies the given offset to each component of a pixel.
   *
   * @param offset The offset to add to each component of the pixel.
   */
  public OffsetOperation(int offset) {
    this.offset = offset;
  }

  @Override
  public IPixel apply(IPixel original) {
    return new Pixel(this.addOffsetAndClamp(original.getRed(), this.offset),
            this.addOffsetAndClamp(original.getGreen(), this.offset),
            this.addOffsetAndClamp(original.getBlue(), this.offset));
  }

  /**
   * Add the given offset to the given value and clamp the result within [0, 255].
   *
   * @param value  The value to manipulate.
   * @param offset The offset to add to the value.
   * @return The result of the sum, clamped to within [0, 255].
   */
  private int addOffsetAndClamp(int value, int offset) {
    int result = value + offset;
    return Math.min(Math.max(result, 0), IPixel.MAX_VALUE);
  }
}
