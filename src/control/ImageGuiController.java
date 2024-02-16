package control;

import java.util.Objects;

import model.IAlbum;
import model.command.ICommand;
import view.ImageGuiView;

/**
 * An asynchronous controller which controls a GUI view.
 */
public class ImageGuiController implements ControllerFeatures {
  private final IAlbum album;
  private final ImageGuiView view;

  /**
   * Construct a controller with the given album state and the given GUI view.
   * @param album The album state.
   * @param view The GUI view.
   */
  public ImageGuiController(IAlbum album, ImageGuiView view) {
    this.album = Objects.requireNonNull(album);
    this.view = Objects.requireNonNull(view);

    this.view.useFeatures(this);
  }

  @Override
  public void runCommand(ICommand command) {
    Objects.requireNonNull(command);
    command.apply(this.album, this.view);
    this.view.refresh();
  }
}
