package nw.report;

import java.awt.*;
import java.awt.geom.*;

public class Rect extends Figure {
    double width;
    double height;

    Rect() {
        color = Color.BLACK;
        width = 10.0;
        height = 10.0;
    }

    @Override 
    public void paint(Graphics2D g) {
        Rectangle2D f = new Rectangle2D.Double(x - width / 2, y - height / 2, width, height);
        g.setStroke(new BasicStroke(
            2.0f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND)
        );
        g.setPaint(color);
        g.draw(f); // g.fill(f);
    }
}
