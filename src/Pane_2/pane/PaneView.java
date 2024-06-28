package Pane_2.pane;

import condition.Condition;
import condition.ValueHolder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import utility.ImageUtility;

/**
 * 窓（ペイン）のビュー。
 */
@SuppressWarnings("serial")
public class PaneView extends mvc.View {

  /**
   * 内容物の表示位置(原点)を保持するフィールド。
   */
  private Point2D.Double originPoint;

  /**
   * 内容物の拡大縮小率を保持するフィールド。
   */
  private Point2D.Double scaleFactor;

  /**
   * 指定されたモデルとコントローラと自分（ビュー）とでMVCを構築するコンストラクタ。
   * @param aModel このビューのモデル
   * @param aController このビューのコントローラ
   */
  public PaneView(PaneModel aModel, PaneController aController) {
    super(aModel, aController); // 親クラスのコンストラクタを呼び出す
    this.intialize(); // 初期化メソッドを呼び出す
  }

  /**
   * 初期化を行う。
   */
  public void intialize() {
    originPoint = new Point2D.Double(0.0d, 0.0d); // 原点を(0.0, 0.0)に設定
    scaleFactor = new Point2D.Double(1.0d, 1.0d); // 拡大縮小率を(1.0, 1.0)に設定
  }

  /**
   * 自分のモデルを応答する。
   * @return このビューのモデル
   */
  public PaneModel getModel() {
    return (PaneModel) (this.model); // モデルをキャストして返す
  }

  /**
   * ビュー座標をモデル座標に変換して応答する。
   * @param aViewPoint ビュー座標
   * @return モデル座標
   */
  public Point convertViewPointToModelPoint(Point aViewPoint) {
    Point scrollAmount = this.scrollAmount(); // スクロール量を取得
    Point aPoint = new Point(
      aViewPoint.x + scrollAmount.x,
      aViewPoint.y + scrollAmount.y
    ); // ビュー座標にスクロール量を加算してモデル座標を取得
    return aPoint;
  }

  /**
   * ビュー座標をピクチャ座標に変換して応答する。
   * @param aViewPoint ビュー座標
   * @return ピクチャ座標
   */
  public Point convertViewPointToPicturePoint(Point aViewPoint) {
    Point aModelPoint = this.convertViewPointToModelPoint(aViewPoint); // ビュー座標をモデル座標に変換
    Point aPoint = this.convertModelPointToPicturePoint(aModelPoint); // モデル座標をピクチャ座標に変換
    return aPoint;
  }

  /**
   * モデル座標をピクチャ座標に変換して応答する。
   * @param aModelPoint モデル座標
   * @return ピクチャ座標
   */
  public Point convertModelPointToPicturePoint(Point aModelPoint) {
    Point aPoint = null;
    try {
      PaneModel aModel = this.getModel(); // モデルを取得
      BufferedImage anImage = aModel.picture(); // ピクチャを取得
      new Condition(() -> anImage == null)
        .ifTrue(() -> {
          throw new RuntimeException();
        }); // ピクチャが存在しない場合に例外を投げる

      // モデル座標をピクチャ座標に変換
      Double xValue =
        ((double) (aModelPoint.x) - originPoint.x) / scaleFactor.x;
      Double yValue =
        ((double) (aModelPoint.y) - originPoint.y) / scaleFactor.y;
      Integer x = Integer.valueOf(xValue.intValue());
      Integer y = Integer.valueOf(yValue.intValue());

      // ピクチャ座標が範囲外の場合に例外を投げる
      new Condition(() -> x < 0)
        .ifTrue(() -> {
          throw new RuntimeException();
        });
      new Condition(() -> y < 0)
        .ifTrue(() -> {
          throw new RuntimeException();
        });
      Integer width = anImage.getWidth();
      Integer height = anImage.getHeight();
      new Condition(() -> x > width)
        .ifTrue(() -> {
          throw new RuntimeException();
        });
      new Condition(() -> y > height)
        .ifTrue(() -> {
          throw new RuntimeException();
        });

      aPoint = new Point(x, y); // ピクチャ座標を返す
    } catch (RuntimeException anException) {
      return null;
    }
    return aPoint;
  }

  /**
   * 描画を行う。
   */
  public void paintComponent(Graphics aGraphics) {
    try {
      // コンポーネントの幅と高さを取得
      Integer width = this.getWidth();
      Integer height = this.getHeight();
      // 背景を白で塗りつぶす
      aGraphics.setColor(Color.white);
      aGraphics.fillRect(0, 0, width, height);

      new Condition(() -> model == null)
        .ifTrue(() -> {
          throw new RuntimeException();
        }); // モデルが存在しない場合に例外を投げる
      ValueHolder<BufferedImage> picture = new ValueHolder<BufferedImage>(
        model.picture()
      ); // ピクチャを取得
      new Condition(() -> picture.get() == null)
        .ifTrue(() -> {
          throw new RuntimeException();
        }); // ピクチャが存在しない場合に例外を投げる

      // ピクチャの幅と高さを取得
      Integer w = picture.get().getWidth();
      Integer h = picture.get().getHeight();
      // ピクチャの拡大縮小率を計算
      ValueHolder<Double> x = new ValueHolder<Double>(
        Double.valueOf(width) / Double.valueOf(w)
      );
      ValueHolder<Double> y = new ValueHolder<Double>(
        Double.valueOf(height) / Double.valueOf(h)
      );
      // 縦横比を維持するための調整
      new Condition(() -> x.get() > y.get())
        .ifThenElse(
          () -> {
            x.set(y.get());
          },
          () -> {
            y.set(x.get());
          }
        );
      scaleFactor = new Point2D.Double(x.get(), y.get());

      // ピクチャの新しい幅と高さを計算
      w = Integer.valueOf((int) (Double.valueOf(w) * x.get()));
      h = Integer.valueOf((int) (Double.valueOf(h) * y.get()));
      // ピクチャを調整
      picture.set(ImageUtility.adjustImage(picture.get(), w, h));
      // ピクチャの表示位置を計算
      x.set(Double.valueOf(width - w) / 2.0d);
      y.set(Double.valueOf(height - h) / 2.0d);
      originPoint = new Point2D.Double(x.get(), y.get());

      // ピクチャを描画
      aGraphics.drawImage(
        picture.get(),
        x.get().intValue(),
        y.get().intValue(),
        null
      );
    } catch (RuntimeException anException) {
      return;
    }
    return;
  }

  /**
   * スクロールを抑制する。スコープMVCでは必要ないため、スクロール量を常にゼロに保つ。
   * @return スクロール量を常にゼロ座標を応答する。
   */
  public Point scrollAmount() {
    return (new Point(0, 0)); // スクロール量を常に(0, 0)にする
  }

  /**
   * 相対スクロールをしないようにする。スコープMVCでは必要ないため、何もしないことにする。
   * @param aPoint 無視
   */
  public void scrollBy(Point aPoint) {
    return;
  }

  /**
   * 絶対スクロールをしないようにする。スコープMVCでは必要ないため、何もしないことにする。
   * @param aPoint 無視
   */
  public void scrollTo(Point aPoint) {
    return;
  }
}
