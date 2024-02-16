package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Histogram class, which is responsible for generating and representing
 * a histogram from an image.
 */
public class HistogramTest {
  IImage image = new Image(2, 2, new IPixel[][]{
          { new Pixel(207, 60, 3), new Pixel(212, 213, 3) },
          { new Pixel(1, 2, 3), new Pixel(0, 47, 50) }
  }, "test-image");
  Histogram histogram = new Histogram(image);

  @Test
  public void testRed() {
    assertEquals(1, this.histogram.getRedAt(0));
    assertEquals(1, this.histogram.getRedAt(1));
    assertEquals(0, this.histogram.getRedAt(2));
    assertEquals(0, this.histogram.getRedAt(8));
    assertEquals(0, this.histogram.getRedAt(37));
    assertEquals(0, this.histogram.getRedAt(153));
    assertEquals(1, this.histogram.getRedAt(207));
    assertEquals(0, this.histogram.getRedAt(255));
  }

  @Test
  public void testGreen() {
    assertEquals(0, this.histogram.getGreenAt(0));
    assertEquals(1, this.histogram.getGreenAt(2));
    assertEquals(1, this.histogram.getGreenAt(47));
    assertEquals(0, this.histogram.getGreenAt(59));
    assertEquals(1, this.histogram.getGreenAt(60));
    assertEquals(0, this.histogram.getGreenAt(212));
    assertEquals(1, this.histogram.getGreenAt(213));
    assertEquals(0, this.histogram.getGreenAt(254));
    assertEquals(0, this.histogram.getGreenAt(255));
  }

  @Test
  public void testBlue() {
    assertEquals(0, this.histogram.getBlueAt(10));
    assertEquals(3, this.histogram.getBlueAt(3));
    assertEquals(1, this.histogram.getBlueAt(50));
    assertEquals(0, this.histogram.getBlueAt(100));
    assertEquals(0, this.histogram.getBlueAt(0));
    assertEquals(0, this.histogram.getBlueAt(255));
  }

  @Test
  public void testIntensity() {
    assertEquals(1, this.histogram.getIntensityAt(90));
    assertEquals(1, this.histogram.getIntensityAt(142));
    assertEquals(1, this.histogram.getIntensityAt(2));
    assertEquals(1, this.histogram.getIntensityAt(32));
    assertEquals(0, this.histogram.getIntensityAt(60));
    assertEquals(0, this.histogram.getIntensityAt(213));
    assertEquals(0, this.histogram.getIntensityAt(0));
    assertEquals(0, this.histogram.getIntensityAt(207));
  }
}
