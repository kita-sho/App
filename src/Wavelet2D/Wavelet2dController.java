package Wavelet2D;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import Pane_2.pane.PaneController;

import Wavelet2D.Wavelet2dModel;
import Wavelet2D.Wavelet2dView;
import mvc.Controller;
import wavelet.WaveletTransform2D;

public class Wavelet2dController extends Controller implements MouseListener, MouseMotionListener {
    protected Wavelet2dModel model;
    protected Wavelet2dView view;
    protected WaveletTransform2D transform2d;
    protected double[][] interactiveHorizontalWaveletCoefficients;
    protected double[][] interactiveVerticalWaveletCoefficients;
    protected double[][] interactiveDiagonalWaveletCoefficients;
    protected double[][] coefficientsForTransform;
    protected double[][] scalingCoefficients;
    protected double[][] recomposedCoefficients;

    /*
     * コンストラクタ
     */
    public Wavelet2dController(Wavelet2dModel model, Wavelet2dView view) {
        this.model = model;
        this.view = view;
        this.scalingCoefficients = model.getscalingCoefficients();
        this.interactiveHorizontalWaveletCoefficients = model.getinteractiveHorizontalWaveletCoefficients();
        this.interactiveVerticalWaveletCoefficients = model.getinteractiveVerticalWaveletCoefficients();
        this.interactiveDiagonalWaveletCoefficients = model.getinteractiveDiagonalWaveletCoefficients();
    }

    /*
     * マウスがクリックされた時配列の値を変更し、画像を再生成する
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // 配列の変更
        model.changeArrayfromPoint(e.getX(), e.getY());

        // recomposedCoefficientsの更新
        recomposedCoefficients = new WaveletTransform2D(
                scalingCoefficients,
                new double[][][] {
                        interactiveHorizontalWaveletCoefficients,
                        interactiveVerticalWaveletCoefficients,
                        interactiveDiagonalWaveletCoefficients
                }).getRecomposedCoefficients();

        // 画像の再生成
        view.intaractivehorizotalImage = Wavelet2dModel.generateImage2(interactiveHorizontalWaveletCoefficients,
                view.scaleFactor, 0);
        view.intaractiverticalImage = Wavelet2dModel.generateImage2(interactiveDiagonalWaveletCoefficients,
                view.scaleFactor, 0);
        view.intaractivediagonalImage = Wavelet2dModel.generateImage2(interactiveVerticalWaveletCoefficients,
                view.scaleFactor, 0);
        view.recomposedimage = Wavelet2dModel.generateImage2(recomposedCoefficients, view.scaleFactor, 0);
        view.repaint();

    }

    /*
     * マウスがドラッグされた時配列の値を変更し、画像を再生成する
     */
    // MouseMotionListenerインターフェースのメソッドを実装
    @Override
    public void mouseDragged(MouseEvent e) {
        // 配列の更新
        model.changeArrayfromPoint(e.getX(), e.getY());

        // recomposedCoefficientsの更新
        recomposedCoefficients = new WaveletTransform2D(
                scalingCoefficients,
                new double[][][] {
                        interactiveHorizontalWaveletCoefficients,
                        interactiveVerticalWaveletCoefficients,
                        interactiveDiagonalWaveletCoefficients
                }).getRecomposedCoefficients();

        // 画像の再生成
        view.intaractivehorizotalImage = Wavelet2dModel.generateImage2(interactiveHorizontalWaveletCoefficients,
                view.scaleFactor, 0);
        view.intaractiverticalImage = Wavelet2dModel.generateImage2(interactiveDiagonalWaveletCoefficients,
                view.scaleFactor, 0);
        view.intaractivediagonalImage = Wavelet2dModel.generateImage2(interactiveVerticalWaveletCoefficients,
                view.scaleFactor, 0);
        view.recomposedimage = Wavelet2dModel.generateImage2(recomposedCoefficients, view.scaleFactor, 0);
        view.repaint();
    }

    /*
     * マウスのボタンが押された時、ControllerのmousePressedメソッドが呼ばれる
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
    }

    /*
     * マウスのボタンが離された時、ControllerのmouseReleasedメソッドが呼ばれる
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }

}
