package Pane_2.pane;

import image.*;
import utility.*;
import WaveletData.WaveletData;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 窓（ペイン）の例題プログラム。
 * オブザーバ・デザインパターン（MVC: Model-View-Controller）を用いた典型的（模範的）なプログラム。
 */
public class Execute extends Object {

  // クラスのインスタンスを生成して利用できるようにする
  public void executeProgram(int i, BufferedImage anImage) {
    GridLayout aLayout = new GridLayout(2, 2); // 2行2列のグリッドレイアウトを作成
    JPanel aPanel = new JPanel(aLayout); // グリッドレイアウトを持つパネルを作成

    // 一つ目の画像モデルを作成し、ビューとコントローラを設定
    PaneModel aModel = new PaneModel(anImage);
    PaneView aView = new PaneView(aModel, new PaneController());
    aPanel.add(aView); // パネルにビューを追加

    // 二つ目の画像モデルを作成し、ビューとコントローラを設定
    aModel = new PaneModel("image.jpg");
    aView = new PaneView(aModel, new PaneController());
    aPanel.add(aView); // パネルにビューを追加

    // 三つ目の画像モデルを作成し、ビューとコントローラを設定
    aModel = new PaneModel("image.jpg");
    aView = new PaneView(aModel, new PaneController());
    aPanel.add(aView); // パネルにビューを追加

    // 四つ目の画像モデルを作成し、ビューとコントローラを設定
    aModel = new PaneModel("image.jpg");
    aView = new PaneView(aModel, new PaneController());
    aPanel.add(aView); // パネルにビューを追加

    JFrame aWindow = new JFrame("Pane"); // メインウィンドウを作成し、タイトルを設定
    aWindow.getContentPane().add(aPanel); // ウィンドウにパネルを追加
    aWindow.setMinimumSize(new Dimension(100, 100)); // 最小ウィンドウサイズを設定
    aWindow.setResizable(true); // ウィンドウサイズ変更を許可
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウが閉じられた時にプログラムが終了するように設定
    aWindow.addNotify(); // ウィンドウのネイティブスクリーン情報を取得(タイトルバーの高さを取得するため)
    int titleBarHeight = aWindow.getInsets().top; // タイトルバーの高さを取得
    aWindow.setSize(400, 300 + titleBarHeight); // ウィンドウサイズ設定
    aWindow.setLocation(130+60*i, 150+60*i); // ウィンドウの位置を設定
    aWindow.setVisible(true); // ウィンドウを表示
    aWindow.toFront(); // ウィンドウを最前面に移動
  }
}
