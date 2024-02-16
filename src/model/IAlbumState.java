package model;

import java.util.Set;

/**
 * This interface is a place in order for our program to store the loaded photos. We used "Album"
 * for the interface to better parallel reality, where you would store photos in a photo album.
 * This interface does just that, and allows us to store the images into a map.
 */
public interface IAlbumState {
  /**
   * Gets an image with the specified name.
   *
   * @param imageName the name of the image trying to be found.
   * @return a {@code Image} that is being found.
   */
  IImage getImage(String imageName);

  /**
   * Get the images contained in the album.
   *
   * @return a set of image names which map to images in the album.
   */
  Set<String> getImageNames();
}
