package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class is an implementation of {@code IAlbum} that stores the information about the given
 * images into a map. This makes it easier to access specific pictures and its name when needed.
 * The two methods in this class are addImage, which adds an image to the album, and get image
 * which retrieves an image with the specified name.
 */
public class Album implements IAlbum {
  Map<String, IImage> images;

  /**
   * An album contains a map of images. A HashMap is used in order to store a key, which is the
   * name of the image, and it's value, which is the image itself.
   */
  public Album() {
    this.images = new HashMap<>();
  }

  @Override
  public void addImage(IImage image) {
    Objects.requireNonNull(image);
    this.images.put(image.getImageName(), image);
  }

  @Override
  public IImage getImage(String imageName) {
    if (!this.images.containsKey(imageName)) {
      throw new IllegalArgumentException("Image " + imageName + " not found!");
    }
    return this.images.get(imageName);
  }

  @Override
  public Set<String> getImageNames() {
    return this.images.keySet();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Album album = (Album) o;
    return images.equals(album.images);
  }

  @Override
  public int hashCode() {
    return Objects.hash(images);
  }
}
