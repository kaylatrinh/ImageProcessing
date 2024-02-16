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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A testing class for {@code LoadCommand} that ensures that images can be loaded in
 * correctly.
 */
public class LoadCommandTest {
  IAlbum album;
  IImage rocks;
  IImage dock;
  IImage mountains;
  IImage sunset;
  StringBuffer viewBuffer;
  ImageView view;

  @Before
  public void initData() throws IOException {
    album = new Album();

    rocks = ImageUtil.readPPM("res/rocks.ppm", "rocks");
    dock = ImageUtil.loadImage("res/dock.bmp", "dock");
    mountains = ImageUtil.loadImage("res/mountains.jpg", "mountains");
    sunset = ImageUtil.loadImage("res/sunset.png", "sunset");

    viewBuffer = new StringBuffer();
    view = new ImageTextView(viewBuffer);
  }

  @Test
  public void testLoadImage() {
    ICommand load = new LoadCommand("res/rocks.ppm", "loaded-rocks");
    load.apply(album, view);

    IImage loaded = album.getImage("loaded-rocks");
    assertNotNull(loaded);

    assertEquals(rocks.getPixel(12, 34), loaded.getPixel(12, 34));
  }

  @Test
  public void testLoadPng() {
    ICommand load = new LoadCommand("res/sunset.png", "loaded-sunset");
    load.apply(album, view);

    IImage loaded = album.getImage("loaded-sunset");
    assertNotNull(loaded);

    assertEquals(sunset.getPixel(12, 34), loaded.getPixel(12, 34));
  }

  @Test
  public void testLoadJpg() {
    ICommand load = new LoadCommand("res/mountains.jpg", "loaded-mountains");
    load.apply(album, view);

    IImage loaded = album.getImage("loaded-mountains");
    assertNotNull(loaded);

    assertEquals(mountains.getPixel(12, 34), loaded.getPixel(12, 34));
  }

  @Test
  public void testLoadBmp() {
    ICommand load = new LoadCommand("res/dock.bmp", "loaded-dock");
    load.apply(album, view);

    IImage loaded = album.getImage("loaded-dock");
    assertNotNull(loaded);

    assertEquals(dock.getPixel(12, 34), loaded.getPixel(12, 34));
  }

  @Test
  public void testLoadInvalidPath() {
    ICommand load = new LoadCommand("res/does-not-exist.ppm", "loaded-invalid");
    load.apply(album, view);

    try {
      album.getImage("loaded-invalid");
      fail("Image was present!");
    } catch (IllegalArgumentException e) {
      assertNotNull(e);
    }

    assertTrue(viewBuffer.toString().contains("Failed to load image"));
  }
}