package model.operation;

import org.junit.Before;
import org.junit.Test;

import model.IPixel;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * A testing class for the {@code ColorTransformationOperation} that ensures it correctly
 * applies the given color transformation to any inputted pixels.
 */
public class ColorTransformationOperationTest {
  IPixel pixel1;
  IPixel pixel2;
  IPixel pixel3;

  ColorTransformationOperation redComponent;
  ColorTransformationOperation greenComponent;
  ColorTransformationOperation blueComponent;
  ColorTransformationOperation intensity;
  ColorTransformationOperation luma;

  ColorTransformationOperation lumaMatrix;
  ColorTransformationOperation sepia;

  @Before
  public void initData() {
    pixel1 = new Pixel(10, 15, 255);
    pixel2 = new Pixel(255, 255, 255);
    pixel3 = new Pixel(0, 0, 0);
    // Scale for the red component.
    redComponent = new ColorTransformationOperation(1, 0, 0);
    // Scale for the green component.
    greenComponent = new ColorTransformationOperation(0, 1, 0);
    // Scale for the blue component.
    blueComponent = new ColorTransformationOperation(0, 0, 1);
    // Scale for the intensity.
    intensity = new ColorTransformationOperation(1.0 / 3, 1.0 / 3, 1.0 / 3);
    // Scale for the luma.
    luma = new ColorTransformationOperation(0.2126, 0.7152, 0.0722);

    // Matrix for luma
    lumaMatrix = new ColorTransformationOperation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
    });

    // Matrix for sepia
    sepia = new ColorTransformationOperation(new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixTooManyRows() {
    new ColorTransformationOperation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixNotEnoughRows() {
    new ColorTransformationOperation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixTooManyColumns() {
    new ColorTransformationOperation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722, 0.0722},
            {0.2126, 0.7152, 0.0722},
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMatrixNotEnoughColumns() {
    new ColorTransformationOperation(new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152},
    });
  }

  @Test
  public void testRed() {
    assertEquals(new Pixel(10, 10, 10), redComponent.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), redComponent.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), redComponent.apply(pixel3));
  }

  @Test
  public void testGreen() {
    assertEquals(new Pixel(15, 15, 15), greenComponent.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), greenComponent.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), greenComponent.apply(pixel3));
  }

  @Test
  public void testBlue() {
    assertEquals(new Pixel(255, 255, 255), blueComponent.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), blueComponent.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), blueComponent.apply(pixel3));
  }

  @Test
  public void testIntensity() {
    assertEquals(new Pixel(93, 93, 93), intensity.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), intensity.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), intensity.apply(pixel3));
  }

  @Test
  public void testLuma() {
    assertEquals(new Pixel(31, 31, 31), luma.apply(pixel1));
    assertEquals(new Pixel(254, 254, 254), luma.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), luma.apply(pixel3));
  }

  @Test
  public void testLumaMatrix() {
    assertEquals(new Pixel(31, 31, 31), lumaMatrix.apply(pixel1));
    assertEquals(new Pixel(254, 254, 254), lumaMatrix.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), lumaMatrix.apply(pixel3));
  }

  @Test
  public void testSepia() {
    assertEquals(new Pixel(63, 56, 44), sepia.apply(pixel1));
    assertEquals(new Pixel(255, 255, 238), sepia.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), sepia.apply(pixel3));
  }
}