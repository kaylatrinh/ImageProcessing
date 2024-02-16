package view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.ImageUtil;
import model.IAlbumState;
import model.IImage;

/**
 * A panel which shows the current image.
 */
public class ImagePanel extends JPanel {
  private final IAlbumState album;
  private final ViewModel viewModel;
  private final JLabel label;

  /**
   * Construct an image panel with the given album and view model.
   * @param album The album to retrieve images from.
   * @param viewModel The view model to retrieve the current active image from.
   */
  public ImagePanel(IAlbumState album, ViewModel viewModel) {
    super();
    this.album = Objects.requireNonNull(album);
    this.viewModel = Objects.requireNonNull(viewModel);

    this.setBackground(Color.BLACK);
    this.setLayout(new BorderLayout());

    this.label = new JLabel();
    this.label.setHorizontalAlignment(JLabel.CENTER);
    this.label.setVerticalAlignment(JLabel.CENTER);

    this.add(label, BorderLayout.CENTER);

    this.refresh();
  }

  /**
   * Refresh this view to update the drawn image, in case the active image changed.
   */
  public void refresh() {
    if (this.viewModel.isImagePresent()) {
      IImage image = this.album.getImage(this.viewModel.getActiveImage());
      this.label.setIcon(new ImageIcon(ImageUtil.imageToBufferedImage(image)));
    }
  }
}
