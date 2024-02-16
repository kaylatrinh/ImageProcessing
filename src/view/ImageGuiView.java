package view;

import control.ControllerFeatures;

/**
 * A view which uses the asynchronous controller (e.g. by outputting to a GUI).
 */
public interface ImageGuiView extends ImageView {
  /**
   * Configure this view to use the given features object to manipulate the model.
   * @param features An object which implements the given features to manipulate the model.
   */
  void useFeatures(ControllerFeatures features);

  /**
   * Refresh this view to make sure it is displaying the most current state of the album.
   */
  void refresh();
}
