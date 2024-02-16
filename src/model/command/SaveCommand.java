package model.command;

import java.io.IOException;
import java.util.Objects;

import control.ImageUtil;
import model.IAlbum;
import model.IImage;
import view.ImageView;

/**
 * Represents a command which saves some image to a file.
 */
public class SaveCommand implements ICommand {
  private final String imagePath;
  private final String imageName;

  public SaveCommand(String imagePath, String imageName) {
    this.imagePath = Objects.requireNonNull(imagePath);
    this.imageName = Objects.requireNonNull(imageName);
  }

  @Override
  public void apply(IAlbum album, ImageView view) {
    IImage image;
    try {
      image = album.getImage(this.imageName);
    } catch (IllegalArgumentException e) {
      view.renderMessage(e.getMessage() + "\n");
      return;
    }

    try {
      ImageUtil.saveImage(this.imagePath, image);
    } catch (IOException e) {
      view.renderMessage("Could not write file: " + e.getMessage() + "\n");
    }
  }
}
