package Wavelet2D;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Wavelet2D.Wavelet2dModel;
import wavelet.WaveletTransform2D;

import java.awt.event.MouseEvent;

public class Wavelet2dView extends JPanel {
        protected Wavelet2dModel model;
        protected Wavelet2dController controller;
        protected WaveletTransform2D waveletTransform2D;
        protected double[][] scalingCoefficients;
        protected double[][] horizontalWaveletCoefficients;
        protected double[][] verticalWaveletCoefficients;
        protected double[][] diagonalWaveletCoefficients;
        protected double[][] recomposedCoefficients;
        protected double[][] interactiveVerticalWaveletCoefficients;
        protected double[][] interactiveHorizontalWaveletCoefficients;
        protected double[][] interactiveDiagonalWaveletCoefficients;

        protected Point scaleFactor;

        protected BufferedImage animage;
        protected BufferedImage horizontalimage;
        protected BufferedImage verticalanimage;
        protected BufferedImage diagonalimage;
        protected BufferedImage recomposedimage;

        protected BufferedImage intaractiverticalImage;
        protected BufferedImage intaractivehorizotalImage;
        protected BufferedImage intaractivediagonalImage;

        protected BufferedImage combindimage;
        protected BufferedImage combindimage2;
        protected BufferedImage combindimage3;

        protected JLabel label1, label2, label3, label4;

        /*
         * コンストラクタ
         */
        public Wavelet2dView() {
                model = new Wavelet2dModel();
                controller = new Wavelet2dController(model, this);
                scalingCoefficients = model.getscalingCoefficients();
                horizontalWaveletCoefficients = model.gethorizontalWaveletCoefficients();
                verticalWaveletCoefficients = model.getverticalWaveletCoefficients();
                diagonalWaveletCoefficients = model.getdiagonalWaveletCoefficients();
                interactiveVerticalWaveletCoefficients = model.getinteractiveVerticalWaveletCoefficients();
                interactiveHorizontalWaveletCoefficients = model.getinteractiveHorizontalWaveletCoefficients();
                interactiveDiagonalWaveletCoefficients = model.getinteractiveDiagonalWaveletCoefficients();
                recomposedCoefficients = new WaveletTransform2D(
                        scalingCoefficients,
                        new double[][][] {
                                interactiveHorizontalWaveletCoefficients,
                                interactiveVerticalWaveletCoefficients,
                                interactiveDiagonalWaveletCoefficients
                        }).getRecomposedCoefficients();

                scaleFactor = new Point(2, 2);

                // 初期表示
                animage = Wavelet2dModel.generateImage2(model.dataSample, scaleFactor, 0);
                horizontalimage = Wavelet2dModel.generateImage2(horizontalWaveletCoefficients, scaleFactor, 0);
                verticalanimage = Wavelet2dModel.generateImage2(verticalWaveletCoefficients, scaleFactor, 0);
                diagonalimage = Wavelet2dModel.generateImage2(diagonalWaveletCoefficients, scaleFactor, 0);
                recomposedimage = Wavelet2dModel.generateImage2(recomposedCoefficients, scaleFactor, 0);

                intaractiverticalImage = Wavelet2dModel.generateImage2(interactiveVerticalWaveletCoefficients,
                                scaleFactor, 0);
                intaractivehorizotalImage = Wavelet2dModel.generateImage2(interactiveHorizontalWaveletCoefficients,
                                scaleFactor, 0);
                intaractivediagonalImage = Wavelet2dModel.generateImage2(interactiveDiagonalWaveletCoefficients,
                                scaleFactor, 0);

                combindimage = Wavelet2dModel.combineImages(animage, horizontalimage, verticalanimage, diagonalimage);
                combindimage2 = Wavelet2dModel.combineImages(combindimage, horizontalimage, verticalanimage,
                                diagonalimage);
                combindimage3 = Wavelet2dModel.combineImages(animage, intaractivehorizotalImage, intaractiverticalImage,
                                intaractivediagonalImage);

                this.controller = new Wavelet2dController(model, this);
                this.addMouseListener(controller); // マウスリスナーとしてcontrollerを登録
                this.addMouseMotionListener(controller); // マウスモーションリスナーとしてcontrollerを登録
        }

        /*
         * 画面の再描画 repaint()が呼ばれると呼び出される
         */
        @Override
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int halfWidth = getWidth() / 2;
                int halfHeight = getHeight() / 2;

                // 画像の生成
                intaractiverticalImage = Wavelet2dModel.generateImage2(interactiveDiagonalWaveletCoefficients,
                                scaleFactor, 0);
                intaractivehorizotalImage = Wavelet2dModel.generateImage2(
                                interactiveHorizontalWaveletCoefficients,
                                scaleFactor, 0);
                intaractivediagonalImage = Wavelet2dModel.generateImage2(interactiveVerticalWaveletCoefficients,
                                scaleFactor, 0);

                // conbindimage3の生成
                combindimage3 = Wavelet2dModel.combineImages(animage, intaractivehorizotalImage,
                                intaractivediagonalImage, intaractiverticalImage);

                // 画面を4つに分割してそれぞれの画像を描画
                g.drawImage(animage, 0, 0, halfWidth, halfHeight, this);
                g.drawImage(combindimage2, halfWidth, 0, halfWidth, halfHeight, this);
                g.drawImage(recomposedimage, 0, halfHeight, halfWidth, halfHeight, this);
                g.drawImage(combindimage3, halfWidth, halfHeight, halfWidth, halfHeight, this);

                // 再描画が行われたことをメッセージで出力
                //System.out.println("画面が再描画されました");
        }

}
