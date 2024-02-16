package controller;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import control.ControllerFeatures;
import control.ImageGuiController;
import model.Album;
import model.IAlbum;
import model.command.FlipCommand;
import model.command.LoadCommand;
import view.ImageGuiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the asynchronous controller (which is used with the GUI view).
 */
public class ImageGuiControllerTest {
  private static class MockGuiView implements ImageGuiView {
    private int refreshCount = 0;
    private final List<String> messages = new ArrayList<>();
    private boolean hasFeatures = false;

    @Override
    public void useFeatures(ControllerFeatures features) {
      this.hasFeatures = true;
    }

    public boolean hasFeatures() {
      return this.hasFeatures;
    }

    @Override
    public void refresh() {
      this.refreshCount += 1;
    }

    public int getRefreshCount() {
      return this.refreshCount;
    }

    @Override
    public void renderCommands(Set<String> commands) {
      // Unused in the unit tests that follow
    }

    @Override
    public void renderMessage(String message) {
      this.messages.add(message);
    }

    public List<String> getMessages() {
      return this.messages;
    }
  }

  @Test
  public void testConstructor() {
    MockGuiView view = new MockGuiView();
    IAlbum album = new Album();

    // Make sure the constructor sets up the features on the view
    assertFalse(view.hasFeatures());
    new ImageGuiController(album, view);
    assertTrue(view.hasFeatures());
  }

  @Test(expected = NullPointerException.class)
  public void testNullAlbum() {
    new ImageGuiController(null, new MockGuiView());
  }

  @Test(expected = NullPointerException.class)
  public void testNullView() {
    new ImageGuiController(new Album(), null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullAll() {
    new ImageGuiController(null, null);
  }

  @Test
  public void testRunCommand() {
    IAlbum album = new Album();
    MockGuiView view = new MockGuiView();
    ImageGuiController controller = new ImageGuiController(album, view);

    // the command is applied to the stored album
    controller.runCommand(new LoadCommand("res/rocks.ppm", "rocks"));
    assertNotNull(album.getImage("rocks"));

    // the view is properly refreshed
    assertEquals(1, view.getRefreshCount());
    assertEquals(0, view.getMessages().size());

    // error messages are outputted to the stored view
    controller.runCommand(new LoadCommand("not-a-path/not-an-image.ppm", "rocks"));
    assertEquals(2, view.getRefreshCount());
    assertEquals(1, view.getMessages().size());
    assertEquals("Failed to load image: " +
            "java.io.FileNotFoundException: " +
            "not-a-path/not-an-image.ppm (No such file or directory)\n", view.getMessages().get(0));

    // commands continue to run fine after the error
    controller.runCommand(new FlipCommand("rocks", "flipped", true));
    controller.runCommand(new FlipCommand("flipped", "two-flips", true));
    assertEquals(3, album.getImageNames().size());
    assertEquals(album.getImage("rocks"), album.getImage("two-flips"));
  }

  @Test(expected = NullPointerException.class)
  public void testNullCommand() {
    new ImageGuiController(new Album(), new MockGuiView()).runCommand(null);
  }
}
