package wavelet;

public class WaveletTransform1D extends WaveletTransformation {

	protected double[] sourceCoefficients;

	protected double[] scalingCoefficients;

	protected double[] waveletCoefficients;

	protected double[] recomposedCoefficients;

	/*
	 * 1次元変換のコンストラクタ
	 * 変換まえの信号を受け取る
	 */
	public WaveletTransform1D(double[] sourceCollection) {
		super.initalize();
		this.setSourceCofficients(sourceCollection);
	}

	/*
	 * 1次元変換のコンストラクタ
	 * スケーリング係数とウェーブレット係数を受け取る
	 */
	public WaveletTransform1D(double[] scalingCoefficients, double[] waveletCoefficients) {
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
	 * 変換まえの信号を返す
	 */
	public double[] getSourceCofficients() {
		return this.sourceCoefficients;
	}

	/*
	 * 変換まえの信号をフィールドにする
	 */
	public void setSourceCofficients(double[] sourceCollection) {
		this.sourceCoefficients = sourceCollection;
		this.scalingCoefficients = null;
		this.waveletCoefficients = null;
	}

	/*
	 * スケーリング係数を返す
	 */
	public double[] getScalingCofficients() {
		if (this.scalingCoefficients == null) {
			this.calcrationScalingAndWaveletCoefficients();
		}
		return this.scalingCoefficients;
	}

	/*
	 * スケーリング係数をフィールドにする
	 */
	public void setScalingCofficients(double[] scalingCoefficient) {
		this.scalingCoefficients = scalingCoefficient;
		this.recomposedCoefficients = null;
	}

	/*
	 * ウェーブレット係数を返す
	 */
	public double[] getWaveletCofficients() {
		if (this.waveletCoefficients == null) {
			this.calcrationScalingAndWaveletCoefficients();
		}
		return this.waveletCoefficients;
	}

	/*
	 * ウェーブレット係数をフィールドにする
	 */
	public void setWaveletCofficients(double[] waveletCoefficient) {
		this.waveletCoefficients = waveletCoefficient;
		this.recomposedCoefficients = null;
	}

	/*
	 * 変換後の信号を返す
	 */
	public double[] getRecomposedCoefficients() {
		if (this.recomposedCoefficients == null) {
			this.calcretionRecomposedCoefficients();
		}
		return this.recomposedCoefficients;
	}

	/*
	 * ウェーブレット1次元変換の計算
	 * スケーリング係数とウェーブレット係数を計算するメソッド
	 */
	protected void calcrationScalingAndWaveletCoefficients() {
		if (this.sourceCoefficients != null) {
			Integer originSignalLength = this.sourceCoefficients.length;
			Integer originSignalLengthHarf = originSignalLength / 2;
			this.scalingCoefficients = new double[originSignalLengthHarf];
			this.waveletCoefficients = new double[originSignalLengthHarf];

			for (Integer i = 0; i < originSignalLengthHarf; i++) {
				this.scalingCoefficients[i] = 0.0;
				this.waveletCoefficients[i] = 0.0;
				for (Integer j = 0; j < this.daubechiesScalingSequence.length; j++) {
					Integer index = (j + 2 * i) % originSignalLength;
					double sourceValue = this.sourceCoefficients[index];
					this.scalingCoefficients[i] += this.daubechiesScalingSequence[j] * sourceValue;
					this.waveletCoefficients[i] += this.daubechiesWaveletSequence[j] * sourceValue;
				}
			}
		}
	}

	/*
	 * ウェーブレット1次元逆変換の計算
	 * 変換後の信号を計算するメソッド
	 */
	protected void calcretionRecomposedCoefficients() {
		if (this.scalingCoefficients == null || this.waveletCoefficients == null) {
			this.calcrationScalingAndWaveletCoefficients();
		}
		Integer originSignalLengthHarf = this.scalingCoefficients.length;
		this.recomposedCoefficients = new double[originSignalLengthHarf * 2];
		Integer correctionValue = Math.max(1024, originSignalLengthHarf);

		for (Integer i = 0; i < originSignalLengthHarf; i++) {
			this.recomposedCoefficients[2 * i + 1] = 0.0;
			this.recomposedCoefficients[2 * i] = 0.0;
			for (Integer j = 0; j < this.daubechiesScalingSequence.length / 2; j++) {
				Integer index = (i - j + correctionValue) % originSignalLengthHarf;
				double scalingCoefficient = this.scalingCoefficients[index];
				double waveletCoefficient = this.waveletCoefficients[index];

				this.recomposedCoefficients[2 * i + 1] += this.daubechiesScalingSequence[2 * j + 1]
						* scalingCoefficient
						+ super.daubechiesWaveletSequence[2 * j + 1] * waveletCoefficient;
				this.recomposedCoefficients[2 * i] += this.daubechiesScalingSequence[2 * j] * scalingCoefficient
						+ super.daubechiesWaveletSequence[2 * j] * waveletCoefficient;
			}
		}
	}

}
