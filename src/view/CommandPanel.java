package view;

import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.ControllerFeatures;
import model.command.FlipCommand;
import model.command.ICommand;
import model.command.KernelOperationCommand;
import model.command.LoadCommand;
import model.command.PixelOperationCommand;
import model.command.SaveCommand;
import model.operation.ColorTransformationOperation;
import model.operation.OffsetOperation;
import model.operation.ValueOperation;

/**
 * A panel in the GUI which allows the user to invoke commands with buttons.
 */
public class CommandPanel extends JPanel {
  private final ViewModel viewModel;
  private final Map<String, JButton> buttons;

  /**
   * Construct a command panel which updates based on the given view model.
   * @param viewModel The view model to use.
   */
  public CommandPanel(ViewModel viewModel) {
    super();

    this.viewModel = Objects.requireNonNull(viewModel);

    this.setLayout(new GridLayout(0, 4));

    this.buttons = new LinkedHashMap<>();

    this.buttons.put("load", new JButton("Load"));
    this.buttons.put("save", new JButton("Save"));

    this.buttons.put("red-component", new JButton("Red"));
    this.buttons.put("green-component", new JButton("Green"));
    this.buttons.put("blue-component", new JButton("Blue"));

    this.buttons.put("luma", new JButton("Luma"));
    this.buttons.put("intensity", new JButton("Intensity"));
    this.buttons.put("value", new JButton("Value"));

    this.buttons.put("brighten", new JButton("Brighten"));
    this.buttons.put("darken", new JButton("Darken"));

    this.buttons.put("vertical-flip", new JButton("V. Flip"));
    this.buttons.put("horizontal-flip", new JButton("H. Flip"));

    this.buttons.put("blur", new JButton("Blur"));
    this.buttons.put("sharpen", new JButton("Sharpen"));

    this.buttons.put("grayscale", new JButton("Grayscale"));
    this.buttons.put("sepia", new JButton("Sepia"));

    for (JButton button : this.buttons.values()) {
      this.add(button);
    }

    this.refresh();
  }

  /**
   * Attach callbacks to command buttons using the given features object.
   * @param features The implementor of the features we wish to use.
   */
  public void useFeatures(ControllerFeatures features) {
    this.buttons.get("load").addActionListener((e) -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(null);

      if (result == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        String name = JOptionPane.showInputDialog("Image name?");

        ICommand command = new LoadCommand(filePath, name);
        features.runCommand(command);
        this.viewModel.setActiveImage(name);
      }
    });

    this.buttons.get("save").addActionListener((e) -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showSaveDialog(null);

      if (result == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        ICommand command = new SaveCommand(filePath, this.viewModel.getActiveImage());
        features.runCommand(command);
      }
    });

    this.buttons.get("red-component").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              1, 0, 0));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("green-component").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              0, 1, 0));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("blue-component").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              0, 0, 1));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("luma").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              0.2126, 0.7152, 0.0722));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("intensity").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              1.0 / 3, 1.0 / 3, 1.0 / 3));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("value").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ValueOperation());
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("brighten").addActionListener((e) -> {
      int amount = Integer.parseInt(JOptionPane.showInputDialog("Amount to brighten?"));
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new OffsetOperation(amount));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("darken").addActionListener((e) -> {
      int amount = Integer.parseInt(JOptionPane.showInputDialog("Amount to darken?"));
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new OffsetOperation(-amount));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("vertical-flip").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new FlipCommand(this.viewModel.getActiveImage(), destName, true);
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("horizontal-flip").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new FlipCommand(this.viewModel.getActiveImage(), destName, false);
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("blur").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new KernelOperationCommand(
              this.viewModel.getActiveImage(), destName, new double[][] {
                  { 1.0 / 16, 1.0 / 8, 1.0 / 16 },
                  { 1.0 / 8, 1.0 / 4, 1.0 / 8 },
                  { 1.0 / 16, 1.0 / 8, 1.0 / 16 }});
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("sharpen").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new KernelOperationCommand(
              this.viewModel.getActiveImage(), destName, new double[][] {
                  { -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8 },
                  { -1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8 },
                  { -1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8 },
                  { -1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8 },
                  { -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8 }});
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("grayscale").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(
              0.2126, 0.7152, 0.0722));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });

    this.buttons.get("sepia").addActionListener((e) -> {
      String destName = JOptionPane.showInputDialog("Destination image name?");
      ICommand command = new PixelOperationCommand(this.viewModel.getActiveImage(), destName,
          new ColorTransformationOperation(new double[][] {
              { 0.393, 0.769, 0.189 },
              { 0.349, 0.686, 0.168 },
              { 0.272, 0.534, 0.131 }
          }));
      features.runCommand(command);
      this.viewModel.setActiveImage(destName);
    });
  }

  /**
   * Refresh this view, updating it based on the status of the view model.
   */
  public void refresh() {
    for (Map.Entry<String, JButton> entry : this.buttons.entrySet()) {
      if (entry.getKey().equals("load")) {
        entry.getValue().setEnabled(true);
      } else {
        entry.getValue().setEnabled(this.viewModel.isImagePresent());
      }
    }
  }
}
