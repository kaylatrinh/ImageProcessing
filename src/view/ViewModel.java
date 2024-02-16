package view;

import java.util.Objects;

/**
 * A class for the image that is currently being worked on.
 */
class ViewModel {
  private String activeImageName;
  private final Runnable changeListener;

  /**
   * Consruct a view model that takes in a runnable.
   * @param changeListener The runnable that will listen for a change in the active image.
   */
  public ViewModel(Runnable changeListener) {
    this.changeListener = Objects.requireNonNull(changeListener);
  }

  /**
   * A method that sets the image to be worked on as the active image.
   * @param imageName the name of the image.
   */
  public void setActiveImage(String imageName) {
    this.activeImageName = Objects.requireNonNull(imageName);
    changeListener.run();
  }

  /**
   * A method to determine if there is an active image.
   * @return true if there is an image present, false if null.
   */
  public boolean isImagePresent() {
    return this.activeImageName != null;
  }

  /**
   * Gets the image that is currently active.
   * @return the name of the active image.
   */
  public String getActiveImage() {
    if (this.activeImageName == null) {
      throw new IllegalStateException("Active image not yet set!");
    }
    return this.activeImageName;
  }
}
