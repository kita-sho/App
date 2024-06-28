package error;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorWindow {

  public static void showError(String message) {
    JFrame errorFrame = new JFrame("エラー");
    JPanel panel = new JPanel();
    JLabel label = new JLabel(message);

    panel.add(label);
    errorFrame.add(panel);

    errorFrame.setSize(300, 100);
    errorFrame.setLocationRelativeTo(null);
    errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    errorFrame.setVisible(true);
  }
}
