package model.operation;

import org.junit.Before;
import org.junit.Test;

import model.IPixel;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * This class is a testing class for {@code OffsetOperation} that initializes data such as a
 * pixel to call the operation on, and the offset value that will alter the pixel. The
 * constructor is also tested to ensure completeness of testing.
 */
public class OffsetOperationTest {
  IPixel pixel1;
  IPixel pixel2;
  IPixel pixel3;

  OffsetOperation offset1;
  OffsetOperation offset2;
  OffsetOperation offset3;

  @Before
  public void initData() {
    pixel1 = new Pixel(10, 15, 255);
    pixel2 = new Pixel(255, 255, 255);
    pixel3 = new Pixel(0, 0, 0);
    offset1 = new OffsetOperation(5);
    offset2 = new OffsetOperation(-2);
    offset3 = new OffsetOperation(0);
  }

  @Test
  public void testApply() {
    // Tests to brighten image.
    assertEquals(new Pixel(15, 20, 255), offset1.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), offset1.apply(pixel2));
    assertEquals(new Pixel(5, 5, 5), offset1.apply(pixel3));

    // Tests to darken image.
    assertEquals(new Pixel(8, 13, 253), offset2.apply(pixel1));
    assertEquals(new Pixel(253, 253, 253), offset2.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), offset2.apply(pixel3));

    // Tests for no change in offset.
    assertEquals(new Pixel(10, 15, 255), offset3.apply(pixel1));
    assertEquals(new Pixel(255, 255, 255), offset3.apply(pixel2));
    assertEquals(new Pixel(0, 0, 0), offset3.apply(pixel3));
  }
}