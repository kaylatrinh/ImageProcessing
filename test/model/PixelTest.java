package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * This is a testing class for {@code Pixel} to ensure that the constructors work properly and
 * will catch an error for an invalid constructor, and to make sure getting and setting
 * individual channels works properly.
 */
public class PixelTest {

  IPixel one;
  IPixel two;
  IPixel three;

  @Before
  public void initData() {
    this.one = new Pixel(0, 0, 0);
    this.two = new Pixel(255, 255, 255);
    this.three = new Pixel(35, 111, 98);
  }

  @Test
  public void testValidConstructor() {
    assertEquals(0, one.getRed());
    assertEquals(0, one.getGreen());
    assertEquals(0, one.getBlue());

    assertEquals(255, two.getRed());
    assertEquals(255, two.getGreen());
    assertEquals(255, two.getGreen());

    assertEquals(35, three.getRed());
    assertEquals(111, three.getGreen());
    assertEquals(98, three.getBlue());
  }

  @Test
  public void testInvalidConstructor() {
    try {
      new Pixel(-1, 0, 3);
      fail("Invalid constructor does not fail.");
    } catch (IllegalArgumentException e) {
      assertNotNull(e);
    }

    try {
      new Pixel(0, 4, 300);
      fail("Invalid constructor does not fail.");
    } catch (IllegalArgumentException e) {
      assertNotNull(e);
    }
  }

  @Test
  public void testGetRed() {
    assertEquals(0, one.getRed());
    assertEquals(255, two.getRed());
    assertEquals(35, three.getRed());
  }

  @Test
  public void testGetGreen() {
    assertEquals(0, one.getGreen());
    assertEquals(255, two.getGreen());
    assertEquals(111, three.getGreen());
  }

  @Test
  public void testGetBlue() {
    assertEquals(0, one.getBlue());
    assertEquals(255, two.getBlue());
    assertEquals(98, three.getBlue());
  }

  @Test
  public void testToString() {
    assertEquals("Pixel(0, 0, 0)", this.one.toString());
    assertEquals("Pixel(255, 255, 255)", this.two.toString());
    assertEquals("Pixel(35, 111, 98)", this.three.toString());
  }
}