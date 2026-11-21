package nw.report;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class PaintCanvas extends JPanel implements MouseListener, MouseMotionListener {
    Figure obj = null;
    double x, y;
    ArrayList<Figure> objList;

    PaintCanvas() {
        objList = new ArrayList<Figure>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override public void mousePressed(MouseEvent e) {
        Point2D p = e.getPoint();
        x = p.getX();
        y = p.getY();
        obj = new Circle();
        obj.moveto(x,y);
        objList.add(obj);
        System.out.println("描画した円の数： " + objList.size());
        obj = null;
        repaint();
    }

    @Override public void mouseDragged(MouseEvent e) {
        Point2D p = e.getPoint();
        x = p.getX();
        y = p.getY();
        obj = new Circle();
        obj.moveto(x,y);
        objList.add(obj);
        System.out.println("描画した円の数： " + objList.size());
        obj = null;
        repaint();
    }

    @Override public void mouseReleased(MouseEvent e) {
        Point2D p = e.getPoint();
        x = p.getX();
        y = p.getY();
        obj = new Circle();
        obj.moveto(x,y);
        objList.add(obj);
        System.out.println("描画した円の数： " + objList.size());
        obj = null;
        repaint();
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Figure f;
        for(int i=0; i < objList.size(); i++){
            f = objList.get(i);
            f.paint(g2);
        }
        // System.out.println("描画した円の数： " + objList.size());
        if(obj != null) obj.paint(g2);
    }
}
