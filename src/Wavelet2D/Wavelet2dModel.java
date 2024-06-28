package Wavelet2D;

import utility.ImageUtility;
import wavelet.WaveletTransform1D;
import wavelet.WaveletTransform2D;
import utility.ColorUtility;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Image;

public class Wavelet2dModel {

	protected double[][] dataSample = Wavelet2dModel.dataSampleCoefficient2d();
	protected WaveletTransform2D transformer = new WaveletTransform2D(dataSample);
	protected double[][] sourceCoefficients = transformer.getSourceCofficients();
	protected double[][] scalingCoefficients = transformer.getScalingCofficients();
	protected double[][] horizontalWaveletCoefficients = transformer.getHorizonalWaveletCoefficient(); // 右上
	protected double[][] verticalWaveletCoefficients = transformer.getVerticalWaveletCoefficient(); // 左下
	protected double[][] diagonalWaveletCoefficients = transformer.getDiagonalWaveletCoefficient(); // 右下
	protected double[][] recomposedCoefficients = transformer.getRecomposedCoefficients();
	protected double[][][] coefficientsForTransform = new double[][][] { horizontalWaveletCoefficients,
			verticalWaveletCoefficients, diagonalWaveletCoefficients };

	// 以下の配列を追加
	protected double[][] interactiveHorizontalWaveletCoefficients;
	protected double[][] interactiveVerticalWaveletCoefficients;
	protected double[][] interactiveDiagonalWaveletCoefficients;

	/*
	 * コンストラクタ
	 */
	public Wavelet2dModel() {
		interactiveHorizontalWaveletCoefficients = new double[horizontalWaveletCoefficients.length][horizontalWaveletCoefficients.length];
		interactiveVerticalWaveletCoefficients = new double[verticalWaveletCoefficients.length][verticalWaveletCoefficients.length];
		interactiveDiagonalWaveletCoefficients = new double[diagonalWaveletCoefficients.length][diagonalWaveletCoefficients.length];
	}

	/*
	 * マウスが押された座標を画像の元配列に対応させ、周囲の配列を変更する
	 */
	public void changeArrayfromPoint(int x, int y) {
		int relativeX_2 = x % 150;
		int relativeY_2 = y % 150;

		int arrayX = relativeX_2 / 5;
		int arrayY = relativeY_2 / 5;

		int range = 1;

		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				int surroundX = Math.min(Math.max(arrayX + i, 0), horizontalWaveletCoefficients.length - 1);
				int surroundY = Math.min(Math.max(arrayY + j, 0), horizontalWaveletCoefficients[0].length - 1);

				interactiveHorizontalWaveletCoefficients[surroundX][surroundY] = horizontalWaveletCoefficients[surroundX][surroundY];
				interactiveDiagonalWaveletCoefficients[surroundX][surroundY] = diagonalWaveletCoefficients[surroundX][surroundY];
				interactiveVerticalWaveletCoefficients[surroundX][surroundY] = verticalWaveletCoefficients[surroundX][surroundY];
			}
		}

	}

	/*
	 * interactiveHorizontalWaveletCoefficientsをからの配列にする
	 */
	public double[][] clearInteractiveHorizontalWaveletCoefficients() {
		for (int i = 0; i < horizontalWaveletCoefficients.length; i++) {
			for (int j = 0; j < horizontalWaveletCoefficients[i].length; j++) {
				interactiveHorizontalWaveletCoefficients[i][j] = 0;
			}
		}
		return interactiveHorizontalWaveletCoefficients;
	}

	/*
	 * interactiveVerticalWaveletCoefficientsをからの配列にする
	 * 
	 */
	public double[][] clearInteractiveVerticalWaveletCoefficients() {
		for (int i = 0; i < verticalWaveletCoefficients.length; i++) {
			for (int j = 0; j < verticalWaveletCoefficients[i].length; j++) {
				interactiveVerticalWaveletCoefficients[i][j] = 0;
			}
		}
		return interactiveVerticalWaveletCoefficients;
	}

	/*
	 * interactiveDiagonalWaveletCoefficientsを空の配列にする
	 */
	public double[][] clearInteractiveDiagonalWaveletCoefficients() {
		for (int i = 0; i < diagonalWaveletCoefficients.length; i++) {
			for (int j = 0; j < diagonalWaveletCoefficients[i].length; j++) {
				interactiveDiagonalWaveletCoefficients[i][j] = 0;
			}
		}
		return interactiveDiagonalWaveletCoefficients;
	}

	/*
	 * recomposedCoefficientsを空の配列にする
	 */
	public double[][] clearRecomposedCoefficients() {
		for (int i = 0; i < recomposedCoefficients.length; i++) {
			for (int j = 0; j < recomposedCoefficients[i].length; j++) {
				recomposedCoefficients[i][j] = 0;
			}
		}
		return recomposedCoefficients;
	}

	/**
	 * 離散ウェーブレット1次元変換のための元データ。
	 */
	public static double[] dataSampleCoefficients() {
		double[] anArray = new double[64];
		Arrays.fill(anArray, 0.0d);
		for (int i = 0; i < 16; i++) {
			anArray[i] = Math.pow((double) (i + 1), 2.0d) / 256.0d;
		}
		for (int i = 16; i < 32; i++) {
			anArray[i] = 0.2d;
		}
		for (int i = 32; i < 48; i++) {
			anArray[i] = Math.pow((double) (48 - (i + 1)), 2.0d) / 256.0d - 0.5d;
		}
		return anArray;
	}

	/**
	 * 離散ウェーブレット2次元変換のための元データ。
	 */
	public static double[][] dataSampleCoefficient2d() {
		int size = 64;
		double[][] aMatrix = new double[size][size];
		for (int index = 0; index < aMatrix.length; index++) {
			Arrays.fill(aMatrix[index], 0.2d);
		}
		for (int index = 5; index < size - 5; index++) {
			aMatrix[5][index] = 1.0d;
			aMatrix[size - 6][index] = 1.0d;
			aMatrix[index][5] = 1.0d;
			aMatrix[index][size - 6] = 1.0d;
			aMatrix[index][index] = 1.0d;
			aMatrix[index][size - index - 1] = 1.0d;
		}
		return aMatrix;
	}

	/**
	 * 離散ウェーブレット2次元変換のための元データ(Earth)。
	 */
	/*
	 * public static double[][][] dataEarth() {
	 * BufferedImage anImage = Wavelet2dModel.imageEarth();
	 * return Wavelet2dModel.lrgbMatrixes(anImage);
	 * }
	 */

	/**
	 * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
	 */
	/*
	 * public static double[][][] dataSmalltalkBalloon() {
	 * BufferedImage anImage = Wavelet2dModel.imageSmalltalkBalloon();
	 * return Wavelet2dModel.lrgbMatrixes(anImage);
	 * }
	 */

	/**
	 * 2次元配列の中を指定された値で初期化する。
	 */
	public static void fill(double[][] aMatrix, double aValue) {
		for (int index = 0; index < aMatrix.length; index++) {
			double[] anArray = aMatrix[index];
			Arrays.fill(anArray, aValue);
		}
		return;
	}

	/**
	 * 離散ウェーブレット2次元変換のためのデータ値(valueMatrix)を画像に変換して応答する。
	 */
	public static BufferedImage generateImage(double[][][] valueMatrixArray, double maxValue) {
		double[][] valueMatrix = valueMatrixArray[0];
		int width = valueMatrix.length;
		int height = valueMatrix[0].length;
		BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D aGraphics = anImage.createGraphics();

		if (valueMatrixArray[1] == null || valueMatrixArray[2] == null || valueMatrixArray[3] == null) {
			// Grayscale
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					double aValue = Math.abs(valueMatrix[x][y]);
					int brightness = (int) Math.round((aValue / maxValue) * 255.0d);
					Color aColor = new Color(brightness, brightness, brightness);
					aGraphics.setColor(aColor);
					aGraphics.fillRect(x, y, 1, 1);
				}
			}
		} else {
			// Color
			int[][] redMatrix = new int[width][height];
			int[][] greenMatrix = new int[width][height];
			int[][] blueMatrix = new int[width][height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					double redValue = Math.abs(valueMatrixArray[1][x][y]);
					int red = (int) Math.round((redValue / maxValue) * 255.0d);
					double greenValue = Math.abs(valueMatrixArray[2][x][y]);
					int green = (int) Math.round((greenValue / maxValue) * 255.0d);
					double blueValue = Math.abs(valueMatrixArray[3][x][y]);
					int blue = (int) Math.round((blueValue / maxValue) * 255.0d);
					Color aColor = new Color(red, green, blue);
					aGraphics.setColor(aColor);
					aGraphics.fillRect(x, y, 1, 1);
				}
			}
		}

		return anImage;
	}

	/**
	 * 離散ウェーブレット2次元変換のためのデータ値(valueMatrix)を画像に変換して応答する。
	 */
	public static BufferedImage generateImage2(double[][] valueMatrix, Point scaleFactor, int rgbFlag) {
		int width = valueMatrix.length;
		int height = valueMatrix[0].length;
		int w = width * scaleFactor.x;
		int h = height * scaleFactor.y;
		BufferedImage anImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D aGraphics = anImage.createGraphics();

		double maxValue = Double.MIN_VALUE;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double aValue = Math.abs(valueMatrix[x][y]);
				maxValue = Math.max(aValue, maxValue);
			}
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double aValue = Math.abs(valueMatrix[x][y]);
				int luminance = (int) Math.round((aValue / maxValue) * 255.0d);
				Color aColor = new Color(luminance, luminance, luminance);
				if (rgbFlag == Constants.Red) {
					aColor = new Color(luminance, 0, 0);
				}
				if (rgbFlag == Constants.Green) {
					aColor = new Color(0, luminance, 0);
				}
				if (rgbFlag == Constants.Blue) {
					aColor = new Color(0, 0, luminance);
				}
				aGraphics.setColor(aColor);
				aGraphics.fillRect(x * scaleFactor.x, y * scaleFactor.y, scaleFactor.x, scaleFactor.y);
			}
		}

		return anImage;
	}

	/**
	 * 離散ウェーブレット2次元変換のための元データ(Earth)。
	 */
	public static BufferedImage imageEarth() {
		String aString = "Wavelet2/SampleImages/imageEarth512x256.jpg";
		BufferedImage anImage = ImageUtility.readImage(aString);
		return anImage;
	}

	/**
	 * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
	 */
	public static BufferedImage imageSmalltalkBalloon() {
		String aString = "WaveletData/SampleImages/imageSmalltalkBalloon256x256.jpg";
		BufferedImage anImage = ImageUtility.readImage(aString);
		return anImage;
	}

	/**
	 * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
	 */
	public static double[][][] lrgbMatrixes(BufferedImage anImage) {
		int width = anImage.getWidth();
		int height = anImage.getHeight();
		double[][] luminanceMatrix = new double[width][height];
		double[][] redMatrix = new double[width][height];
		double[][] greenMatrix = new double[width][height];
		double[][] blueMatrix = new double[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int aRGB = anImage.getRGB(x, y);
				luminanceMatrix[x][y] = ColorUtility.luminanceFromRGB(aRGB);
				double[] rgb = ColorUtility.convertINTtoRGB(aRGB);
				redMatrix[x][y] = rgb[0];
				greenMatrix[x][y] = rgb[1];
				blueMatrix[x][y] = rgb[2];
			}
		}
		return new double[][][] { luminanceMatrix, redMatrix, greenMatrix, blueMatrix };
	}

	/*
	 * 画像を組み合わせ、新しい画像を生成する
	 */
	public static BufferedImage combineImages(BufferedImage image1, BufferedImage image2, BufferedImage image3,
			BufferedImage image4) {
		// Resize images to match dimensions of the largest image
		int maxWidth = Math.max(Math.max(image1.getWidth(), image2.getWidth()),
				Math.max(image3.getWidth(), image4.getWidth()));
		int maxHeight = Math.max(Math.max(image1.getHeight(), image2.getHeight()),
				Math.max(image3.getHeight(), image4.getHeight()));

		image1 = resizeImage(image1, maxWidth, maxHeight);
		image2 = resizeImage(image2, maxWidth, maxHeight);
		image3 = resizeImage(image3, maxWidth, maxHeight);
		image4 = resizeImage(image4, maxWidth, maxHeight);

		// Combine resized images
		int combinedWidth = image1.getWidth() + image2.getWidth();
		int combinedHeight = image1.getHeight() + image3.getHeight();
		BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = combinedImage.createGraphics();

		graphics.drawImage(image1, 0, 0, null);
		graphics.drawImage(image2, image1.getWidth(), 0, null);
		graphics.drawImage(image3, 0, image1.getHeight(), null);
		graphics.drawImage(image4, image1.getWidth(), image1.getHeight(), null);

		graphics.dispose(); // Release resources

		return combinedImage;
	}

	/*
	 * 画像をリサイズする
	 */
	private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
		Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = resizedImage.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resizedImage;
	}

	public double[][] getscalingCoefficients() {
		return scalingCoefficients;
	}

	/*
	 * horizontalWaveletCoefficientsを返す
	 */
	public double[][] gethorizontalWaveletCoefficients() {
		return horizontalWaveletCoefficients;

	}

	/*
	 * verticalWaveletCoefficientsを返す
	 */
	public double[][] getverticalWaveletCoefficients() {
		return verticalWaveletCoefficients;

	}

	/*
	 * diagonalWaveletCoefficientsを返す
	 */
	public double[][] getdiagonalWaveletCoefficients() {
		return diagonalWaveletCoefficients;
	}

	/*
	 * recomposedCoefficientsを返す
	 */
	public double[][] getrecomposedCoefficients() {
		return recomposedCoefficients;

	}

	/*
	 * interactiveHorizontalWaveletCoefficientsを返す
	 */
	public double[][] getinteractiveVerticalWaveletCoefficients() {
		return interactiveVerticalWaveletCoefficients;
	}

	/*
	 * interactiveHorizontalWaveletCoefficientsを返す
	 */
	public double[][] getinteractiveHorizontalWaveletCoefficients() {
		return interactiveHorizontalWaveletCoefficients;
	}

	/*
	 * interactiveDiagonalWaveletCoefficientsを返す
	 */
	public double[][] getinteractiveDiagonalWaveletCoefficients() {
		return interactiveDiagonalWaveletCoefficients;
	}

	public double[][][] getcoefficientsForTransform() {
		return coefficientsForTransform;
	}

	/*
	 * interactiveHorizontalWaveletCoefficientsをセットする
	 */
	public void setinteractiveHorizontalWaveletCoefficients(double[][] interactiveHorizontalWaveletCoefficients) {
		this.interactiveHorizontalWaveletCoefficients = interactiveHorizontalWaveletCoefficients;
	}

	/*
	 * interactiveVerticalWaveletCoefficientsをセットする
	 */
	public void setinteractiveVerticalWaveletCoefficients(double[][] interactiveVerticalWaveletCoefficients) {
		this.interactiveVerticalWaveletCoefficients = interactiveVerticalWaveletCoefficients;
	}

	/*
	 * interactiveDiagonalWaveletCoefficientsをセットする
	 */
	public void setinteractiveDiagonalWaveletCoefficients() {
		this.interactiveDiagonalWaveletCoefficients = interactiveDiagonalWaveletCoefficients;
	}
}
