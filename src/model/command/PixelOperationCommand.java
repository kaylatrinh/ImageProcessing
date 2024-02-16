package model.command;

import java.util.Objects;

import model.IImage;
import model.Image;
import model.operation.IPixelOperation;

/**
 * Represents a command which constructs a new image by applying an operation to each pixel
 * in an existing image.
 */
public class PixelOperationCommand extends AbstractCommand {
  private final IPixelOperation operation;

  /**
   * Constructs a command which applies the given operation to each pixel.
   *
   * @param operation The {@code IPixelOperation} to apply to each pixel.
   */
  public PixelOperationCommand(String originalName, String destinationName,
                               IPixelOperation operation) {
    super(originalName, destinationName);
    Objects.requireNonNull(operation);
    this.operation = operation;
  }

  @Override
  protected IImage createImage(IImage original, String imageName) {
    return new Image(original, this.operation, imageName);
  }
}
