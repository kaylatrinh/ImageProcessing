package model.command;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import control.ImageUtil;
import model.Album;
import model.IAlbum;
import model.IImage;
import view.ImageTextView;
import view.ImageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A testing class for {@code SaveCommand} that ensures that images are able to be saved correctly.
 */
public class SaveCommandTest {
  IAlbum album;
  IImage rocks;
  StringBuffer viewBuffer;
  ImageView view;

  @Before
  public void initData() throws IOException {
    album = new Album();
    rocks = ImageUtil.readPPM("res/rocks.ppm", "rocks");
    album.addImage(rocks);
    viewBuffer = new StringBuffer();
    view = new ImageTextView(viewBuffer);
  }

  @Test
  public void testSaveImage() throws IOException {
    ICommand save = new SaveCommand("res/rocks.ppm", "rocks");
    save.apply(album, view);

    IImage saved = ImageUtil.readPPM("res/rocks.ppm", "rocks-saved");
    assertEquals(rocks.getPixel(12, 34), saved.getPixel(12, 34));
  }

  @Test
  public void testSavePng() throws IOException {
    ICommand save = new SaveCommand("res/rocks.png", "rocks");
    save.apply(album, view);

    IImage saved = ImageUtil.loadImage("res/rocks.png", "rocks-saved");
    assertEquals(rocks.getPixel(12, 34), saved.getPixel(12, 34));
  }

  @Test
  public void testSaveJpg() throws IOException {
    ICommand save = new SaveCommand("res/rocks.jpg", "rocks");
    save.apply(album, view);

    IImage saved = ImageUtil.loadImage("res/rocks.jpg", "rocks-saved");
    // JPEGs are lossy, so we assert that it's "close enough"
    assertEquals(rocks.getPixel(12, 34).getRed(), saved.getPixel(12, 34).getRed(), 5);
  }

  @Test
  public void testSaveBmp() throws IOException {
    ICommand save = new SaveCommand("res/rocks.bmp", "rocks");
    save.apply(album, view);

    IImage saved = ImageUtil.loadImage("res/rocks.bmp", "rocks-saved");
    assertEquals(rocks.getPixel(12, 34), saved.getPixel(12, 34));
  }

  @Test
  public void testSaveInvalidImage() throws IOException {
    ICommand save = new SaveCommand("res/rocks.ppm", "not-an-image");
    save.apply(album, view);

    // didn't mutate the saved image
    IImage saved = ImageUtil.readPPM("res/rocks.ppm", "rocks-saved");
    assertEquals(rocks.getPixel(12, 34), saved.getPixel(12, 34));

    // said an error
    assertTrue(viewBuffer.toString().contains("Image not-an-image not found"));
  }

  @Test
  public void testSaveInvalidPath() throws IOException {
    // directory, not a file
    ICommand save = new SaveCommand("not-a-directory/rocks.png", "rocks");
    save.apply(album, view);

    // didn't mutate the saved image
    IImage saved = ImageUtil.readPPM("res/rocks.ppm", "rocks-saved");
    assertEquals(rocks.getPixel(12, 34), saved.getPixel(12, 34));

    // said an error
    assertTrue(viewBuffer.toString().contains("Could not write file"));
  }
}