package nw.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

final class DrawingModes {
    private enum Mode {
        NORMAL, MIRROR, KALEIDOSCOPE
    }

    private static final BasicStroke GUIDE_STROKE = new BasicStroke(
        1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{4.0f, 4.0f}, 0.0f);

    private Mode active = Mode.NORMAL;

    void bindTo(JComponent canvas) {
        bindToggle(canvas, Mode.MIRROR, KeyEvent.VK_1, KeyEvent.VK_NUMPAD1);
        bindToggle(canvas, Mode.KALEIDOSCOPE, KeyEvent.VK_2, KeyEvent.VK_NUMPAD2);
    }

    private void bindToggle(JComponent canvas, Mode mode, int mainKey, int keypadKey) {
        var inputMap = canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(mainKey, 0, true), mode);
        inputMap.put(KeyStroke.getKeyStroke(keypadKey, 0, true), mode);
        canvas.getActionMap().put(mode, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = active == mode ? Mode.NORMAL : mode;
                canvas.repaint();
            }
        });
    }

    List<Point2D.Double> drawingPoints(int x, int y, int width, int height) {
        return switch (active) {
            case NORMAL -> List.of(new Point2D.Double(x, y));
            case MIRROR -> MirrorMode.drawingPoints(x, y, width);
            case KALEIDOSCOPE -> KaleidoscopeMode.drawingPoints(x, y, width, height);
        };
    }

    void paintGuide(Graphics2D graphics, int width, int height) {
        if (active == Mode.NORMAL) {
            return;
        }

        Graphics2D guide = (Graphics2D) graphics.create();
        try {
            guide.setColor(Color.GRAY);
            guide.setStroke(GUIDE_STROKE);
            if (active == Mode.MIRROR) {
                MirrorMode.paintGuide(guide, width, height);
            } else {
                KaleidoscopeMode.paintGuide(guide, width, height);
            }
        } finally {
            guide.dispose();
        }
    }
}
