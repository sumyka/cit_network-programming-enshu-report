package nw.report;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
    static final int UNLIMITED = -1;

    private final Set<Figure> figures = new LinkedHashSet<>();
    private final FigureHistory circleHistory;
    private final FigureHistory rectHistory;
    private final DrawingModes drawingModes = new DrawingModes();
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
        circleHistory = new FigureHistory(circleLimit);
        rectHistory = new FigureHistory(rectLimit);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this::resizeNextFigure);
        drawingModes.bindTo(this);
    }

    @Override public void mousePressed(MouseEvent e) {
        addFigure(e);
    }

    @Override public void mouseDragged(MouseEvent e) {
        addFigure(e);
    }

    @Override public void mouseReleased(MouseEvent e) {
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
        int added;
        if (isPressedShiftkey(e)) {
            added = addFigures(e, this::createRect, rectHistory);
            totalRectCount += added;
        } else {
            added = addFigures(e, this::createCircle, circleHistory);
            totalCircleCount += added;
        }
        if (added > 0) {
            System.out.println("描画した円：" + totalCircleCount + ", 描画した四角形: " + totalRectCount);
            repaint();
        }
    }

    private Rect createRect() {
        Rect rect = new Rect();
        rect.width = figureSize;
        rect.height = figureSize;
        return rect;
    }

    private Circle createCircle() {
        Circle circle = new Circle();
        circle.size = figureSize;
        return circle;
    }

    private int addFigures(MouseEvent e, Supplier<Figure> createFigure, FigureHistory history) {
        List<Point2D.Double> points = drawingModes.drawingPoints(e.getX(), e.getY(), getWidth(), getHeight());
        if (history.limit != UNLIMITED && points.size() > history.limit) {
            return 0;
        }

        List<Figure> group = new ArrayList<>(points.size());
        for (Point2D.Double point : points) {
            Figure figure = createFigure.get();
            figure.moveto(point.x, point.y);
            group.add(figure);
        }
        history.add(group, figures);
        return group.size();
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawingModes.paintGuide(g2, getWidth(), getHeight());
        for (Figure figure : figures) {
            figure.paint(g2);
        }
    }

    private static final class FigureHistory {
        private final int limit;
        private final Deque<List<Figure>> groups = new ArrayDeque<>();
        private int visibleCount;

        private FigureHistory(int limit) {
            this.limit = limit;
        }

        private void add(List<Figure> group, Set<Figure> figures) {
            if (limit != UNLIMITED) {
                while (visibleCount > limit - group.size()) {
                    List<Figure> oldest = groups.removeFirst();
                    figures.removeAll(oldest);
                    visibleCount -= oldest.size();
                }
                groups.addLast(group);
                visibleCount += group.size();
            }
            figures.addAll(group);
        }
    }
}
