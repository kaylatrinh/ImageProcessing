import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import control.ImageController;
import control.ImageControllerImpl;
import control.ImageGuiController;
import model.Album;
import model.IAlbum;
import view.ImageGuiView;
import view.ImageTextView;
import view.ImageView;
import view.SwingGuiView;

/**
 * Instantiates and runs components of the image processing program.
 */
public class ImageProcessing {
  /**
   * Invokes the image processing program.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    IAlbum album = new Album();

    if (args.length == 0) {
      ImageGuiView view = new SwingGuiView(album);
      new ImageGuiController(album, view);
    } else if (args[0].equals("-text")) {
      ImageView view = new ImageTextView(System.out);
      ImageController controller = new ImageControllerImpl(
              album, view, new InputStreamReader(System.in));
      controller.run();
    } else if (args[0].equals("-file")) {
      ImageView view = new ImageTextView(System.out);
      Readable input;

      try {
        input = new FileReader(args[1]);
      } catch (FileNotFoundException e) {
        view.renderMessage("File not found!\n");
        return;
      }

      ImageController controller = new ImageControllerImpl(album, view, input);
      controller.run();
    }
  }
}
