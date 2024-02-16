package model.command;

import java.util.Objects;

import model.IAlbum;
import model.IImage;
import view.ImageView;

/**
 * Represents a command which takes in some parameters and the name of an existing image in the
 * album, uses those parameters and the existing image to create a new image, and adds the new
 * image to the album.
 */
public abstract class AbstractCommand implements ICommand {
  private final String originalName;
  private final String destinationName;

  public AbstractCommand(String originalName, String destinationName) {
    this.originalName = Objects.requireNonNull(originalName);
    this.destinationName = Objects.requireNonNull(destinationName);
  }

  /**
   * Create an image from an existing image and some parameters.
   *
   * @param original   The original image to create the new image from.
   * @param imageName  The name of the new image.
   * @return The new image to add to the album.
   */
  protected abstract IImage createImage(IImage original, String imageName);

  @Override
  public final void apply(IAlbum album, ImageView view) {
    IImage original;
    try {
      original = album.getImage(this.originalName);
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage() + "\n");
      return;
    }

    album.addImage(this.createImage(original, this.destinationName));
  }
}
