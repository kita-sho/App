package Pane_2.pane;

import condition.Condition;
import condition.ValueHolder;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * 窓（ペイン）のコントローラ。
 */
public class PaneController extends mvc.Controller {

  /**
   * 上位コンストラクタを継承するただけのコンストラクタ。
   */
  public PaneController() {
    super();
  }

  /**
   * 自分のビューを応答する。
   * @return このコントローラのビュー
   */
  public PaneView getView() {
    return (PaneView) (this.view); // ビューをキャストして返す
  }

  /**
   * マウスクリックした位置をピクチャ座標にしてモデルに通知する。
   */
  public void mouseClicked(MouseEvent aMouseEvent) {
    try {
      ValueHolder<Point> aPoint = new ValueHolder<Point>(
        aMouseEvent.getPoint()
      ); // マウスクリックした位置を取得
      PaneView aView = this.getView(); // ビューを取得
      aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get())); // ビュー座標をピクチャ座標に変換
      new Condition(() -> aPoint.get() == null)
        .ifTrue(() -> {
          throw new RuntimeException();
        }); // ピクチャ座標がnullの場合に例外を投げる
      aView.getModel().mouseClicked(aPoint.get(), aMouseEvent); // モデルにマウスクリックイベントを通知
    } catch (RuntimeException anException) {
      return;
    }
    return;
  }

  /**
   * マウスドラッグした位置をピクチャ座標にしてモデルに通知する。
   */
  public void mouseDragged(MouseEvent aMouseEvent) {
    try {
      ValueHolder<Point> aPoint = new ValueHolder<Point>(
        aMouseEvent.getPoint()
      ); // マウスドラッグした位置を取得
      PaneView aView = this.getView(); // ビューを取得
      aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get())); // ビュー座標をピクチャ座標に変換
      new Condition(() -> aPoint.get() == null)
        .ifTrue(() -> {
          throw new RuntimeException();
        }); // ピクチャ座標がnullの場合に例外を投げる
      aView.getModel().mouseDragged(aPoint.get(), aMouseEvent); // モデルにマウスドラッグイベントを通知　
    } catch (RuntimeException anException) {
      return;
    }
    return;
  }
}
