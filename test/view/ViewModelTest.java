package view;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the view model, which is responsible for keeping track of the currently-selected
 * image name.
 */
public class ViewModelTest {
  @Test
  public void testConstructor() {
    AtomicInteger runnableCalled = new AtomicInteger();

    ViewModel model = new ViewModel(() -> runnableCalled.addAndGet(1));

    // The runnable is not called initially
    assertEquals(0, runnableCalled.get());

    // When we set the active image, the runnable is called once
    model.setActiveImage("my very cool image");
    assertEquals(1, runnableCalled.get());

    // When we set it again, it is called again
    model.setActiveImage("my very cool image 2");
    assertEquals(2, runnableCalled.get());

    // ...even if we set it to the same value
    model.setActiveImage("my very cool image 2");
    assertEquals(3, runnableCalled.get());
  }

  @Test(expected = NullPointerException.class)
  public void testConstructorNullRunnable() {
    new ViewModel(null);
  }

  @Test
  public void testSetActiveImage() {
    ViewModel model = new ViewModel(() -> { } );

    model.setActiveImage("this is an image");
    assertEquals("this is an image", model.getActiveImage());

    model.setActiveImage("another image");
    assertEquals("another image", model.getActiveImage());
  }

  @Test(expected = NullPointerException.class)
  public void testSetActiveImageNull() {
    new ViewModel(() -> { } ).setActiveImage(null);
  }

  @Test
  public void testIsImagePresent() {
    ViewModel model = new ViewModel(() -> { } );

    assertFalse(model.isImagePresent());

    model.setActiveImage("an image! hooray");
    assertTrue(model.isImagePresent());

    model.setActiveImage("omg another one");
    assertTrue(model.isImagePresent());
  }

  @Test
  public void testGetActiveImage() {
    ViewModel model = new ViewModel(() -> { } );
    model.setActiveImage("hiii");
    assertEquals("hiii", model.getActiveImage());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetActiveImageNull() {
    new ViewModel(() -> { } ).getActiveImage();
  }
}
