package nw.report;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class KaleidoscopeMode {
    static List<Point2D.Double> drawingPoints(int x, int y, int width, int height) {
        if (width <= 1 || height <= 1) {
            return List.of(new Point2D.Double(x, y));
        }

        double centerX = (width - 1) / 2.0;
        double centerY = (height - 1) / 2.0;
        double offsetX = x - centerX;
        double offsetY = y - centerY;
        Set<Point2D.Double> points = new LinkedHashSet<>();

        for (int turn = 0; turn < 4; turn++) {
            points.add(new Point2D.Double(centerX + offsetX, centerY + offsetY));
            points.add(new Point2D.Double(centerX + offsetX, centerY - offsetY));

            // Rotate the offset by 90 degrees for the next pair.
            double nextX = -offsetY;
            offsetY = offsetX;
            offsetX = nextX;
        }
        return new ArrayList<>(points);
    }

    static void paintGuide(Graphics2D graphics, int width, int height) {
        double centerX = (width - 1) / 2.0;
        double centerY = (height - 1) / 2.0;
        int extent = Math.max(width, height);
        graphics.draw(new Line2D.Double(centerX, 0, centerX, height));
        graphics.draw(new Line2D.Double(0, centerY, width, centerY));
        graphics.draw(new Line2D.Double(centerX - extent, centerY - extent, centerX + extent, centerY + extent));
        graphics.draw(new Line2D.Double(centerX - extent, centerY + extent, centerX + extent, centerY - extent));
    }
}
