package model.command;

import org.junit.Before;
import org.junit.Test;

import model.Album;
import model.IAlbum;
import model.IImage;
import model.IPixel;
import model.Image;
import model.Pixel;
import view.ImageTextView;
import view.ImageView;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the KernelOperationCommand correctly verifies constructor arguments, takes in input,
 * and applies a kernel transformation to the image.
 */
public class KernelOperationCommandTest {
  IImage image;
  IAlbum album;
  ImageView view;

  @Before
  public void initData() {
    this.album = new Album();
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
    this.album.addImage(this.image);
    this.view = new ImageTextView(System.out);
  }

  @Test
  public void testBlur() {
    ICommand command = new KernelOperationCommand("testing", "blurred", new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    });

    command.apply(album, view);
    IImage blurred = album.getImage("blurred");

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
    ICommand command = new KernelOperationCommand("testing", "sharp", new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    });

    command.apply(album, view);
    IImage sharp = album.getImage("sharp");

    // Test that it handles edge/corner cases correctly
    int actualBlue = sharp.getPixel(2, 1).getBlue();

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

    int actualRed = sharp.getPixel(2, 2).getRed();

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
