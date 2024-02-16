package model.command;

import model.IImage;
import model.Image;
import model.operation.IKernelOperation;
import model.operation.KernelOperation;

/**
 * This class represents a command that will be called onto a kernel.
 */
public class KernelOperationCommand extends AbstractCommand {
  private final IKernelOperation operation;

  /**
   * A constructor for the {@code KernelOperationCommand} that takes in a 2d array that the
   * operations will be called on.
   *
   * @param kernelArray the 2d array that will be called onto the image.
   */
  public KernelOperationCommand(String originalName, String destinationName,
                                double[][] kernelArray) {
    super(originalName, destinationName);
    this.operation = new KernelOperation(kernelArray);
  }

  @Override
  protected IImage createImage(IImage original, String imageName) {
    return new Image(original, this.operation, imageName);
  }
}
