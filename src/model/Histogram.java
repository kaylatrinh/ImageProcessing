package model;

/**
 * Represents a histogram (a chart which shows the frequency of different
 * pixel component values) for a particular image. Contains lines for red, green,
 * blue, and intensity components.
 */
public class Histogram {
  private final int[] redHistogram;
  private final int[] greenHistogram;
  private final int[] blueHistogram;
  private final int[] intensityHistogram;

  /**
   * Construct a histogram chart based on the given image.
   * @param image The image to build the histogram from.
   */
  public Histogram(IImage image) {
    this.redHistogram = new int[256];
    this.greenHistogram = new int[256];
    this.blueHistogram = new int[256];
    this.intensityHistogram = new int[256];

    for (int x = 0; x < image.getWidth(); ++x) {
      for (int y = 0; y < image.getHeight(); ++y) {
        IPixel pixel = image.getPixel(x, y);
        this.redHistogram[pixel.getRed()] += 1;
        this.greenHistogram[pixel.getGreen()] += 1;
        this.blueHistogram[pixel.getBlue()] += 1;

        int intensity = (int) (1.0 / 3 * pixel.getRed()
                + 1.0 / 3 * pixel.getGreen()
                + 1.0 / 3 * pixel.getBlue());
        this.intensityHistogram[intensity] += 1;
      }
    }
  }

  /**
   * Return the number of pixels with the given red-component value.
   * @param value The value to return the number of pixels with.
   * @return The number of pixels with that value.
   */
  public int getRedAt(int value) {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Invalid pixel value");
    }
    return this.redHistogram[value];
  }

  /**
   * Return the number of pixels with the given green-component value.
   * @param value The value to return the number of pixels with.
   * @return The number of pixels with that value.
   */
  public int getGreenAt(int value) {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Invalid pixel value");
    }
    return this.greenHistogram[value];
  }

  /**
   * Return the number of pixels with the given blue-component value.
   * @param value The value to return the number of pixels with.
   * @return The number of pixels with that value.
   */
  public int getBlueAt(int value) {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Invalid pixel value");
    }
    return this.blueHistogram[value];
  }

  /**
   * Return the number of pixels with the given intensity value.
   * @param value The value to return the number of pixels with.
   * @return The number of pixels with that value.
   */
  public int getIntensityAt(int value) {
    if (value < 0 || value > 255) {
      throw new IllegalArgumentException("Invalid pixel value");
    }
    return this.intensityHistogram[value];
  }

  /**
   * Return the maximum count across the red, green, blue, and intensity charts.
   * @return The maximum number of pixels which share the same value for either
   *         red, green, blue, or intensity.
   */
  public int getMaximum() {
    int max = 0;
    for (int count : this.redHistogram) {
      max = Math.max(max, count);
    }
    for (int count : this.greenHistogram) {
      max = Math.max(max, count);
    }
    for (int count : this.blueHistogram) {
      max = Math.max(max, count);
    }
    for (int count : this.intensityHistogram) {
      max = Math.max(max, count);
    }
    return max;
  }
}
