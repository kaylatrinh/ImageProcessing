package control;

/**
 * This interface is the controller class for the model package of the Image assignment. This
 * class allows user input to be analyzed, and will not make any changes to how the program
 * functions. It will simply read the commands of the user.
 */
public interface ImageController {

  /**
   * The run method is a method in order to determine the command that is being called and
   * ensuring that the appropriate steps are taken to carry out the command.
   */
  void run();
}
