package model;

/**
 * Represents an album which can be mutated by adding images.
 */
public interface IAlbum extends IAlbumState {

  /**
   * Adds an image to the map of images.
   *
   * @param image the image that is added to the map.
   */
  void addImage(IImage image);
}
