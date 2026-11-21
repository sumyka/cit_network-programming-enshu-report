package nw.report;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
    static final int UNLIMITED = -1;

    private final Deque<Figure> figures = new ArrayDeque<>();
    private final int circleLimit;
    private long totalCircleCount;

    PaintCanvas() {
        this(UNLIMITED);
    }

    PaintCanvas(int circleLimit) {
        this.circleLimit = circleLimit;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override public void mousePressed(MouseEvent e) {
        addCircle(e);
    }

    @Override public void mouseDragged(MouseEvent e) {
        addCircle(e);
    }

    @Override public void mouseReleased(MouseEvent e) {
        addCircle(e);
    }

    private void addCircle(MouseEvent e) {
        if (circleLimit == 0) {
            return;
        }

        Circle circle = new Circle();
        circle.moveto(e.getX(), e.getY());
        figures.addLast(circle);
        if (circleLimit != UNLIMITED && figures.size() > circleLimit) {
            figures.removeFirst();
        }

        totalCircleCount++;
        System.out.println("描画した円の数： " + totalCircleCount);
        repaint();
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Figure figure : figures) {
            figure.paint(g2);
        }
    }
}
