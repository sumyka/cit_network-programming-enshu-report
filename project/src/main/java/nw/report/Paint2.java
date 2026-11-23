package nw.report;

import javax.swing.*;
import java.awt.*;

public class Paint2 extends JFrame {
    public static void main(String[] args) {
        int circleLimit;
        int rectLimit;
        try {
            circleLimit = parseLimit(args, 0, "円");
            rectLimit = parseLimit(args, 1, "四角形");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        showWindow(circleLimit, rectLimit);
    }

    private static int parseLimit(String[] args, int index, String shape) {
        if (index >= args.length) {
            return PaintCanvas.UNLIMITED;
        }

        String message = shape + "の上限には0以上の整数を指定してください。";
        int limit;
        try {
            limit = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(message, e);
        }
        if (limit < 0) {
            throw new IllegalArgumentException(message);
        }
        return limit;
    }

    private static void showWindow(int circleLimit, int rectLimit) {
        Paint2 window = new Paint2();
        PaintCanvas canvas = new PaintCanvas(circleLimit, rectLimit);
        canvas.setBackground(Color.WHITE);
        window.setTitle("Paint2");
        window.getContentPane().add(canvas, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
