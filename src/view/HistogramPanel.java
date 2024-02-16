package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JPanel;

import model.Histogram;
import model.IAlbumState;

/**
 * A panel which shows the histogram of the current image.
 */
public class HistogramPanel extends JPanel {
  private static final double X_SCALE = 1; // how many horizontal pixels each item on the graph is
  private static final int Y_PAD = 20;

  private final IAlbumState album;
  private final ViewModel viewModel;

  /**
   * Construct a histogram panel using the given album and view model.
   * @param album The album to retrieve images from.
   * @param viewModel The view model to retrieve the current active image from.
   */
  public HistogramPanel(IAlbumState album, ViewModel viewModel) {
    super();

    this.album = Objects.requireNonNull(album);
    this.viewModel = Objects.requireNonNull(viewModel);

    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(300, 200));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (!this.viewModel.isImagePresent()) {
      return;
    }

    Histogram histogram;
    histogram = new Histogram(this.album.getImage(this.viewModel.getActiveImage()));

    int graphStart = (this.getWidth() - (int)(X_SCALE * 256)) / 2;

    int[] xAxis = new int[256];
    for (int i = 0; i < 256; ++i) {
      xAxis[i] = graphStart + (int)(i * X_SCALE);
    }

    int maxValue = histogram.getMaximum();
    int baseline = this.getHeight() - Y_PAD;
    double scale = (double)(this.getHeight() - 2 * Y_PAD) / maxValue;

    int[] redLine = new int[256];
    for (int i = 0; i < 256; ++i) {
      redLine[i] = baseline - (int)(histogram.getRedAt(i) * scale);
    }

    int[] greenLine = new int[256];
    for (int i = 0; i < 256; ++i) {
      greenLine[i] = baseline - (int)(histogram.getGreenAt(i) * scale);
    }

    int[] blueLine = new int[256];
    for (int i = 0; i < 256; ++i) {
      blueLine[i] = baseline - (int)(histogram.getBlueAt(i) * scale);
    }

    int[] intensityLine = new int[256];
    for (int i = 0; i < 256; ++i) {
      intensityLine[i] = baseline - (int)(histogram.getIntensityAt(i) * scale);
    }

    g.setColor(Color.RED);
    g.drawPolyline(xAxis, redLine, 256);
    g.setColor(Color.GREEN);
    g.drawPolyline(xAxis, greenLine, 256);
    g.setColor(Color.BLUE);
    g.drawPolyline(xAxis, blueLine, 256);
    g.setColor(Color.BLACK);
    g.drawPolyline(xAxis, intensityLine, 256);
  }

  /**
   * Repaint this component to update the histogram,
   * in case the current active image changed.
   */
  public void refresh() {
    this.repaint();
  }
}
