package model.command;

import model.IImage;
import model.IPixel;
import model.Image;

/**
 * Represents a command which flips an image vertically or horizontally.
 * This command takes no additional parameters at the time of usage.
 */
public class FlipCommand extends AbstractCommand {
  private final boolean flipVertical;

  public FlipCommand(String originalName, String destinationName, boolean flipVertical) {
    super(originalName, destinationName);
    this.flipVertical = flipVertical;
  }

  @Override
  protected IImage createImage(IImage original, String imageName) {
    int width = original.getWidth();
    int height = original.getHeight();

    IPixel[][] newArray = new IPixel[height][width];

    for (int originalY = 0; originalY < height; ++originalY) {
      for (int originalX = 0; originalX < width; ++originalX) {
        IPixel old = original.getPixel(originalX, originalY);

        if (this.flipVertical) {
          newArray[height - 1 - originalY][originalX] = old;
        } else {
          newArray[originalY][width - 1 - originalX] = old;
        }
      }
    }

    return new Image(width, height, newArray, imageName);
  }
}
