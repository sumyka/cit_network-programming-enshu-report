package nw.report;

import javax.swing.*;
import java.awt.*;

public class Paint2 extends JFrame {
    public static void main(String[] args) {
        int circleLimit;
        try {
            circleLimit = args.length == 0 
                        ? PaintCanvas.UNLIMITED 
                        : Integer.parseInt(args[0]);

            if (args.length > 0 && circleLimit < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("円の上限には0以上の整数を指定してください。");
            return;
        }

        showWindow(circleLimit);
    }

    private static void showWindow(int circleLimit) {
        Paint2 window = new Paint2();
        PaintCanvas canvas = new PaintCanvas(circleLimit);
        canvas.setBackground(Color.WHITE);
        window.setTitle("Paint2");
        window.getContentPane().add(canvas, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
