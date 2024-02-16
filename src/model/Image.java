package model;

import java.util.Arrays;
import java.util.Objects;

import model.operation.IKernelOperation;
import model.operation.IPixelOperation;

/**
 * This class implements the methods of {@code IImage}. The two methods in this
 * class are
 * getImageName, which returns the name of the image, and getPixel, which
 * returns the location of
 * the pixel.
 */
public class Image implements IImage {
  private final IPixel[][] pixelArray;
  private final String imageName;
  private final int width;
  private final int height;

  /**
   * Constructs an image from the given pixel array.
   *
   * @param width      The width of the pixel array.
   * @param height     The height of the pixel array.
   * @param pixelArray The array containing pixels of the image.
   * @param imageName  The name of the image to be created.
   */
  public Image(int width, int height, IPixel[][] pixelArray, String imageName) {
    Objects.requireNonNull(pixelArray);
    Objects.requireNonNull(imageName);

    this.width = width;
    this.height = height;
    this.pixelArray = new IPixel[height][width];

    if (height != pixelArray.length) {
      throw new IllegalArgumentException(
              "Array dimension does not match provided height " + height);
    }

    for (int y = 0; y < height; ++y) {
      IPixel[] row = pixelArray[y];
      Objects.requireNonNull(row, "Row " + y + " of provided array is null");

      if (width != row.length) {
        throw new IllegalArgumentException(
                "Array dimension (row " + y + ") does not match provided width " + width);
      }

      for (int x = 0; x < width; ++x) {
        IPixel pixel = row[x];
        Objects.requireNonNull(pixel, "Pixel (" + x + ", " + y + ") of provided array is null");

        this.pixelArray[y][x] = pixel;
      }
    }

    this.imageName = imageName;
  }

  /**
   * Constructs an image by applying the given operation to each pixel in an
   * existing image.
   *
   * @param original  The original image to create the new image from.
   * @param operation The operation to apply to pixels in the old image.
   * @param imageName The name of the new image.
   */
  public Image(IImage original, IPixelOperation operation, String imageName) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(operation);
    Objects.requireNonNull(imageName);

    this.imageName = imageName;
    this.width = original.getWidth();
    this.height = original.getHeight();

    this.pixelArray = new Pixel[height][width];

    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        this.pixelArray[y][x] = operation.apply(original.getPixel(x, y));
      }
    }
  }

  /**
   * Construct an image by applying the provided kernel operation to the provided original image.
   * @param original The original image to perform the provided operation to.
   * @param operation The kernel operation to perform.
   * @param imageName The name of the newly created image.
   */
  public Image(IImage original, IKernelOperation operation, String imageName) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(operation);
    Objects.requireNonNull(imageName);

    this.imageName = imageName;
    this.width = original.getWidth();
    this.height = original.getHeight();

    this.pixelArray = new Pixel[height][width];

    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        this.pixelArray[y][x] = operation.apply(x, y, original);
      }
    }
  }

  @Override
  public String getImageName() {
    return this.imageName;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public IPixel getPixel(int x, int y) {
    if (x >= this.width || y >= this.height || x < 0 || y < 0) {
      throw new IllegalArgumentException(
              "Pixel (" + x + ", " + y + ") out of bounds! Image size: ("
                      + this.width + ", " + this.height + ")");
    }
    return this.pixelArray[y][x];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Image image = (Image) o;
    return width == image.width && height == image.height
            && Arrays.deepEquals(pixelArray, image.pixelArray);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(imageName, width, height);
    result = 31 * result + Arrays.deepHashCode(pixelArray);
    return result;
  }
}
