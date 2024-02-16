package view;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.IAlbumState;

/**
 * A panel which allows the user to choose the active image.
 */
public class TabPanel extends JPanel {
  private final IAlbumState album;
  private final ViewModel viewModel;
  private final JComboBox<String> combobox;
  private final ItemListener itemListener;

  /**
   * Construct a tab panel which uses the specified album and view model.
   * @param album The album to pull the list of images from.
   * @param viewModel The view model to get and set the active image in.
   */
  public TabPanel(IAlbumState album, ViewModel viewModel) {
    super();

    this.album = Objects.requireNonNull(album);
    this.viewModel = Objects.requireNonNull(viewModel);

    this.setLayout(new FlowLayout());

    JLabel label = new JLabel("Image Processing");
    label.setHorizontalAlignment(SwingConstants.LEFT);
    this.add(label);

    this.itemListener = (e) -> {

      if (e.getStateChange() == ItemEvent.SELECTED) {
        this.viewModel.setActiveImage((String) e.getItem());
      }
    };

    this.combobox = new JComboBox<>(this.album.getImageNames().toArray(String[]::new));

    this.add(combobox);

    this.refresh();
  }

  /**
   * Refresh this panel in case the available images and/or the active image changed.
   */
  public void refresh() {
    // Add any new items not yet in the combobox
    itemLoop: for (String imageName : this.album.getImageNames()) {
      for (int i = 0; i < this.combobox.getItemCount(); ++i) {
        if (imageName.equals(this.combobox.getItemAt(i))) {
          continue itemLoop;
        }
      }

      this.combobox.addItem(imageName);
    }

    // Temporarily remove the item listener so that we don't trigger a refresh loop
    this.combobox.removeItemListener(this.itemListener);
    if (this.viewModel.isImagePresent()) {
      this.combobox.setSelectedItem(this.viewModel.getActiveImage());
    }
    this.combobox.addItemListener(this.itemListener);
  }
}
