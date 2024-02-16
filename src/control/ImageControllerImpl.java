package control;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;

import model.IAlbum;
import model.command.FlipCommand;
import model.command.ICommand;
import model.command.KernelOperationCommand;
import model.command.LoadCommand;
import model.command.PixelOperationCommand;
import model.command.SaveCommand;
import model.operation.ColorTransformationOperation;
import model.operation.OffsetOperation;
import model.operation.ValueOperation;
import view.ImageView;

/**
 * This is a class to implement the methods of {@code ImageController}. There is only one public
 * method in this class, and that is to run the program with the specified commands. However,
 * there are private helper methods to aid the run method.
 */
public class ImageControllerImpl implements ImageController {
  private final IAlbum album;
  private final ImageView view;
  private final Readable input;
  private final Map<String, Function<Scanner, ICommand>> commands;

  /**
   * This is a constructor for {@code ImageControllerImpl} that initialize the commands and sets
   * the album, view, and input.
   *
   * @param album the album that is being passed in.
   * @param view  the view that the controller should output to.
   * @param input the source of user input.
   */
  public ImageControllerImpl(IAlbum album, ImageView view, Readable input) {
    Objects.requireNonNull(album);
    Objects.requireNonNull(view);
    Objects.requireNonNull(input);
    this.album = album;
    this.view = view;
    this.input = input;

    this.commands = new LinkedHashMap<>();
    this.initCommands();
  }

  /**
   * Initialize the map of command names to Command objects.
   */
  private void initCommands() {
    this.commands.put("save", s -> new SaveCommand(s.next(), s.next()));
    this.commands.put("load", s -> new LoadCommand(s.next(), s.next()));

    this.commands.put("red-component",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        1, 0, 0)));
    this.commands.put("green-component",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        0, 1, 0)));
    this.commands.put("blue-component",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        0, 0, 1)));

    this.commands.put("luma",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        0.2126, 0.7152, 0.0722)));
    this.commands.put("intensity",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        1.0 / 3, 1.0 / 3, 1.0 / 3)));
    this.commands.put("value",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ValueOperation()));

    this.commands.put("brighten",
        (s) -> {
          int offset = s.nextInt();
          return new PixelOperationCommand(s.next(), s.next(), new OffsetOperation(offset));
        });
    this.commands.put("darken",
        (s) -> {
          int offset = -s.nextInt();
          return new PixelOperationCommand(s.next(), s.next(), new OffsetOperation(offset));
        });

    this.commands.put("vertical-flip",
        s -> new FlipCommand(s.next(), s.next(), true));
    this.commands.put("horizontal-flip",
        s -> new FlipCommand(s.next(), s.next(), false));

    this.commands.put("blur", s -> new KernelOperationCommand(s.next(), s.next(), new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    }));

    this.commands.put("sharpen", s -> new KernelOperationCommand(s.next(), s.next(), new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    }));

    this.commands.put("grayscale",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(
                        0.2126, 0.7152, 0.0722)));

    this.commands.put("sepia",
        s -> new PixelOperationCommand(s.next(), s.next(),
                new ColorTransformationOperation(new double[][]{
                    {0.393, 0.769, 0.189},
                    {0.349, 0.686, 0.168},
                    {0.272, 0.534, 0.131}})));
  }

  @Override
  public void run() {
    Scanner scan = new Scanner(this.input);

    this.view.renderCommands(this.commands.keySet());

    this.view.renderMessage("Enter command:\n");
    while (scan.hasNext()) {
      String commandName = scan.next();

      if (!this.commands.containsKey(commandName)) {
        this.view.renderMessage("Command not found!\n");
      } else {
        ICommand command = this.commands.get(commandName).apply(scan);
        command.apply(this.album, this.view);
      }

      this.view.renderMessage("Enter command:\n");
    }
  }
}
