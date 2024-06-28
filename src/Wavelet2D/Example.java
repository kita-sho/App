package Wavelet2D;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Wavelet2D.Wavelet2dController;
import Wavelet2D.Wavelet2dModel;

public class Example {
    public void run(){
          // SwingのUIスレッドで実行
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
      
    

    private static void createAndShowGUI() {
        // Wavelet2dViewとWavelet2dControllerのインスタンスを作成
        Wavelet2dModel model = new Wavelet2dModel();
        Wavelet2dView view = new Wavelet2dView();
        Wavelet2dController controller = new Wavelet2dController(model, view);

        // controllerをviewのMouseMotionListenerとして登録
        view.addMouseMotionListener(controller);
        Wavelet2dView aPanel = new Wavelet2dView(); // グリッドレイアウトを持つパネルを作成
        JFrame aWindow = new JFrame("Pane"); // メインウィンドウを作成し、タイトルを設定
        aWindow.getContentPane().add(aPanel); // ウィンドウにパネルを追加
        aWindow.setMinimumSize(new Dimension(100, 100)); // 最小ウィンドウサイズを設定
        aWindow.setResizable(true); // ウィンドウサイズ変更を許可
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウが閉じられた時にプログラムが終了するように設定
        aWindow.addNotify(); // ウィンドウのネイティブスクリーン情報を取得(タイトルバーの高さを取得するため)
        int titleBarHeight = aWindow.getInsets().top; // タイトルバーの高さを取得
        aWindow.setSize(600, 600 + titleBarHeight); // ウィンドウサイズ設定
        aWindow.setLocation(300, 300); // ウィンドウの位置を設定
        aWindow.setVisible(true); // ウィンドウを表示
        aWindow.toFront(); // ウィンドウを最前面に移動

    }
}
