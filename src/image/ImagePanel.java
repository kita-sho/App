package image;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

// 画像を表示するラベルを含むパネルを定義するクラス
public class ImagePanel extends JPanel {

  private JLabel imageLabel; // 画像表示ラベル
  private ImageIcon selectedImage; // 選択された画像

  // コンストラクタ
  public ImagePanel() {
    setLayout(new BorderLayout()); // ボーダーレイアウトを設定

    JButton selectButton = new JButton("画像選択"); // 画像選択ボタン
    selectButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          JFileChooser fileChooser = new JFileChooser(); // ファイル選択ダイアログ
          fileChooser.setDialogTitle("Select Image File"); // ダイアログのタイトル設定
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files",
            "jpg",
            "jpeg",
            "png",
            "gif"
          );
          fileChooser.setFileFilter(filter); // ファイルフィルターの設定

          int result = fileChooser.showOpenDialog(ImagePanel.this); // ダイアログを表示してファイルを選択
          if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();
            selectedImage = new ImageIcon(imagePath); // 選択された画像を取得
            imageLabel.setIcon(selectedImage); // 画像を表示
            saveImage(selectedImage, "image.jpg"); // 画像を保存
          }
        }
      }
    );

    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setVerticalAlignment(JLabel.CENTER);

    add(selectButton, BorderLayout.NORTH); // 選択ボタンを北側に追加
    add(imageLabel, BorderLayout.CENTER); // 画像ラベルを中央に追加
  }

  // 選択された画像を保存するメソッド
  private void saveImage(ImageIcon imageIcon, String fileName) {
    // ImageIconからBufferedImageを作成
    Image image = imageIcon.getImage();
    BufferedImage bufferedImage = new BufferedImage(
      image.getWidth(null),
      image.getHeight(null),
      BufferedImage.TYPE_INT_RGB
    );
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();

    // 画像をファイルに保存
    try {
      File outputFile = new File(fileName);
      ImageIO.write(bufferedImage, "jpg", outputFile);
      System.out.println("Image saved as " + fileName);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
