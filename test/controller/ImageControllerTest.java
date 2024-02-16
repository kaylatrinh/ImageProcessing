package controller;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;

import control.ImageController;
import control.ImageControllerImpl;
import model.Album;
import model.IAlbum;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of the image processing program controller: interpreting commands
 * and printing output as needed.
 */
public class ImageControllerTest {
  @Test(expected = NullPointerException.class)
  public void testNullAlbum() {
    new ImageControllerImpl(null, new ImageTextView(new StringBuffer()), new StringReader(""));
  }

  @Test(expected = NullPointerException.class)
  public void testNullView() {
    new ImageControllerImpl(new Album(), null, new StringReader(""));
  }

  @Test(expected = NullPointerException.class)
  public void testNullReadable() {
    new ImageControllerImpl(new Album(), new ImageTextView(new StringBuffer()), null);
  }

  @Test
  public void testPrintWelcome() {
    Appendable output = new StringBuffer();
    ImageController controller = new ImageControllerImpl(
            new Album(), new ImageTextView(output), new StringReader(""));

    controller.run();

    String expected = "Available commands:\n" +
            "save\n" +
            "load\n" +
            "red-component\n" +
            "green-component\n" +
            "blue-component\n" +
            "luma\n" +
            "intensity\n" +
            "value\n" +
            "brighten\n" +
            "darken\n" +
            "vertical-flip\n" +
            "horizontal-flip\n" +
            "blur\n" +
            "sharpen\n" +
            "grayscale\n" +
            "sepia\n" +
            "Enter command:\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testReadFile() throws FileNotFoundException {
    Appendable output = new StringBuffer();
    IAlbum album = new Album();
    ImageController controller = new ImageControllerImpl(
            album, new ImageTextView(output), new FileReader("res/script.txt"));

    controller.run();

    // Make sure we got the welcome message

    String expected = "Available commands:\n" +
            "save\n" +
            "load\n" +
            "red-component\n" +
            "green-component\n" +
            "blue-component\n" +
            "luma\n" +
            "intensity\n" +
            "value\n" +
            "brighten\n" +
            "darken\n" +
            "vertical-flip\n" +
            "horizontal-flip\n" +
            "blur\n" +
            "sharpen\n" +
            "grayscale\n" +
            "sepia\n" +
            "Enter command:\n";

    assertTrue(output.toString().startsWith(expected));

    // Make sure we actually performed operations and created images

    String[] expectedImages = {
      "rocks",
      "dock",
      "mountains",
      "sunset",
      "red",
      "green",
      "blue",
      "luma",
      "intensity",
      "value",
      "horizontal",
      "vertical",
      "bright",
      "dark",
      "blurred",
      "sharpened",
      "grayscaled",
      "sepia"
    };

    for (String expectedImage : expectedImages) {
      assertNotNull(album.getImage(expectedImage));
    }
  }

  @Test
  public void testLoadModifySave() {
    Appendable output = new StringBuffer();
    IAlbum album = new Album();

    String commands = "load res/rocks.ppm rocks" +
            " luma rocks rocks-luma save res/luma.ppm rocks-luma";

    ImageController controller = new ImageControllerImpl(
            album, new ImageTextView(output), new StringReader(commands));

    controller.run();

    assertNotNull(album.getImage("rocks"));
    assertNotNull(album.getImage("rocks-luma"));
  }

  @Test
  public void testInvalidCommandName() {
    Appendable output = new StringBuffer();
    IAlbum album = new Album();

    String commands = "load res/rocks.ppm rocks  luma rocks rocks-luma" +
            "  invalid rocks rocks-invalid  red-component rocks red-rocks";

    ImageController controller = new ImageControllerImpl(
            album, new ImageTextView(output), new StringReader(commands));

    controller.run();

    // An error should be printed for each invalid token
    assertEquals(3, Arrays.stream(
            output.toString().split("\n")).filter(
              s -> s.equals("Command not found!")).count());

    assertNotNull(album.getImage("rocks"));
    assertNotNull(album.getImage("rocks-luma"));
    // The command following the invalid commands should still be executed
    assertNotNull(album.getImage("red-rocks"));
  }

  @Test
  public void testFailedCommand() {
    Appendable output = new StringBuffer();
    IAlbum album = new Album();

    String commands = "load res/rocks.ppm rocks  luma rocks rocks-luma  " +
            "save nonexistent-directory/luma.ppm rocks-luma  red-component rocks red-rocks";

    ImageController controller = new ImageControllerImpl(
            album, new ImageTextView(output), new StringReader(commands));

    controller.run();

    // An error should be printed
    assertNotNull(Arrays.stream(
            output.toString().split("\n")).filter(
              s -> s.equals("Could not write file: " +
                    "nonexistent-directory/luma.ppm (No such file " +
                    "or directory)")).findFirst());

    assertNotNull(album.getImage("rocks"));
    assertNotNull(album.getImage("rocks-luma"));
    // The command following the invalid commands should still be executed
    assertNotNull(album.getImage("red-rocks"));
  }
}
