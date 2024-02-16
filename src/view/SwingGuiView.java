package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.util.Objects;
import java.util.Set;

import control.ControllerFeatures;
import model.IAlbumState;

/**
 * A GUI view for the image processing implemented in Swing.
 */
public class SwingGuiView extends JFrame implements ImageGuiView {
  private final TabPanel tabPanel;
  private final ImagePanel imagePanel;
  private final HistogramPanel histogramPanel;
  private final CommandPanel commandPanel;

  /**
   * Construct an image view using the specified album.
   * @param album The (read-only) album to display.
   */
  public SwingGuiView(IAlbumState album) {
    super("Image Processing");
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    Objects.requireNonNull(album);

    ViewModel viewModel = new ViewModel(this::refresh);

    this.tabPanel = new TabPanel(album, viewModel);
    this.add(this.tabPanel, BorderLayout.PAGE_START);

    JPanel rightSide = new JPanel();
    rightSide.setLayout(new GridLayout(2, 0));

    this.histogramPanel = new HistogramPanel(album, viewModel);
    rightSide.add(this.histogramPanel);

    this.commandPanel = new CommandPanel(viewModel);
    rightSide.add(this.commandPanel);

    this.add(rightSide, BorderLayout.EAST);

    this.imagePanel = new ImagePanel(album, viewModel);
    JScrollPane scrollFrame = new JScrollPane(this.imagePanel);
    scrollFrame.setPreferredSize(new Dimension(600, 400));
    this.add(scrollFrame, BorderLayout.CENTER);

    this.pack();
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.tabPanel.refresh();
    this.imagePanel.refresh();
    this.histogramPanel.refresh();
    this.commandPanel.refresh();
  }

  @Override
  public void useFeatures(ControllerFeatures features) {
    this.commandPanel.useFeatures(features);
  }

  @Override
  public void renderCommands(Set<String> commands) {
    // No action necessary, since the commands are always displayed in the GUI
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }
}
