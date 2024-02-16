package model.command;

import java.io.IOException;
import java.util.Objects;

import control.ImageUtil;
import model.IAlbum;
import model.IImage;
import view.ImageView;

/**
 * Represents a command which loads an image into the album.
 */
public class LoadCommand implements ICommand {
  private final String imagePath;
  private final String imageName;

  public LoadCommand(String imagePath, String imageName) {
    this.imagePath = Objects.requireNonNull(imagePath);
    this.imageName = Objects.requireNonNull(imageName);
  }

  @Override
  public void apply(IAlbum album, ImageView view) {
    try {
      IImage image = ImageUtil.loadImage(this.imagePath, this.imageName);
      album.addImage(image);
    } catch (IOException e) {
      view.renderMessage("Failed to load image: " + e + "\n");
    }
  }

}
