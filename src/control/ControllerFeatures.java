package control;

import model.command.ICommand;

/**
 * Represents the features which the view can invoke on the controller.
 */
public interface ControllerFeatures {
  /**
   * Run the specified command on the image album.
   * @param command The command to run on the album.
   */
  void runCommand(ICommand command);
}
