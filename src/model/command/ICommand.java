package model.command;

import model.IAlbum;
import view.ImageView;

/**
 * Represents a command which the user can execute. Commands receive an IAlbum to manipulate and
 * an ImageView to output messages to.
 */
public interface ICommand {

  /**
   * Apply this command to the given album.
   *
   * @param album The album to manipulate.
   * @param view The view to show error messages to.
   */
  void apply(IAlbum album, ImageView view);
}
