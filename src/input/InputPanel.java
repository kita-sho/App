package input;

import Pane_2.pane.*;
import error.ErrorWindow;
import image.*;
import utility.*;
import WaveletData.WaveletData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class InputPanel extends JPanel {

  private JTextField coefficientField;
  private ImagePanel selectedImage;
  private Execute executeProgram; // インスタンスはクラスフィールドとして宣言されている

  public InputPanel() {
    setLayout(new GridLayout(3, 3));

    coefficientField = new JTextField(10);
    JButton coefficientButton = new JButton("確定");
    coefficientButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String coefficient = coefficientField.getText();
          // 何も入力されていなかった時
          if (coefficient == null || coefficient.trim().isEmpty()) {
            ErrorWindow.showError("係数が入力されていません。");
          } else {
            JOptionPane.showMessageDialog(
              InputPanel.this,
              "係数: " + coefficient
            );
          }
        }
      }
    );

    executeProgram = new Execute(); // ここで既存のインスタンスを初期化する

    JButton newWindowButton = new JButton("実行");
    newWindowButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String coefficient = coefficientField.getText();
          // 何も入力されていなかった時
          if (coefficient == null || coefficient.trim().isEmpty()) {
            ErrorWindow.showError("係数が入力されていません。");
          } else {

            // テスト的に配布データも用いた
            BufferedImage anImage = ImageUtility.readImage("image.jpg");
            BufferedImage earthImage = WaveletData.imageEarth();
            executeProgram.executeProgram(0, earthImage); // 既存のインスタンスのメソッドを呼び出す
              
            BufferedImage grayscaleImage = ImageUtility.grayscaleImage(anImage);
            BufferedImage imageSmalltalkBallon = WaveletData.imageSmalltalkBalloon();
            executeProgram.executeProgram(1, imageSmalltalkBallon); // 既存のインスタンスのメソッドを呼び出す

            BufferedImage redImage = ImageUtility.redImage(anImage);
            double[][][] lrgb = WaveletData.lrgbMatrixes(imageSmalltalkBallon);
            BufferedImage generateImage = WaveletData.generateImage(lrgb, 50);
            executeProgram.executeProgram(2, generateImage); // 既存のインスタンスのメソッドを呼び出す

            BufferedImage greenImage = ImageUtility.greenImage(anImage);
            executeProgram.executeProgram(3, greenImage); // 既存のインスタンスのメソッドを呼び出す

            BufferedImage blueImage = ImageUtility.blueImage(anImage);
            executeProgram.executeProgram(4, blueImage); // 既存のインスタンスのメソッドを呼び出す
          }
        }
      }
    );

    add(new JLabel("係数:"));
    add(coefficientField);
    add(coefficientButton);
    add(newWindowButton);
  }
}
