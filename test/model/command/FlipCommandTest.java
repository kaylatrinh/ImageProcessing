package model.command;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import control.ImageController;
import control.ImageControllerImpl;
import control.ImageUtil;
import model.Album;
import model.IAlbum;
import model.IImage;
import view.ImageTextView;
import view.ImageView;

import static org.junit.Assert.assertEquals;

/**
 * This is a testing class for the {@code FlipCommand} that checks to ensure the correctness of
 * flipping an image horizontally or vertically. We do this by comparing a pixel from the old
 * image and ensuring that it is in the expected position in the new image.
 */
public class FlipCommandTest {
  IImage image1;
  IAlbum album;
  ImageView view;
  ImageController control;
  Reader in;

  @Before
  public void initData() throws IOException {
    image1 = ImageUtil.readPPM("res/rocks.ppm", "rocks");
    album = new Album();
    album.addImage(image1);
    view = new ImageTextView(new StringBuilder());
    in = new StringReader("");
    control = new ImageControllerImpl(album, view, in);
  }

  @Test
  public void testVerticalFlip() {
    FlipCommand flip = new FlipCommand("rocks", "vertical", true);
    flip.apply(album, view);
    album.getImage("vertical");
    IImage vertical = album.getImage("vertical");
    assertEquals(vertical.getPixel(75, 160 - 1 - 69), image1.getPixel(75, 69));
    assertEquals(vertical.getPixel(0, 160 - 1), image1.getPixel(0, 0));
    assertEquals(vertical.getPixel(199, 0), image1.getPixel(199, 159));
  }

  @Test
  public void testHorizontalFlip() {
    FlipCommand flip = new FlipCommand("rocks", "horizontal", false);
    flip.apply(album, view);
    album.getImage("horizontal");
    IImage horizontal = album.getImage("horizontal");
    assertEquals(horizontal.getPixel(200 - 75 - 1, 69), image1.getPixel(75, 69));
    assertEquals(horizontal.getPixel(199, 0), image1.getPixel(0, 0));
    assertEquals(horizontal.getPixel(0, 159), image1.getPixel(199, 159));
  }
}