package wavelet;

public class WaveletTransformation extends Object {

	protected double[] daubechiesScalingSequence;

	protected double[] daubechiesWaveletSequence;

	public WaveletTransformation() {
		this.initalize();
	}

	private void CalcrationDaubechiesWaveletSequence(Integer number) {
		this.daubechiesScalingSequence = new double[] { 0.4829629131445341, 0.8365163037378077, 0.2241438680420134,
				-0.1294095225512603 };

		if (number == 3) {
			this.daubechiesScalingSequence = new double[] { 0.3326705529500825, 0.8068915093110924, 0.4598775021184914,
					-0.1350110200102546, -0.0854412738820267, 0.0352262918857095 };
		}
		if (number == 4) {
			this.daubechiesScalingSequence = new double[] { 0.2303778133088964, 0.7148465705529155, 0.6308807679298599,
					-0.0279837694168599, -0.1870348117190931, 0.0308413818355607, 0.0328830116668852,
					-0.010597401785069 };
		}

		Integer daubechiesSequenceLength = this.daubechiesScalingSequence.length;

		this.daubechiesWaveletSequence = new double[daubechiesSequenceLength];
		for (Integer i = 0; i < daubechiesSequenceLength; i++) {
			this.daubechiesWaveletSequence[i] = Math.pow(-1.0, i)
					* this.daubechiesScalingSequence[daubechiesSequenceLength - 1 - i];
		}
	}

	protected void initalize() {
		this.CalcrationDaubechiesWaveletSequence(2);
	}

}
