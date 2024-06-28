package image;

import image.ImagePanel;
import input.InputPanel;
import javax.swing.*;

// メインのフレームを定義し、UI の配置を行うクラス
public class ImageLoader extends JFrame {

  private InputPanel inputPanel; // 入力パネル
  private ImagePanel imagePanel; // 画像表示パネル

  // コンストラクタ
  public ImageLoader() {
    setTitle("Image Loader"); // タイトルの設定
    setSize(400, 400); // サイズの設定
    setDefaultCloseOperation(EXIT_ON_CLOSE); // 閉じるボタンをクリックしたときの動作を設定
    setLocationRelativeTo(null); // ウィンドウを中央に配置

    inputPanel = new InputPanel(); // 入力パネルの初期化
    imagePanel = new ImagePanel(); // 画像表示パネルの初期化

    // メインパネルに入力パネルと画像表示パネルを配置
    JPanel mainPanel = new JPanel();
    mainPanel.add(inputPanel);
    mainPanel.add(imagePanel);

    add(mainPanel); // メインパネルをフレームに追加
  }
}
