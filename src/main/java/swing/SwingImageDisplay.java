package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.ImageDisplay;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private final List<PaintOrder> orders;
    private int shiftStart;
    private Dragged dragged = Dragged.Null;
    private Released released = Released.Null;

    public SwingImageDisplay() {
        this.orders = new ArrayList<>();
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                dragged.to(e.getX() - shiftStart);
            }

            public void mouseMoved(MouseEvent e) { }
        };
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                shiftStart = e.getX();
            }

            public void mouseReleased(MouseEvent e) {
                released.at(e.getX() - shiftStart);
            }

            public void mouseEntered(MouseEvent e) { }

            public void mouseExited(MouseEvent e) { }
        };
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());
        System.out.println(orders);
        for (PaintOrder order : orders) {
            g.drawImage(load(order.image), order.offset, 0, null);

        }
    }

    private BufferedImage load(String name) {
        BufferedImage image = new BufferedImage(this.getWidth(), getHeight(), TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(colorOf(name));
        graphics.fillRect(0 ,0, this.getWidth(), this.getHeight());
        return image;
    }

    private Map<String,Color> colors = Map.of(
            "RED", Color.RED,
            "GREEN", Color.GREEN,
            "BLUE", Color.BLUE
    );

    private Color colorOf(String name) {
        return colors.get(name);
    }

    @Override
    public void clear() {
        orders.clear();
        repaint();
    }

    @Override
    public void paint(String image, int offset) {
        orders.add(new PaintOrder(image, offset));
        repaint();
    }

    @Override
    public void on(Dragged dragged) {
        this.dragged = dragged != null ? dragged : Dragged.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private record PaintOrder(String image, int offset) {}

}
