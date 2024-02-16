package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * A test for the {@code Album} that ensures albums are created correctly and images are able to
 * be stored in an album.
 */
public class AlbumTest {
  IImage image = new Image(1, 1, new IPixel[][]{{new Pixel(255, 255, 255)}}, "simple-image");

  @Test
  public void testConstructor() {
    IAlbum album = new Album();
    assertNotNull(album);
  }

  @Test
  public void testAddGetImage() {
    IAlbum album = new Album();

    album.addImage(this.image);

    assertEquals(this.image, album.getImage("simple-image"));
  }

  @Test(expected = NullPointerException.class)
  public void testAddNullImage() {
    new Album().addImage(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInvalidKey() {
    IAlbum album = new Album();
    album.addImage(this.image);

    album.getImage("image-that-doesnt-exist");
  }
}
