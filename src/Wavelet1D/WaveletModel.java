package Wavelet1D;

import wavelet.WaveletTransform1D;
import java.util.Arrays;

public class WaveletModel {
    private double[] sourceCoefficients;
    private double[] scalingCoefficients;
    private double[] waveletCoefficients;
    private double[] recomposedCoefficients;
    private double[] interactiveWaveletCoefficients;

    public WaveletModel() {
        double[] anArray = dataSampleCoefficients();

        WaveletTransform1D transform = new WaveletTransform1D(anArray);
        sourceCoefficients = transform.getSourceCofficients();
        scalingCoefficients = transform.getScalingCofficients();
        waveletCoefficients = transform.getWaveletCofficients();
        recomposedCoefficients = transform.getRecomposedCoefficients();
        interactiveWaveletCoefficients = new double[waveletCoefficients.length];
        Arrays.fill(interactiveWaveletCoefficients, 0.0d);
    }

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

    public double[] getSourceCoefficients() {
        return sourceCoefficients;
    }

    public double[] getScalingCoefficients() {
        return scalingCoefficients;
    }

    public double[] getWaveletCoefficients() {
        return waveletCoefficients;
    }

    public double[] getRecomposedCoefficients() {
        return recomposedCoefficients;
    }

    public double[] getInteractiveWaveletCoefficients() {
        return interactiveWaveletCoefficients;
    }

    public void updateInteractiveCoefficient(int index) {
        interactiveWaveletCoefficients[index] = waveletCoefficients[index];
    }
}
