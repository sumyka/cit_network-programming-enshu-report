package nw.report;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

final class MirrorMode {
    static List<Point2D.Double> drawingPoints(int x, int y, int width) {
        var point = new Point2D.Double(x, y);
        int reflectedX = width - 1 - x;
        if (width <= 1 || reflectedX == x) {
            return List.of(point);
        }
        return List.of(point, new Point2D.Double(reflectedX, y));
    }

    static void paintGuide(Graphics2D graphics, int width, int height) {
        double axisX = (width - 1) / 2.0;
        graphics.draw(new Line2D.Double(axisX, 0, axisX, height));
    }
}
