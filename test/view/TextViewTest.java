package view;

import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the view for the program which writes output as text to an Appendable.
 */
public class TextViewTest {
  @Test
  public void testRenderMessage() {
    Appendable output = new StringBuffer();
    ImageView view = new ImageTextView(output);

    view.renderMessage("Hello World!\n");
    view.renderMessage("Test message.\n");

    assertEquals("Hello World!\nTest message.\n", output.toString());
  }

  @Test
  public void testFailToWriteMessage() {
    Appendable output = new NotAppendable();
    ImageView view = new ImageTextView(output);

    try {
      view.renderMessage("Hello World!\n");
      fail("Did not throw exception");
    } catch (IllegalStateException e) {
      assertEquals("Failed to write to output", e.getMessage());
    }
  }

  @Test
  public void testRenderCommands() {
    Set<String> commands = new LinkedHashSet<>();
    commands.add("command-one");
    commands.add("command-two");
    commands.add("command-three");

    Appendable output = new StringBuffer();
    ImageView view = new ImageTextView(output);

    view.renderCommands(commands);

    assertEquals("Available commands:\ncommand-one\n" +
            "command-two\ncommand-three\n", output.toString());
  }

  static class NotAppendable implements Appendable {

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw new IOException("");
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw new IOException("");
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw new IOException("");
    }
  }
}
