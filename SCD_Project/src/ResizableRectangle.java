package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResizableRectangle extends JPanel {

    private Rectangle rectangle = new Rectangle(100, 100, 200, 150); // Initial rectangle

    private boolean resizing = false; // Flag to indicate resizing
    private static final int HANDLE_SIZE = 10; // Handle size for resizing
    private int dragX, dragY; // To store the last mouse position

    public ResizableRectangle() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isInResizeHandle(e.getPoint())) {
                    resizing = true;
                    dragX = e.getX();
                    dragY = e.getY();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resizing = false; // Stop resizing when the mouse is released
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (resizing) {
                    int deltaX = e.getX() - dragX;
                    int deltaY = e.getY() - dragY;

                    // Update rectangle dimensions
                    rectangle.width += deltaX;
                    rectangle.height += deltaY;

                    // Update drag position
                    dragX = e.getX();
                    dragY = e.getY();

                    // Repaint the panel to update the rectangle
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Draw the rectangle
        g2d.setColor(Color.BLUE);
        g2d.fill(rectangle);

        // Draw the resize handle
        g2d.setColor(Color.RED);
        g2d.fillRect(rectangle.x + rectangle.width - HANDLE_SIZE,
                rectangle.y + rectangle.height - HANDLE_SIZE,
                HANDLE_SIZE, HANDLE_SIZE);
    }

    private boolean isInResizeHandle(Point p) {
        // Check if the point is within the resize handle
        return new Rectangle(rectangle.x + rectangle.width - HANDLE_SIZE,
                rectangle.y + rectangle.height - HANDLE_SIZE,
                HANDLE_SIZE, HANDLE_SIZE).contains(p);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Resizable Rectangle");
        ResizableRectangle resizableRectangle = new ResizableRectangle();

        frame.add(resizableRectangle);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
