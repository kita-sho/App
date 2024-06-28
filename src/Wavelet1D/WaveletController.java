package Wavelet1D;

public class WaveletController {
    private WaveletModel model;

    public WaveletController(WaveletModel model) {
        this.model = model;
    }

    public void updateInteractiveCoefficient(int index) {
        model.updateInteractiveCoefficient(index);
    }

    public double[] getSourceCoefficients() {
        return model.getSourceCoefficients();
    }

    public double[] getScalingCoefficients() {
        return model.getScalingCoefficients();
    }

    public double[] getWaveletCoefficients() {
        return model.getWaveletCoefficients();
    }

    public double[] getRecomposedCoefficients() {
        return model.getRecomposedCoefficients();
    }

    public double[] getInteractiveWaveletCoefficients() {
        return model.getInteractiveWaveletCoefficients();
    }
}
