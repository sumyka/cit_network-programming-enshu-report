package nw.report;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
    static final int UNLIMITED = -1;

    private final Set<Figure> figures = new LinkedHashSet<>();
    private final Deque<Circle> visibleCircles = new ArrayDeque<>();
    private final Deque<Rect> visibleRects = new ArrayDeque<>();
    private final int circleLimit;
    private final int rectLimit;
    private long totalCircleCount;
    private long totalRectCount;
    private double figureSize = 10.0;

    PaintCanvas() {
        this(UNLIMITED);
    }

    PaintCanvas(int circleLimit) {
        this(circleLimit, UNLIMITED);
    }

    PaintCanvas(int circleLimit, int rectLimit) {
        this.circleLimit = circleLimit;
        this.rectLimit = rectLimit;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this::resizeNextFigure);
    }

    @Override public void mousePressed(MouseEvent e) {
        addFigure(e);
    }

    @Override public void mouseDragged(MouseEvent e) {
        addFigure(e);
    }

    @Override public void mouseReleased(MouseEvent e) {
        addFigure(e);
    }

    private void resizeNextFigure(MouseWheelEvent e) {
        // macOSでは水平スクロールをこれで受け取る
        if (!e.isShiftDown()) {
            figureSize = Math.max(1.0, figureSize - 2.0 * e.getPreciseWheelRotation());
        }
    }

    private boolean isPressedShiftkey(MouseEvent e) {
        return e.isShiftDown();
    }

    private void addFigure(MouseEvent e) {
        if (isPressedShiftkey(e)) {
            if (rectLimit == 0) {
                return;
            }
            Rect rect = new Rect();
            rect.width = figureSize;
            rect.height = figureSize;
            addFigure(rect, e, rectLimit, visibleRects);
            totalRectCount++;
        } else {
            if (circleLimit == 0) {
                return;
            }
            Circle circle = new Circle();
            circle.size = figureSize;
            addFigure(circle, e, circleLimit, visibleCircles);
            totalCircleCount++;
        }
        System.out.println("描画した円：" + totalCircleCount + ", 描画した四角形: " + totalRectCount);
    }

    private <T extends Figure> void addFigure(T figure, MouseEvent e, int limit, Deque<T> visibleFigures) {
        figure.moveto(e.getX(), e.getY());
        figures.add(figure);
        if (limit != UNLIMITED) {
            visibleFigures.addLast(figure);
            if (visibleFigures.size() > limit) {
                figures.remove(visibleFigures.removeFirst());
            }
        }
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
