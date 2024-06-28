package main;

import javax.swing.SwingUtilities;
import Wavelet1D.Plot1dMain;
import Wavelet2D.Example;

public class Main {
  
  public static void main(String[] str) {
    Example example = new Example();
    example.run();
    System.out.println("Hello World");
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            Plot1dMain.execute();
        }
    });
}

}
