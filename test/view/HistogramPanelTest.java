package view;

import org.junit.Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import control.ImageUtil;
import model.Album;
import model.Histogram;
import model.IAlbum;
import model.IImage;

import static org.junit.Assert.fail;

/**
 * Tests that the histogram panel properly draws the given image.
 */
public class HistogramPanelTest {
  static int getMinExpected(int[] values, int index) {
    int min = values[index];
    if (index > 0) {
      min = Math.min(min, values[index - 1]);
    }
    if (index < values.length - 1) {
      min = Math.min(min, values[index + 1]);
    } else {
      min = 0; // if we're at the end, it'll return to zero
    }
    return min;
  }

  static int getMaxExpected(int[] values, int index) {
    int max = values[index];
    if (index > 0) {
      max = Math.max(max, values[index - 1]);
    }
    if (index < values.length - 1) {
      max = Math.max(max, values[index + 1]);
    }
    return max;
  }

  @Test
  public void testPaintComponent() throws IOException {
    // Set up album
    IAlbum album = new Album();
    IImage rocks = ImageUtil.loadImage("res/rocks.ppm", "rocks");
    album.addImage(rocks);

    // Reference histogram
    Histogram histogram = new Histogram(rocks);
    int maximum = histogram.getMaximum();

    int width = 256;
    int height = 1040;

    // Prepare histogram panel
    ViewModel vm = new ViewModel(() -> { } );
    vm.setActiveImage("rocks");
    HistogramPanel panel = new HistogramPanel(album, vm);
    panel.setSize(new Dimension(width, height));

    // Render histogram panel
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    panel.paint(graphics);

    // Compare image to actual histogram
    double scale = (height - 40.0) / maximum;
    int baseline = height - 20;

    // Figure out where we expect the line to be
    int[] expectedRedHeights = new int[256];
    int[] expectedGreenHeights = new int[256];
    int[] expectedBlueHeights = new int[256];
    int[] expectedIntensityHeights = new int[256];

    for (int value = 0; value < 256; ++value) {
      int redCount = histogram.getRedAt(value);
      int greenCount = histogram.getGreenAt(value);
      int blueCount = histogram.getBlueAt(value);
      int intensityCount = histogram.getIntensityAt(value);

      expectedRedHeights[value] = baseline - (int) (redCount * scale);
      expectedGreenHeights[value] = baseline - (int) (greenCount * scale);
      expectedBlueHeights[value] = baseline - (int) (blueCount * scale);
      expectedIntensityHeights[value] = baseline - (int) (intensityCount * scale);
    }

    // Look for the line!
    for (int value = 0; value < 256; ++value) {
      for (int actualHeight = height - 20; actualHeight > 20; actualHeight -= 1) {
        int pixel = image.getRGB(value, actualHeight);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        if (red == 255 && green == 255 && blue == 255) {
          if (actualHeight == expectedRedHeights[value]
                  || actualHeight == expectedGreenHeights[value]
                  || actualHeight == expectedBlueHeights[value]
                  || actualHeight == expectedIntensityHeights[value]) {
            fail("Pixel at (" + value + ", " + actualHeight
                    + ") is white, but it should be colored in");
          }
        } else if (red == 255 && green == 0 && blue == 0) {
          if (actualHeight < getMinExpected(expectedRedHeights, value)
                  || actualHeight > getMaxExpected(expectedRedHeights, value)) {
            fail("Unexpected red pixel found at (" + value + ", " + actualHeight
                    + "), should be at " + expectedRedHeights[value]);
          }
        } else if (red == 0 && green == 255 && blue == 0) {
          if (actualHeight < getMinExpected(expectedGreenHeights, value)
                  || actualHeight > getMaxExpected(expectedGreenHeights, value)) {
            fail("Unexpected green pixel found at (" + value + ", " + actualHeight
                    + "), should be at " + expectedGreenHeights[value]);
          }
        } else if (red == 0 && green == 0 && blue == 255) {
          if (actualHeight < getMinExpected(expectedBlueHeights, value)
                  || actualHeight > getMaxExpected(expectedBlueHeights, value)) {
            fail("Unexpected blue pixel found at (" + value + ", " + actualHeight
                    + "), should be at " + expectedBlueHeights[value]);
          }
        } else if (red == 0 && green == 0 && blue == 0) {
          if (actualHeight < getMinExpected(expectedIntensityHeights, value)
                  || actualHeight > getMaxExpected(expectedIntensityHeights, value)) {
            fail("Unexpected black pixel found at (" + value + ", " + actualHeight
                    + "), should be at " + expectedIntensityHeights[value]);
          }
        } else {
          fail("Unexpected color (" + red + ", " + green + ", " + blue
                  + ") found at position (" + value + ", " + actualHeight + ")");
        }
      }
    }
  }
}
