package Wavelet1D;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Plot1dMain {
    public static void execute() {
        WaveletModel model = new WaveletModel();
        WaveletController controller = new WaveletController(model);

        double[] sourceCoefficients = controller.getSourceCoefficients();
        double[] scalingCoefficients = controller.getScalingCoefficients();
        double[] waveletCoefficients = controller.getWaveletCoefficients();
        double[] recomposedCoefficients = controller.getRecomposedCoefficients();
        double[] interactiveWaveletCoefficients = controller.getInteractiveWaveletCoefficients();

        double maxTop = Math.max(Arrays.stream(sourceCoefficients).max().orElse(1.0),
                Math.max(Arrays.stream(scalingCoefficients).max().orElse(1.0), Arrays.stream(waveletCoefficients).max().orElse(1.0)));
        double minTop = Math.min(Arrays.stream(sourceCoefficients).min().orElse(0.0),
                Math.min(Arrays.stream(scalingCoefficients).min().orElse(0.0), Arrays.stream(waveletCoefficients).min().orElse(0.0)));

        double maxBottom = Math.max(Arrays.stream(recomposedCoefficients).max().orElse(1.0),
                Math.max(Arrays.stream(scalingCoefficients).max().orElse(1.0), Arrays.stream(interactiveWaveletCoefficients).max().orElse(1.0)));
        double minBottom = Math.min(Arrays.stream(recomposedCoefficients).min().orElse(0.0),
                Math.min(Arrays.stream(scalingCoefficients).min().orElse(0.0), Arrays.stream(interactiveWaveletCoefficients).min().orElse(0.0)));

        JFrame frame = new JFrame("Wavelet Transform (1D)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 3));  

        frame.add(new WaveletView(sourceCoefficients, "Source Coefficients", minTop, maxTop, controller));
        frame.add(new WaveletView(scalingCoefficients, "Scaling Coefficients", minTop, maxTop, controller));
        frame.add(new WaveletView(waveletCoefficients, "Wavelet Coefficients", minTop, maxTop, controller));
        frame.add(new WaveletView(recomposedCoefficients, "Recomposed Coefficients", minBottom, maxBottom, controller));
        frame.add(new WaveletView(scalingCoefficients, "Scaling Coefficients", minBottom, maxBottom, controller));
        frame.add(new WaveletView(interactiveWaveletCoefficients, "Interactive Wavelet Coefficients", minBottom, maxBottom, waveletCoefficients, interactiveWaveletCoefficients, controller));

        frame.setSize(1200, 600);
        frame.setVisible(true);

        JFrame frame2 = new JFrame("Wavelet Example (1D)");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLayout(new GridLayout(2, 2));  

        frame2.add(new WaveletView(sourceCoefficients, "Source Coefficients", minTop, maxTop, controller));
        frame2.add(new WaveletView(scalingCoefficients, "Scaling Coefficients", minTop, maxTop, controller));
        frame2.add(new WaveletView(recomposedCoefficients, "Recomposed Coefficients", minBottom, maxBottom, controller));
        frame2.add(new WaveletView(waveletCoefficients, "Wavelet Coefficients", minTop, maxTop, controller));

        frame2.setSize(800, 600);
        frame2.setVisible(true);
    }
}
