package view;

import java.io.IOException;
import java.util.Set;

/**
 * This is a class that outputs messages in the console which prompt users to enter a commmand,
 * in addition to displaying a list of possible commands and error messages.
 */
public class ImageTextView implements ImageView {
  private final Appendable output;

  /**
   * Constructs a text view with the specified outputs.
   *
   * @param output an appendable object.
   */
  public ImageTextView(Appendable output) {
    this.output = output;
  }

  @Override
  public void renderMessage(String message) {
    try {
      output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to output");
    }
  }

  @Override
  public void renderCommands(Set<String> commands) {
    this.renderMessage("Available commands:\n");
    for (String entry : commands) {
      this.renderMessage(String.format("%s\n", entry));
    }
  }
}
