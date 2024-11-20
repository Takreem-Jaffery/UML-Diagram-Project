package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ResizeableClassAndCommentUI extends JFrame {
    JPanel pageTitlePanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    public ResizeableClassAndCommentUI() {
        // Initialize panels
        pageTitlePanel = new JPanel();
        canvas = new UMLCanvas();
        samplesPanel = new SamplesPanel(canvas);

        JLabel pageTitle = new JLabel("UML Class Diagram");
        pageTitle.setForeground(Color.white);

        // Configure page title
        pageTitlePanel.add(pageTitle);
        pageTitlePanel.setBackground(new Color(51, 51, 51));

        // Configure main frame layout
        this.setLayout(new BorderLayout());
        this.add(pageTitlePanel, BorderLayout.NORTH);
        this.add(samplesPanel, BorderLayout.EAST);
        this.add(canvas, BorderLayout.CENTER);

        this.setSize(900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // UML Component class
    public class UMLComponent {
        private String type;
        private Point position;
        private String name;
        private int width; // Width of the component
        private int height; // Height of the component
        private boolean resizing; // Whether the component is being resized

        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.width = 100; // Default width
            this.height = 50; // Default height
            this.resizing = false;
        }

        public void move(Point newPoint) {
            if (!resizing) {
                position = new Point(newPoint.x - width / 2, newPoint.y - height / 2); // Center the drag
            }
        }

        public boolean contains(Point point) {
            return point.x >= position.x && point.x <= position.x + width
                    && point.y >= position.y && point.y <= position.y + height;
        }

        public boolean isOnResizeEdge(Point point) {
            int margin = 10; // Area near the edges for resizing
            return (point.x >= position.x + width - margin && point.x <= position.x + width
                    && point.y >= position.y && point.y <= position.y + height) ||
                    (point.y >= position.y + height - margin && point.y <= position.y + height
                            && point.x >= position.x && point.x <= position.x + width);
        }

        public void resize(Point newPoint) {
            width = Math.max(50, newPoint.x - position.x); // Minimum width
            height = Math.max(30, newPoint.y - position.y); // Minimum height
        }

        public void setName(String name) {
            this.name = name;
        }

        public void draw(Graphics g) {
            int x = position.x, y = position.y;
            g.setColor(Color.BLACK);
            switch (type) {
                case "Class":
                case "Comment":
                    g.drawRect(x, y, width, height);
                    if (type.equals("Class")) {
                        g.drawLine(x, y + 15, x + width, y + 15);
                    }
                    break;
                default:
                    // Non-resizable components
                    g.drawRect(x, y, 100, 50);
                    break;
            }
            g.drawString(name, x + 10, y + 20);
        }
    }

    // UMLCanvas class
    public class UMLCanvas extends JPanel {
        private ArrayList<UMLComponent> components;
        private UMLComponent selectedComponent;
        private boolean resizing;

        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing = false;

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point point = e.getPoint();
                    for (UMLComponent component : components) {
                        if (component.isOnResizeEdge(point)) {
                            selectedComponent = component;
                            resizing = true;
                            return;
                        }
                        if (component.contains(point)) {
                            selectedComponent = component;
                            resizing = false;
                            return;
                        }
                    }
                    selectedComponent = null;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    resizing = false;
                    if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null) {
                        int response = JOptionPane.showConfirmDialog(
                                UMLCanvas.this,
                                "Do you want to delete this component?",
                                "Delete Component",
                                JOptionPane.OK_CANCEL_OPTION
                        );
                        if (response == JOptionPane.OK_OPTION) {
                            components.remove(selectedComponent);
                            selectedComponent = null;
                            repaint();
                        }
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedComponent != null && e.getClickCount() == 2) {
                        String newName = JOptionPane.showInputDialog("Enter new name:");
                        if (newName != null) {
                            selectedComponent.setName(newName);
                            repaint();
                        }
                    }
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedComponent != null) {
                        if (resizing) {
                            selectedComponent.resize(e.getPoint());
                        } else {
                            selectedComponent.move(e.getPoint());
                        }
                        repaint();
                    }
                }
            });
        }

        public void createNewComponent(String type) {
            components.add(new UMLComponent(type, new Point(100, 100)));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (UMLComponent component : components) {
                component.draw(g);
            }
        }
    }

    // Panel to display static samples
    public class SamplesPanel extends JPanel {
        private UMLCanvas canvas;

        public SamplesPanel(UMLCanvas canvas) {
            this.canvas = canvas;
            this.setPreferredSize(new Dimension(150, 0));
            this.setBackground(new Color(200, 200, 200));

            // Add mouse listener to detect clicks on samples
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String type = detectSampleClick(e.getPoint());
                    if (type != null) {
                        canvas.createNewComponent(type);
                    }
                }
            });
        }

        // Detect which sample is clicked
        private String detectSampleClick(Point point) {
            int y = point.y;
            if (y < 50) return "Class";
            else if (y < 100) return "Association";
            else if (y < 150) return "Inheritance";
            else if (y < 200) return "Aggregation";
            else if (y < 250) return "Composition";
            else if (y < 300) return "Comment";
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSamples(g);
        }

        // Draw static samples
        private void drawSamples(Graphics g) {
            int x = 10, y = 10;
            g.setColor(Color.BLACK);

            // Class
            g.drawRect(x, y, 100, 50);
            g.drawLine(x, y + 15, x + 100, y + 15);
            g.drawString("Class", x + 35, y + 10);

            // Association
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawString("Association", x + 35, y + 10);

            // Inheritance
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x + 100, x + 105, x + 100}, new int[]{y + 20, y + 25, y + 30}, 3);
            g.drawString("Inheritance", x + 25, y + 10);

            // Aggregation
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
            g.drawString("Aggregation", x + 20, y + 10);

            // Composition
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.fillPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
            g.drawString("Composition", x + 20, y + 10);

            // Comment
            y += 50;
            g.drawRect(x, y, 100, 50);
            g.drawString("Comment...", x + 10, y + 25);
        }
    }

    public static void main(String[] args) {
        new ResizeableClassAndCommentUI();
    }
}
