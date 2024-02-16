package model.command;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import control.ImageUtil;
import model.Album;
import model.IAlbum;
import model.IImage;
import model.operation.ColorTransformationOperation;
import model.operation.OffsetOperation;
import view.ImageView;

import static org.junit.Assert.assertEquals;

/**
 * This is a testing class for the {@code PixelOperationCommand} that ensures that being able to
 * visualize the RGB components, intensity, and luma work properly. We compare the RGB components
 * of a pixel from the new image and compare it to the RGB components of the old image with the
 * operations called onto it.
 */
public class PixelOperationCommandTest {
  IImage image1;
  IAlbum album;
  ImageView view;

  @Before
  public void initData() throws IOException {
    image1 = ImageUtil.readPPM("res/rocks.ppm", "rocks");
    album = new Album();
    album.addImage(image1);
  }

  @Test
  public void testRedComponent() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "red",
            new ColorTransformationOperation(1, 0, 0));
    pixel.apply(album, view);
    album.getImage("red");
    IImage red = album.getImage("red");
    // Gets each individual component from a pixel in the new image to compare to the red
    // component of a pixel from the old image.
    int oldRed = image1.getPixel(75, 69).getRed();
    int newRed = red.getPixel(75, 69).getRed();
    int newGreen = red.getPixel(75, 69).getGreen();
    int newBlue = red.getPixel(75, 69).getBlue();
    assertEquals(oldRed, newRed);
    assertEquals(oldRed, newGreen);
    assertEquals(oldRed, newBlue);
  }

  @Test
  public void testGreenComponent() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "green",
            new ColorTransformationOperation(0, 1, 0));
    pixel.apply(album, view);
    album.getImage("green");
    IImage green = album.getImage("green");
    // Gets each individual component from a pixel in the new image to compare to the green
    // component of a pixel from the old image.
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int newRed = green.getPixel(75, 69).getRed();
    int newGreen = green.getPixel(75, 69).getGreen();
    int newBlue = green.getPixel(75, 69).getBlue();
    assertEquals(oldGreen, newRed);
    assertEquals(oldGreen, newGreen);
    assertEquals(oldGreen, newBlue);
  }

  @Test
  public void testBlueComponent() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "blue",
            new ColorTransformationOperation(0, 0, 1));
    pixel.apply(album, view);
    album.getImage("blue");
    IImage blue = album.getImage("blue");
    // Gets each individual component from a pixel in the new image to compare to the blue
    // component of a pixel from the old image.
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = blue.getPixel(75, 69).getRed();
    int newGreen = blue.getPixel(75, 69).getGreen();
    int newBlue = blue.getPixel(75, 69).getBlue();
    assertEquals(oldBlue, newRed);
    assertEquals(oldBlue, newGreen);
    assertEquals(oldBlue, newBlue);
  }

  @Test
  public void testIntensity() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "intensity",
            new ColorTransformationOperation(1.0 / 3, 1.0 / 3, 1.0 / 3));
    pixel.apply(album, view);
    album.getImage("intensity");
    IImage intensity = album.getImage("intensity");
    // Gets each individual component from a pixel in the new image to compare to the intensity
    // of the old image.
    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = intensity.getPixel(75, 69).getRed();
    int newGreen = intensity.getPixel(75, 69).getGreen();
    int newBlue = intensity.getPixel(75, 69).getBlue();
    int sum = (int) ((oldRed / 3.0) + (oldGreen / 3.0) + (oldBlue / 3.0));
    assertEquals(newRed, sum);
    assertEquals(newGreen, sum);
    assertEquals(newBlue, sum);
  }

  @Test
  public void testLuma() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "luma",
            new ColorTransformationOperation(0.2126, 0.7152, 0.0722));
    pixel.apply(album, view);
    album.getImage("luma");
    IImage luma = album.getImage("luma");
    // Gets each individual component from a pixel in the new image to compare to the luma
    // of the old image.
    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = luma.getPixel(75, 69).getRed();
    int newGreen = luma.getPixel(75, 69).getGreen();
    int newBlue = luma.getPixel(75, 69).getBlue();
    int sum = (int) ((oldRed * 0.2126) + (oldGreen * 0.7152) + (oldBlue * 0.0722));
    assertEquals(newRed, sum);
    assertEquals(newGreen, sum);
    assertEquals(newBlue, sum);
  }

  @Test
  public void testDarken() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "dark", new OffsetOperation(-10));
    pixel.apply(album, view);
    album.getImage("dark");
    IImage dark = album.getImage("dark");
    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = dark.getPixel(75, 69).getRed();
    int newGreen = dark.getPixel(75, 69).getGreen();
    int newBlue = dark.getPixel(75, 69).getBlue();
    assertEquals(newRed, oldRed - 10);
    assertEquals(newGreen, oldGreen - 10);
    assertEquals(newBlue, oldBlue - 10);
  }

  @Test
  public void testBrighten() {
    PixelOperationCommand pixel = new PixelOperationCommand(
            "rocks", "bright", new OffsetOperation(10));
    pixel.apply(album, view);
    album.getImage("bright");
    IImage bright = album.getImage("bright");
    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = bright.getPixel(75, 69).getRed();
    int newGreen = bright.getPixel(75, 69).getGreen();
    int newBlue = bright.getPixel(75, 69).getBlue();
    assertEquals(newRed, oldRed + 10);
    assertEquals(newGreen, oldGreen + 10);
    assertEquals(newBlue, oldBlue + 10);
  }

  @Test
  public void testLumaMatrix() {
    PixelOperationCommand pixel = new PixelOperationCommand("rocks", "luma",
            new ColorTransformationOperation(new double[][]{
                    {0.2126, 0.7152, 0.0722},
                    {0.2126, 0.7152, 0.0722},
                    {0.2126, 0.7152, 0.0722}}));
    pixel.apply(album, view);
    album.getImage("luma");
    IImage luma = album.getImage("luma");
    // Gets each individual component from a pixel in the new image to compare to the luma
    // of the old image.
    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = luma.getPixel(75, 69).getRed();
    int newGreen = luma.getPixel(75, 69).getGreen();
    int newBlue = luma.getPixel(75, 69).getBlue();
    int sum = (int) ((oldRed * 0.2126) + (oldGreen * 0.7152) + (oldBlue * 0.0722));
    assertEquals(newRed, sum);
    assertEquals(newGreen, sum);
    assertEquals(newBlue, sum);
  }

  @Test
  public void testSepia() {
    PixelOperationCommand pixel = new PixelOperationCommand("rocks", "sepia",
            new ColorTransformationOperation(new double[][]{
                    {0.393, 0.769, 0.189},
                    {0.349, 0.686, 0.168},
                    {0.272, 0.534, 0.131}}));
    pixel.apply(album, view);
    album.getImage("sepia");
    IImage sepia = album.getImage("sepia");

    int oldRed = image1.getPixel(75, 69).getRed();
    int oldGreen = image1.getPixel(75, 69).getGreen();
    int oldBlue = image1.getPixel(75, 69).getBlue();
    int newRed = sepia.getPixel(75, 69).getRed();
    int newGreen = sepia.getPixel(75, 69).getGreen();
    int newBlue = sepia.getPixel(75, 69).getBlue();

    int expectedRed = (int) ((oldRed * 0.393) + (oldGreen * 0.769) + (oldBlue * 0.189));
    int expectedGreen = (int) ((oldRed * 0.349) + (oldGreen * 0.686) + (oldBlue * 0.168));
    int expectedBlue = (int) ((oldRed * 0.272) + (oldGreen * 0.534) + (oldBlue * 0.131));

    assertEquals(expectedRed, newRed);
    assertEquals(expectedGreen, newGreen);
    assertEquals(expectedBlue, newBlue);
  }
}