package wavelet;

import java.util.stream.IntStream;

public class WaveletTransform2D extends WaveletTransformation {

	protected double[][] sourceCoefficients;

	protected double[][] scalingCoefficients;

	protected double[][][] waveletCoefficients;

	protected double[][] recomposedCoefficients;

	/*
	 * 2次元変換のコンストラクタ
	 * 変換まえの信号を受け取る
	 */
	public WaveletTransform2D(double[][] sourceCollection) {
		super.initalize();
		this.sourceCoefficients = sourceCollection;
	}

	/*
	 * 2次元変換のコンストラクタ
	 * スケーリング係数とウェーブレット係数を受け取る
	 */
	public WaveletTransform2D(double[][] scalingCoefficients, double[][][] waveletCoefficients) {
		this.setScalingCofficients(scalingCoefficients);
		this.setWaveletCofficients(waveletCoefficients);
	}

	/*
	 * 初期化用のメソッド
	 */
	protected void initalize() {
		super.initalize();
		this.sourceCoefficients = null;
		this.scalingCoefficients = null;
		this.waveletCoefficients = null;
		this.recomposedCoefficients = null;
	}

	/*
	 * 変換後の信号を返す
	 */
	public double[][] getRecomposedCoefficients() {
		if (this.recomposedCoefficients == null) {
			this.calcrationRecomposedCoefficients();
		}
		return this.recomposedCoefficients;
	}

	/*
	 * 変換まえの信号を返す
	 */
	public double[][] getSourceCofficients() {
		return this.sourceCoefficients;
	}

	/*
	 * 変換まえの信号をフィールドにする
	 */
	public void setSourceCofficients(double[][] sourceCollection) {
		this.sourceCoefficients = sourceCollection;
		this.scalingCoefficients = null;
		this.waveletCoefficients = null;
	}

	/*
	 * スケーリング係数を返す
	 */
	public double[][] getScalingCofficients() {
		if (this.scalingCoefficients == null) {
			this.calcrationScalingAndWaveletCofficients();
		}
		return this.scalingCoefficients;
	}

	/*
	 * スケーリング係数をフィールドにする
	 */
	public void setScalingCofficients(double[][] scalingCoefficient) {
		this.scalingCoefficients = scalingCoefficient;
		this.recomposedCoefficients = null;
	}

	/*
	 * ウェーブレット係数を返す
	 */
	public double[][][] getWaveletCofficients() {
		if (this.waveletCoefficients == null) {
			this.calcrationScalingAndWaveletCofficients();
		}
		return this.waveletCoefficients;
	}

	/*
	 * ウェーブレット係数をフィールドにする
	 */
	public void setWaveletCofficients(double[][][] waveletCoefficient) {
		this.waveletCoefficients = waveletCoefficient;
		this.recomposedCoefficients = null;
	}

	/*
	 * 水平方向のウェーブレット係数を返す
	 */
	public double[][] getHorizonalWaveletCoefficient() {
		return this.getWaveletCofficients()[0];
	}

	/*
	 * 垂直方向のウェーブレット係数を返す
	 */
	public double[][] getVerticalWaveletCoefficient() {
		return this.getWaveletCofficients()[1];
	}

	/*
	 * 斜め方向のウェーブレット係数を返す
	 */
	public double[][] getDiagonalWaveletCoefficient() {
		return this.getWaveletCofficients()[2];
	}

	/*
	 * ウェーブレット2次元変換の計算
	 * スケーリング係数とウェーブレット係数を計算するメソッド
	 */
	protected void calcrationRecomposedCoefficients() {
		if (this.scalingCoefficients == null || this.waveletCoefficients == null) {
			this.calcrationScalingAndWaveletCofficients();
		}
		Integer signalRowSize = this.rowSize(this.scalingCoefficients);
		Integer signalRowDoubleSize = signalRowSize * 2;
		Integer signalColumnSize = this.columnSize(this.scalingCoefficients);
		Integer signalColumnDoubleSize = signalColumnSize * 2;

		double[][] scalingCoefficient = this.matrixTranspose(this.scalingCoefficients);
		double[][] horizonalWaveletCoefficient = this.matrixTranspose(this.getHorizonalWaveletCoefficient());
		double[][] verticalWaveletCoefficient = this.matrixTranspose(this.getVerticalWaveletCoefficient());
		double[][] diagonalWaveletCoefficient = this.matrixTranspose(this.getDiagonalWaveletCoefficient());

		double[][] recomposedScalingCoefficients = new double[signalColumnSize][signalRowDoubleSize];
		double[][] recomposedWaveletCoefficients = new double[signalColumnSize][signalRowDoubleSize];

		for (Integer i = 0; i < signalColumnSize; i++) {
			WaveletTransform1D waveletTransform1D = new WaveletTransform1D(
					this.convertTo1DMatrix(scalingCoefficient, i),
					this.convertTo1DMatrix(horizonalWaveletCoefficient, i));
			this.convertTo2DMatrix(recomposedScalingCoefficients, waveletTransform1D.getRecomposedCoefficients(), i);
			waveletTransform1D = new WaveletTransform1D(this.convertTo1DMatrix(verticalWaveletCoefficient, i),
					this.convertTo1DMatrix(diagonalWaveletCoefficient, i));
			this.convertTo2DMatrix(recomposedWaveletCoefficients, waveletTransform1D.getRecomposedCoefficients(), i);
		}

		recomposedScalingCoefficients = this.matrixTranspose(recomposedScalingCoefficients);
		recomposedWaveletCoefficients = this.matrixTranspose(recomposedWaveletCoefficients);
		double[][] recomposedCoefficients = new double[signalRowDoubleSize][signalColumnDoubleSize];

		for (Integer i = 0; i < signalRowDoubleSize; i++) {
			WaveletTransform1D waveletTransform = new WaveletTransform1D(this.convertTo1DMatrix(recomposedScalingCoefficients, i),
					this.convertTo1DMatrix(recomposedWaveletCoefficients, i));
			this.convertTo2DMatrix(recomposedCoefficients, waveletTransform.getRecomposedCoefficients(), i);
		}

		this.recomposedCoefficients = recomposedCoefficients;
	}

	/*
	 * ウェーブレット2次元逆変換の計算
	 * 変換後の信号を計算するメソッド
	 */
	protected void calcrationScalingAndWaveletCofficients() {
		Integer originSignalRow = this.rowSize(this.sourceCoefficients);
		Integer originSignalRowHarf = originSignalRow / 2;
		Integer originSignalColumn = this.columnSize(this.sourceCoefficients);
		Integer originSignalColumnHarf = originSignalColumn / 2;
		double[][] scalingCoefficient = new double[originSignalRow][originSignalColumnHarf];
		double[][] waveletCoefficient = new double[originSignalRow][originSignalColumnHarf];

		for (Integer i = 0; i < originSignalRow; i++) {
			WaveletTransform1D waveletTransform = new WaveletTransform1D(
					this.convertTo1DMatrix(this.sourceCoefficients, i));
			this.convertTo2DMatrix(scalingCoefficient, waveletTransform.getScalingCofficients(), i);
			this.convertTo2DMatrix(waveletCoefficient, waveletTransform.getWaveletCofficients(), i);
		}

		scalingCoefficient = this.matrixTranspose(scalingCoefficient);
		waveletCoefficient = this.matrixTranspose(waveletCoefficient);
		double[][] scalingCoefficient2 = new double[originSignalColumnHarf][originSignalRowHarf];
		double[][] horizonalWaveletCoefficient = new double[originSignalColumnHarf][originSignalRowHarf];
		double[][] verticalWaveletCoefficient = new double[originSignalColumnHarf][originSignalRowHarf];
		double[][] diagonalWaveletCoefficient = new double[originSignalColumnHarf][originSignalRowHarf];

		for (Integer i = 0; i < originSignalColumnHarf; i++) {
			WaveletTransform1D waveletTransform2 = new WaveletTransform1D(
					this.convertTo1DMatrix(scalingCoefficient, i));
			this.convertTo2DMatrix(scalingCoefficient2, waveletTransform2.getScalingCofficients(), i);
			this.convertTo2DMatrix(horizonalWaveletCoefficient, waveletTransform2.getWaveletCofficients(), i);
			waveletTransform2 = new WaveletTransform1D(this.convertTo1DMatrix(waveletCoefficient, i));
			this.convertTo2DMatrix(verticalWaveletCoefficient, waveletTransform2.getScalingCofficients(), i);
			this.convertTo2DMatrix(diagonalWaveletCoefficient, waveletTransform2.getWaveletCofficients(), i);
		}

		scalingCoefficient2 = this.matrixTranspose(scalingCoefficient2);
		horizonalWaveletCoefficient = this.matrixTranspose(horizonalWaveletCoefficient);
		verticalWaveletCoefficient = this.matrixTranspose(verticalWaveletCoefficient);
		diagonalWaveletCoefficient = this.matrixTranspose(diagonalWaveletCoefficient);

		this.scalingCoefficients = scalingCoefficient2;
		this.waveletCoefficients = new double[][][] { horizonalWaveletCoefficient, verticalWaveletCoefficient,
				diagonalWaveletCoefficient };
	}

	/*
	 * 2次元配列を1次元配列に
	 */
	private double[] convertTo1DMatrix(double[][] collection, Integer index) {
		return collection[index];
	}

	/*
	 * 1次元配列を2次元配列に
	 */
	private void convertTo2DMatrix(double[][] matrix2D, double[] matrix1D, Integer index) {
		IntStream.range(0, matrix2D[index].length).forEach(i -> matrix2D[index][i] = matrix1D[i]);
		// for (Integer i = 0; i < matrix2D[index].length; i++) {
		// matrix2D[index][i] = matrix1D[i];
		// }
	}

	private double[][] matrixTranspose(double[][] originMatrix) {
		Integer columnSize = columnSize(originMatrix);
		Integer rowSize = rowSize(originMatrix);
		double[][] changeMatrix = new double[columnSize][rowSize];
		IntStream.range(0, originMatrix.length).forEach(
				i -> IntStream.range(0, originMatrix[i].length).forEach(j -> changeMatrix[j][i] = originMatrix[i][j]));
		return changeMatrix;
	}

	private Integer columnSize(double[][] collection) {
		return collection[0].length;
	}

	private Integer rowSize(double[][] collection) {
		return collection.length;
	}

}
