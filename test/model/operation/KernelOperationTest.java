package model.operation;

import org.junit.Before;
import org.junit.Test;

import model.IImage;
import model.IPixel;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the KernelOperation properly applies a kernel operation to an image to create
 * a new image, and that it verifies that the matrix passed to it is a well-formed kernel.
 */
public class KernelOperationTest {
  IImage image;

  @Before
  public void initData() {
    this.image = new Image(5, 5, new IPixel[][]{
        {
          new Pixel(0, 0, 255),
          new Pixel(0, 255, 255),
          new Pixel(255, 0, 0),
          new Pixel(0, 255, 255),
          new Pixel(255, 0, 0),
        },
        {
          new Pixel(127, 0, 255),
          new Pixel(0, 255, 255),
          new Pixel(255, 127, 0),
          new Pixel(0, 255, 255),
          new Pixel(255, 127, 0),
        },
        {
          new Pixel(127, 255, 255),
          new Pixel(255, 255, 255),
          new Pixel(255, 127, 255),
          new Pixel(255, 255, 255),
          new Pixel(255, 127, 255),
        },
        {
          new Pixel(127, 255, 0),
          new Pixel(0, 255, 255),
          new Pixel(0, 127, 255),
          new Pixel(255, 255, 0),
          new Pixel(255, 127, 0),
        },
        {
            new Pixel(127, 127, 0),
            new Pixel(0, 127, 127),
            new Pixel(0, 127, 127),
            new Pixel(127, 255, 0),
            new Pixel(127, 127, 0),
        }
    }, "testing");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testKernelNotSquare() {
    new KernelOperation(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testKernelExtraItem() {
    new KernelOperation(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testKernelNotEnoughItems() {
    new KernelOperation(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4},
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
    });
  }

  @Test(expected = IllegalArgumentException.class)
  public void testKernelEvenSize() {
    new KernelOperation(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16, 1.0 / 16},
            {1.0 / 16, 1.0 / 8, 1.0 / 16, 1.0 / 16}
    });
  }

  @Test
  public void testBlur() {
    IKernelOperation blur = new KernelOperation(new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    });

    IImage blurred = new Image(this.image, blur, "blurred");

    // Test that it handles edge/corner cases correctly
    int actualRed = blurred.getPixel(0, 1).getRed();

    int expectedRed = (int) (image.getPixel(0, 0).getRed() * 1.0 / 8
            + image.getPixel(1, 0).getRed() * 1.0 / 16
            + image.getPixel(0, 1).getRed() * 1.0 / 4
            + image.getPixel(1, 1).getRed() * 1.0 / 8
            + image.getPixel(0, 2).getRed() * 1.0 / 8
            + image.getPixel(1, 2).getRed() * 1.0 / 16);

    assertEquals(expectedRed, actualRed);

    // Test that it handles center cases correctly

    int actualGreen = blurred.getPixel(2, 3).getGreen();

    int expectedGreen = (int) (
            image.getPixel(1, 2).getGreen() * 1.0 / 16
                    + image.getPixel(2, 2).getGreen() * 1.0 / 8
                    + image.getPixel(3, 2).getGreen() * 1.0 / 16
                    + image.getPixel(1, 3).getGreen() * 1.0 / 8
                    + image.getPixel(2, 3).getGreen() * 1.0 / 4
                    + image.getPixel(3, 3).getGreen() * 1.0 / 8
                    + image.getPixel(1, 4).getGreen() * 1.0 / 16
                    + image.getPixel(2, 4).getGreen() * 1.0 / 8
                    + image.getPixel(3, 4).getGreen() * 1.0 / 16);

    assertEquals(expectedGreen, actualGreen);
  }

  @Test
  public void testSharpen() {
    IKernelOperation sharpen = new KernelOperation(new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    });

    IImage sharpened = new Image(this.image, sharpen, "sharpened");

    // Test that it handles edge/corner cases correctly
    int actualBlue = sharpened.getPixel(2, 1).getBlue();

    int expectedBlue = (int) (
            image.getPixel(0, 0).getBlue() * -1.0 / 8
                    + image.getPixel(1, 0).getBlue() * 1.0 / 4
                    + image.getPixel(2, 0).getBlue() * 1.0 / 4
                    + image.getPixel(3, 0).getBlue() * 1.0 / 4
                    + image.getPixel(4, 0).getBlue() * -1.0 / 8
                    + image.getPixel(0, 1).getBlue() * -1.0 / 8
                    + image.getPixel(1, 1).getBlue() * 1.0 / 4
                    + image.getPixel(2, 1).getBlue() * 1.0
                    + image.getPixel(3, 1).getBlue() * 1.0 / 4
                    + image.getPixel(4, 1).getBlue() * -1.0 / 8
                    + image.getPixel(0, 2).getBlue() * -1.0 / 8
                    + image.getPixel(1, 2).getBlue() * 1.0 / 4
                    + image.getPixel(2, 2).getBlue() * 1.0 / 4
                    + image.getPixel(3, 2).getBlue() * 1.0 / 4
                    + image.getPixel(4, 2).getBlue() * -1.0 / 8
                    + image.getPixel(0, 3).getBlue() * -1.0 / 8
                    + image.getPixel(1, 3).getBlue() * -1.0 / 8
                    + image.getPixel(2, 3).getBlue() * -1.0 / 8
                    + image.getPixel(3, 3).getBlue() * -1.0 / 8
                    + image.getPixel(4, 3).getBlue() * -1.0 / 8
    );

    assertEquals(expectedBlue, actualBlue);

    // Test that it handles center cases correctly

    int actualRed = sharpened.getPixel(2, 2).getRed();

    int expectedRed = (int) (image.getPixel(0, 0).getRed() * -1.0 / 8
            + image.getPixel(1, 0).getRed() * -1.0 / 8
            + image.getPixel(2, 0).getRed() * -1.0 / 8
            + image.getPixel(3, 0).getRed() * -1.0 / 8
            + image.getPixel(4, 0).getRed() * -1.0 / 8
            + image.getPixel(0, 1).getRed() * -1.0 / 8
            + image.getPixel(1, 1).getRed() * 1.0 / 4
            + image.getPixel(2, 1).getRed() * 1.0 / 4
            + image.getPixel(3, 1).getRed() * 1.0 / 4
            + image.getPixel(4, 1).getRed() * -1.0 / 8
            + image.getPixel(0, 2).getRed() * -1.0 / 8
            + image.getPixel(1, 2).getRed() * 1.0 / 4
            + image.getPixel(2, 2).getRed() * 1.0
            + image.getPixel(3, 2).getRed() * 1.0 / 4
            + image.getPixel(4, 2).getRed() * -1.0 / 8
            + image.getPixel(0, 3).getRed() * -1.0 / 8
            + image.getPixel(1, 3).getRed() * 1.0 / 4
            + image.getPixel(2, 3).getRed() * 1.0 / 4
            + image.getPixel(3, 3).getRed() * 1.0 / 4
            + image.getPixel(4, 3).getRed() * -1.0 / 8
            + image.getPixel(0, 4).getRed() * -1.0 / 8
            + image.getPixel(1, 4).getRed() * -1.0 / 8
            + image.getPixel(2, 4).getRed() * -1.0 / 8
            + image.getPixel(3, 4).getRed() * -1.0 / 8
            + image.getPixel(4, 4).getRed() * -1.0 / 8
    );

    assertEquals(expectedRed, actualRed);
  }
}
