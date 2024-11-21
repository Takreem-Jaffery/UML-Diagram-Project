package  org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ResizableMoveableArrows extends JFrame {
    JPanel pageTitlePanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    public ResizableMoveableArrows() {
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

    public class UMLCanvas extends JPanel {
        private ArrayList<UMLComponent> components;
        private UMLComponent selectedComponent;
        private boolean resizing;

        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing = false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    if (selectedComponent != null && selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
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
                        String newName = JOptionPane.showInputDialog("Enter new label:");
                        if (newName != null) {
                            selectedComponent.setLabel(newName);
                            repaint();
                        }
                    }
                }
            });

            this.addMouseMotionListener(new MouseAdapter() {
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

        // Create a new component
        public void createNewComponent(String type) {
            components.add(new UMLComponent(type, new Point(100, 100), new Point(200, 200)));
            repaint();
        }

        // Select a component at a specific point
        private void selectComponentAt(Point point) {
            for (UMLComponent component : components) {
                if (component.contains(point)) {
                    selectedComponent = component;
                    return;
                }
            }
            selectedComponent = null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (UMLComponent component : components) {
                component.draw(g);
            }
        }
    }

    // UML Component class for different types
    public class UMLComponent {
        private String type;
        private Point start;
        private Point end;
        private String label;

        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.label = type;
        }

        public void move(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
        }

        public boolean contains(Point point) {
            return new Rectangle(start.x - 5, start.y - 5, 10, 10).contains(point) ||
                    new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }

        public boolean isInResizeHandle(Point point) {
            return new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }

        public void resize(Point point) {
            end.setLocation(point);
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawLine(start.x, start.y, end.x, end.y);

            // Draw arrowheads or other decorations based on type
            switch (type) {
                case "Association":
                    // Association: simple straight line
                    break;
                case "Inheritance":
                    drawArrowHead(g, start, end, false); // Hollow arrowhead
                    break;
                case "Aggregation":
                    drawDiamond(g, end, false);
                    break;
                case "Composition":
                    drawDiamond(g, end, true);
                    break;
            }

            // Draw label
            g.drawString(label, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
        }

        private void drawArrowHead(Graphics g, Point start, Point end, boolean filled) {
            int dx = end.x - start.x;
            int dy = end.y - start.y;
            double angle = Math.atan2(dy, dx);
            int arrowLength = 15;
            int arrowWidth = 7;

            int x1 = end.x - (int) (arrowLength * Math.cos(angle - Math.PI / 6));
            int y1 = end.y - (int) (arrowLength * Math.sin(angle - Math.PI / 6));
            int x2 = end.x - (int) (arrowLength * Math.cos(angle + Math.PI / 6));
            int y2 = end.y - (int) (arrowLength * Math.sin(angle + Math.PI / 6));

            g.drawPolygon(new int[]{end.x, x1, x2}, new int[]{end.y, y1, y2}, 3); // Hollow arrowhead
        }

        private void drawDiamond(Graphics g, Point end, boolean filled) {
            int diamondSize = 10;
            int[] xPoints = {
                    end.x,
                    end.x - diamondSize / 2,
                    end.x,
                    end.x + diamondSize / 2
            };
            int[] yPoints = {
                    end.y - diamondSize / 2,
                    end.y,
                    end.y + diamondSize / 2,
                    end.y
            };

            if (filled) {
                g.fillPolygon(xPoints, yPoints, 4);
            } else {
                g.drawPolygon(xPoints, yPoints, 4);
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

        private String detectSampleClick(Point point) {
            int y = point.y;
            if (y < 50) return "Association";
            else if (y < 100) return "Inheritance";
            else if (y < 150) return "Aggregation";
            else if (y < 200) return "Composition";
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSamples(g);
        }

        private void drawSamples(Graphics g) {
            int x = 10, y = 10;
            g.setColor(Color.BLACK);

            // Association
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawString("Association", x + 25, y + 10);

            // Inheritance
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x + 100, x + 105, x + 100}, new int[]{y + 20, y + 25, y + 30}, 3);
            g.drawString("Inheritance", x + 20, y + 10);

            // Aggregation
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x, x + 10, x, x - 10}, new int[]{y + 20, y + 25, y + 30, y + 25}, 4);
            g.drawString("Aggregation", x + 20, y + 10);

            // Composition
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.fillPolygon(new int[]{x, x + 10, x, x - 10}, new int[]{y + 20, y + 25, y + 30, y + 25}, 4);
            g.drawString("Composition", x + 20, y + 10);
        }
    }

    public static void main(String[] args) {
        new ResizableMoveableArrows();
    }
}
