import org.example.ResizeableClassAndCommentUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClassDiagramUISelectable extends JFrame {
    JPanel pageTitlePanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    public ClassDiagramUISelectable() {
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

    // Canvas to display draggable, editable, and deletable components
    public class UMLCanvas extends JPanel {
        private ArrayList<UMLComponent> components;
        private UMLComponent selectedComponent;
        private boolean resizingLine;
        private boolean resizingRect;
        private boolean dragging;
        private int offsetX;
        private int offsetY;

        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizingRect=false;
            resizingLine=false;
            dragging=false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point point = e.getPoint();
                    selectComponentAt(point);
                    if (selectedComponent != null) {
                        if (selectedComponent.isInResizeHandle(point)) {
                            resizingLine = true;
                        } else if (selectedComponent.isOnResizeEdge(point)) {
                            resizingRect = true;
                        } else {
                            dragging = true;
                            offsetX = point.x - selectedComponent.start.x;
                            offsetY = point.y - selectedComponent.start.y;
                        }
                    }
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point point = e.getPoint();

                    if (resizingRect && selectedComponent != null) {
                        selectedComponent.resizeRect(point);
                        repaint();
                    }else if (resizingLine && selectedComponent != null) {

                        selectedComponent.resizeLine(point);
                        repaint();
                    }
                    else if (dragging && selectedComponent != null) {
                        selectedComponent.start.x = point.x - offsetX;
                        selectedComponent.start.y = point.y - offsetY;

                        selectedComponent.end.x = selectedComponent.start.x + 100; // width
                        selectedComponent.end.y = selectedComponent.start.y + 50; // height

                        repaint();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    resizingLine = false;
                    resizingRect=false;
                    dragging=false;
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
                        if (resizingLine) {
                            selectedComponent.resizeLine(e.getPoint());
                        } else if(resizingRect) {
                            selectedComponent.resizeRect(e.getPoint());
                        }
                        else{
                            selectedComponent.move(e.getPoint());
                        }

                        repaint();
                    }
                }
            });
        }

        // Create a new component at a default position
        public void createNewComponent(String type) {
            components.add(new UMLComponent(type, new Point(100, 100),new Point(200, 200)));
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
            int width=100, height=50;
            int[] commentX={x,x+width,x+width+5,x+width+5,x};
            int[] commentY={y,y,y+5,y+height,y+height};

            int[] triangleX={x+width,x+width+5,x+width};
            int[] triangleY={y,y+5,y+5};
            g.drawPolygon(commentX,commentY,5);
            g.drawPolygon(triangleX,triangleY,3);
            g.drawString("Comment...", x + 10, y + 25);
        }
    }

    // UML Component class
    public class UMLComponent {
        private String type;
        private Point start;
        private Point end;
        private String name;
        private int width;
        private int height;
        private boolean resizing;

        public UMLComponent(String type, Point position, Point position2) {
            this.type = type;
            this.start = position;
            this.end=position2;
            this.name = type;
            this.width = 100;
            this.height = 50;
            this.resizing = false;
        }

        public void move(Point newPoint) {
            if(!resizing) {
                int dx = newPoint.x - start.x;
                int dy = newPoint.y - start.y;
                start.translate(dx, dy);
                end.translate(dx, dy);
            }
        }

        public boolean contains(Point point) {
            return point.x >= start.x && point.x <= start.x + width
                    && point.y >= start.y && point.y <= start.y + height;
        }
        public boolean isInResizeHandle(Point point) { //for lines
            return new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        public boolean isOnResizeEdge(Point point) { //for class and comments
            int margin = 10; // Area near the edges for resizing
            return (point.x >= start.x + width - margin && point.x <= start.x + width
                    && point.y >= start.y && point.y <= start.y + height) ||
                    (point.y >= start.y + height - margin && point.y <= start.y + height
                            && point.x >= start.x && point.x <= start.x + width);
        }
        public void resizeLine(Point point) {
            end.setLocation(point);
        }
        public void resizeRect(Point point){
            width = Math.max(50, point.x - start.x);
            height = Math.max(30, point.y - start.y);
            end.setLocation(start.x + width, start.y + height);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void draw(Graphics g) {
            int x = start.x, y = start.y;
            int endx=end.x,endy=end.y;
            g.setColor(Color.BLACK);
            Font font=new Font("Arial",Font.PLAIN,12);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            switch (type) {
                case "Class":
                    g.drawRect(x, y, 100, 50);
                    g.drawLine(x, y + 15, x + 100, y + 15);
                    g.drawString(name, x + 20, y + 10);
                    break;
                case "Association":
                    g.drawLine(x, y + 25, endx, endy);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Inheritance":
                    g.drawLine(x, y + 25, endx, endy);
                    //g.drawPolygon(new int[]{x + 100, x + 105, x + 100}, new int[]{y + 20, y + 25, y + 30}, 3);
                    drawArrowHead(g,start,end,false);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Aggregation":
                    g.drawLine(x, y + 25, endx, endy);
                    // g.drawPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
                    drawDiamond(g,end,false);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Composition":
                    g.drawLine(x, y + 25, endx, endy);
                    //g.fillPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
                    drawDiamond(g,end,true);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Comment":
                    int width=metrics.stringWidth(name)+50, height=50;

                    int[] commentX={x,x+width,x+width+5,x+width+5,x};
                    int[] commentY={y,y,y+5,y+height,y+height};

                    int[] triangleX={x+width,x+width+5,x+width};
                    int[] triangleY={y,y+5,y+5};
                    g.drawPolygon(commentX,commentY,5);
                    g.drawPolygon(triangleX,triangleY,3);
                    g.drawString(name, x + 20, y + 10);
                    break;
                default:
                    // Non-resizable components
                    g.drawRect(x, y, 100, 50);
                    break;
            }
        }
        private void drawArrowHead(Graphics g, Point start, Point end, boolean filled) {
            int dx = end.x - start.x;
            int dy = end.y - start.y;
            double angle = Math.atan2(dy, dx);
            int arrowLength = 15;

            int x1 = end.x - (int) (arrowLength * Math.cos(angle - Math.PI / 6));
            int y1 = end.y - (int) (arrowLength * Math.sin(angle - Math.PI / 6));
            int x2 = end.x - (int) (arrowLength * Math.cos(angle + Math.PI / 6));
            int y2 = end.y - (int) (arrowLength * Math.sin(angle + Math.PI / 6));

            g.drawPolygon(new int[]{end.x, x1, x2}, new int[]{end.y, y1, y2}, 3); // Hollow arrowhead
        }
        private void drawDiamond(Graphics g, Point end, boolean filled) {
            int diamondSize = 10;
            int[] xPoints = {end.x, end.x + diamondSize / 2, end.x+diamondSize, end.x + diamondSize / 2};
            int[] yPoints = {end.y, end.y+diamondSize/2, end.y, end.y-diamondSize/2};

            if (filled) {
                g.fillPolygon(xPoints, yPoints, 4);
            } else {
                g.drawPolygon(xPoints, yPoints, 4);
            }
        }
    }

    public static void main(String[] args) {
        new ClassDiagramUISelectable();
    }
}
