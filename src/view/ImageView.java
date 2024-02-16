package view;

import java.util.Set;

/**
 * Represents a view for the image manipulation program.
 */
public interface ImageView {
  /**
   * Renders the list of commands that the user can use.
   *
   * @param commands A map of command names to commands.
   */
  void renderCommands(Set<String> commands);

  /**
   * Renders the given message.
   *
   * @param message A message to render.
   */
  void renderMessage(String message);
}
