package controller;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import control.ImageUtil;
import model.IImage;
import model.IPixel;
import model.Image;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Tests the helper methods for reading and writing PPM files in the ImageUtil class.
 */
public class ImageUtilTest {
  @Test
  public void testReadPPM() throws ImageUtil.InvalidImageException {
    String shortPPM = "P3\n"
            + "3 2 255\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n"
            + "255 0 255\n";

    IImage image = ImageUtil.readPPM(new StringReader(shortPPM), "small-image");

    assertEquals("small-image", image.getImageName());
    assertEquals(3, image.getWidth());
    assertEquals(2, image.getHeight());

    assertEquals(127, image.getPixel(1, 0).getGreen());
    assertEquals(255, image.getPixel(2, 1).getRed());
    assertEquals(0, image.getPixel(0, 1).getBlue());
  }

  @Test
  public void testReadComment() throws ImageUtil.InvalidImageException {
    String shortPPM = "P3\n"
            + "# This is a really awesome PPM! trust me\n"
            + "3 2 255\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "#no i promise it's really cool\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n"
            + "255 0 255\n";

    IImage image = ImageUtil.readPPM(new StringReader(shortPPM), "small-image");

    assertEquals("small-image", image.getImageName());
    assertEquals(3, image.getWidth());
    assertEquals(2, image.getHeight());

    assertEquals(127, image.getPixel(1, 0).getGreen());
    assertEquals(255, image.getPixel(2, 1).getRed());
    assertEquals(0, image.getPixel(0, 1).getBlue());
  }

  @Test
  public void testReadInvalidShape() {
    String shortPPM = "P3\n"
            + "4 -1 255\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n"
            + "255 0 255\n";

    try {
      ImageUtil.readPPM(new StringReader(shortPPM), "invalid-image");
      fail("No exception thrown");
    } catch (ImageUtil.InvalidImageException e) {
      assertEquals("Invalid image dimensions", e.getMessage());
    }
  }

  @Test
  public void testReadExtraValues() throws ImageUtil.InvalidImageException {
    String shortPPM = "P3\n"
            + "3 2 255\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n"
            + "255 0 255\n"
            + "255 0 255\n"
            + "255 0 255\n"
            + "255 0 255\n";

    IImage image = ImageUtil.readPPM(new StringReader(shortPPM), "extra-image");

    assertEquals("extra-image", image.getImageName());
    assertEquals(3, image.getWidth());
    assertEquals(2, image.getHeight());

    assertEquals(127, image.getPixel(1, 0).getGreen());
    assertEquals(255, image.getPixel(2, 1).getRed());
    assertEquals(0, image.getPixel(0, 1).getBlue());
  }

  @Test
  public void testReadNotEnoughValues() {
    String shortPPM = "P3\n"
            + "3 2 255\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n";

    try {
      ImageUtil.readPPM(new StringReader(shortPPM), "invalid-image");
      fail("No exception thrown");
    } catch (ImageUtil.InvalidImageException e) {
      assertEquals("Not enough pixel values", e.getMessage());
    }
  }

  @Test
  public void testUnsupportedMaxValue() {
    String shortPPM = "P3\n"
            + "3 2 1024\n"
            + "255 0 0\n"
            + "255 127 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n";

    try {
      ImageUtil.readPPM(new StringReader(shortPPM), "invalid-image");
      fail("No exception thrown");
    } catch (ImageUtil.InvalidImageException e) {
      assertEquals("Unsupported maximum PPM value", e.getMessage());
    }
  }

  @Test
  public void testValueTooBig() {
    String shortPPM = "P3\n"
            + "3 2 255\n"
            + "255 0 0\n"
            + "255 302 0\n"
            + "255 255 0\n"
            + "255 255 0\n"
            + "0 255 0\n"
            + "0 0 255\n";

    try {
      ImageUtil.readPPM(new StringReader(shortPPM), "invalid-image");
      fail("No exception thrown");
    } catch (ImageUtil.InvalidImageException e) {
      assertEquals("Invalid pixel value", e.getMessage());
    }
  }

  @Test
  public void testWritePPM() throws IOException {
    IPixel[][] imageArray = {
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

    IImage image = new Image(3, 2, imageArray, "test-image");

    Appendable output = new StringBuffer();
    ImageUtil.savePPM(output, image);

    String expected = "P3\n" +
            "3 2\n255\n" +
            "255\n0\n0\n" +
            "255\n127\n0\n" +
            "255\n255\n0\n" +
            "0\n255\n0\n" +
            "0\n0\n255\n" +
            "255\n0\n255\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testWriteToAppendableError() {
    Appendable output = new NotAppendable();

    IPixel[][] imageArray = {
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

    IImage image = new Image(3, 2, imageArray, "test-image");

    try {
      ImageUtil.savePPM(output, image);
      fail("No exception thrown!");
    } catch (IOException e) {
      assertNotNull(e);
    }
  }

  @Test
  public void testFuzzReadAndWrite() throws IOException {
    Random random = new Random(0);

    for (int i = 0; i < 1000; ++i) {
      int width = random.nextInt(10);
      int height = random.nextInt(10);
      IPixel[][] pixelArray = new IPixel[height][width];

      for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
          int red = random.nextInt(255);
          int green = random.nextInt(255);
          int blue = random.nextInt(255);
          pixelArray[y][x] = new Pixel(red, green, blue);
        }
      }

      IImage image = new Image(width, height, pixelArray, "fuzz-image");

      Appendable output = new StringBuffer();
      ImageUtil.savePPM(output, image);

      IImage secondImage = ImageUtil.readPPM(new StringReader(output.toString()), "fuzz-read");

      for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
          assertEquals(image.getPixel(x, y), secondImage.getPixel(x, y));
        }
      }

      Appendable secondOutput = new StringBuffer();
      ImageUtil.savePPM(secondOutput, secondImage);

      assertEquals(output.toString(), secondOutput.toString());
    }
  }

  static class NotAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("");
    }
  }
}
