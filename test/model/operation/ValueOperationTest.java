package model.operation;

import org.junit.Before;
import org.junit.Test;

import model.IPixel;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * A testing class for the {@code ValueOperation} that ensures that the value greyscale operation
 * works correctly.
 */
public class ValueOperationTest {
  IPixel pixel1;
  IPixel pixel2;
  IPixel pixel3;
  ValueOperation value1;


  @Before
  public void initData() {
    pixel1 = new Pixel(10, 15, 255);
    pixel2 = new Pixel(255, 255, 255);
    pixel3 = new Pixel(0, 0, 0);
    value1 = new ValueOperation();
  }

  @Test
  public void testApply() {
    assertEquals(new Pixel(255, 255, 255), value1.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), value1.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), value1.apply(pixel3));
  }
}