package Wavelet1D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WaveletView extends JPanel {
    private double[] data;
    private String label;
    private double min;
    private double max;
    private double[] waveletCoefficients;
    private double[] interactiveWaveletCoefficients;
    private WaveletController controller;

    public WaveletView(double[] data, String label, double min, double max, WaveletController controller) {
        this.data = data;
        this.label = label;
        this.min = min;
        this.max = max;
        this.controller = controller;
    }

    public WaveletView(double[] data, String label, double min, double max, double[] waveletCoefficients, double[] interactiveWaveletCoefficients, WaveletController controller) {
        this.data = data;
        this.label = label;
        this.min = min;
        this.max = max;
        this.waveletCoefficients = waveletCoefficients;
        this.interactiveWaveletCoefficients = interactiveWaveletCoefficients;
        this.controller = controller;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraph(g, data, label, min, max);
    }

    private void drawGraph(Graphics g, double[] data, String label, double min, double max) {
        int width = getWidth();
        int height = getHeight();
        int margin = 25;
        int graphWidth = width - 2 * margin;
        int graphHeight = height - 2 * margin;

        g.drawString(label, margin, margin - 2);

        int zeroY = height - margin - (int) ((0 - min) / (max - min) * graphHeight);
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(margin, zeroY, width - margin, zeroY);
        g.setColor(Color.BLACK);

        for (int i = 0; i < data.length; i++) {
            int x = margin + (i * graphWidth) / (data.length - 1);  
            int y = height - margin - (int) ((data[i] - min) / (max - min) * graphHeight);
            g.fillOval(x, y, 5, 5);  
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        int width = getWidth();
        int height = getHeight();
        int margin = 30;
        int graphWidth = width - 2 * margin;
        int graphHeight = height - 2 * margin;

        for (int i = 0; i < data.length; i++) {
            int x = margin + (i * graphWidth) / (data.length - 1);
            int y = height - margin - (int) ((data[i] - min) / (max - min) * graphHeight);
            if (Math.abs(mouseX - x) <= 2 && Math.abs(mouseY - y) <= 2) {
                controller.updateInteractiveCoefficient(i);
                repaint();
                break;
            }
        }
    }
}
