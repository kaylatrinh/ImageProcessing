package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the Image class, which represents an image made up of pixels.
 */
public class ImageTest {
  IPixel[][] pixels;

  @Before
  public void initData() {
    this.pixels = new IPixel[][]{
      {
        new Pixel(255, 0, 0),
        new Pixel(255, 127, 0),
        new Pixel(255, 255, 0)
      },
      {
        new Pixel(0, 255, 0),
        new Pixel(0, 0, 255),
        new Pixel(255, 0, 255)
      }
    };
  }

  @Test
  public void testPixelArrayConstructor() {
    IImage image = new Image(3, 2, this.pixels, "array-image");

    assertEquals("array-image", image.getImageName());
    assertEquals(3, image.getWidth());
    assertEquals(2, image.getHeight());

    assertEquals(127, image.getPixel(1, 0).getGreen());
    assertEquals(255, image.getPixel(2, 1).getRed());
    assertEquals(0, image.getPixel(0, 1).getBlue());
  }


  @Test(expected = NullPointerException.class)
  public void testNullPixelArray() {
    new Image(3, 3, null, "null-array");
  }

  @Test(expected = NullPointerException.class)
  public void testNullImageName() {
    new Image(1, 1, new Pixel[][]{{new Pixel(0, 0, 0)}}, null);
  }

  @Test
  public void testNullRowInPixelArray() {
    this.pixels[1] = null;

    try {
      new Image(3, 2, this.pixels, "null-row-image");
      fail("exception not thrown!");
    } catch (NullPointerException e) {
      assertEquals("Row 1 of provided array is null", e.getMessage());
    }
  }

  @Test
  public void testInvalidHeightPixelArray() {
    try {
      new Image(3, 4, this.pixels, "too-short-array");
      fail("exception not thrown!");
    } catch (IllegalArgumentException e) {
      assertEquals("Array dimension does not match provided height 4", e.getMessage());
    }
  }

  @Test
  public void testJaggedPixelArray() {
    this.pixels[1] = new IPixel[]{
      new Pixel(0, 255, 0),
      new Pixel(0, 0, 255),
      new Pixel(255, 0, 255),
      new Pixel(0, 0, 0)
    };

    try {
      new Image(3, 2, this.pixels, "jagged-array");
      fail("exception not thrown!");
    } catch (IllegalArgumentException e) {
      assertEquals("Array dimension (row 1) does not match provided width 3", e.getMessage());
    }
  }

  @Test
  public void testNullPixelInArray() {
    this.pixels[0][2] = null;

    try {
      new Image(3, 2, this.pixels, "null-pixel");
      fail("exception not thrown!");
    } catch (NullPointerException e) {
      assertEquals("Pixel (2, 0) of provided array is null", e.getMessage());
    }
  }

  @Test
  public void testGetPixel() {
    IImage image = new Image(3, 2, this.pixels, "array-image");

    assertEquals(255, image.getPixel(0, 0).getRed());
    assertEquals(0, image.getPixel(0, 0).getGreen());
    assertEquals(0, image.getPixel(0, 0).getBlue());
  }

  @Test
  public void testGetPixelOutOfBounds() {
    IImage image = new Image(3, 2, this.pixels, "array-image");

    try {
      image.getPixel(0, 3);
      fail("exception not thrown!");
    } catch (IllegalArgumentException e) {
      assertEquals("Pixel (0, 3) out of bounds! Image size: (3, 2)", e.getMessage());
    }
  }

  @Test
  public void testGetPixelNegative() {
    IImage image = new Image(3, 2, this.pixels, "array-image");

    try {
      image.getPixel(-1, 0);
      fail("exception not thrown!");
    } catch (IllegalArgumentException e) {
      assertEquals("Pixel (-1, 0) out of bounds! Image size: (3, 2)", e.getMessage());
    }
  }
}
