import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class ClassDiagramUISelectable extends JFrame {
    JPanel pageTitlePanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    JTextArea diagramNotes;

    public ClassDiagramUISelectable() {
        pageTitlePanel = new JPanel();
        canvas = new UMLCanvas();
        diagramNotesPanel = new JPanel();
        bottomPanel=new JPanel();
        samplesPanel = new SamplesPanel(canvas);

        JLabel pageTitle = new JLabel("UML Class Diagram");
        pageTitle.setForeground(Color.white);
        pageTitlePanel.add(pageTitle);
        pageTitlePanel.setBackground(new Color(51, 51, 51));

        diagramNotes=new JTextArea();
        diagramNotes.setSize(150, 250);
        diagramNotes.setLineWrap(true);
        JScrollPane diagramNotesScrollPane = new JScrollPane(diagramNotes);
        diagramNotesPanel.setLayout(new BorderLayout());
        diagramNotesPanel.add(diagramNotesScrollPane);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0,150));
        bottomPanel.add(diagramNotesPanel,BorderLayout.EAST);

        String[] previousText = {""};
        final String[] currentText = {diagramNotes.getText()};
        diagramNotes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    currentText[0] = diagramNotes.getText();
                    //get the new text after enter was pressed
                    String newText;
                    if(previousText[0].isEmpty()) {
                        newText = currentText[0].substring(previousText[0].length());
                    }
                    else{
                        //System.out.println("In here");
                        newText = currentText[0].substring(previousText[0].length()+1);
                    }
                    System.out.println("New text entered: " + newText);
                    if(newText.equals("--")){
                        classDrawPartition(); //STUCK HERE******************************************************************************
                    }
                    // Update previousText to currentText
                    previousText[0] = currentText[0];
                }
            }
        });
        diagramNotes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//                try {
//                    // Get the document
//                    Document doc = e.getDocument();
//                    // Get the new text added to the JTextArea
//                    String newText = doc.getText(e.getOffset(), e.getLength());
//                    System.out.println("New text added: " + newText);
//                } catch (BadLocationException ex) {
//                    ex.printStackTrace();
//                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        // Configure main frame layout
        this.setLayout(new BorderLayout());
        this.add(pageTitlePanel, BorderLayout.NORTH);
        this.add(samplesPanel, BorderLayout.EAST);
        this.add(canvas, BorderLayout.CENTER);
        this.add(bottomPanel,BorderLayout.SOUTH);

        this.setSize(900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    void classDrawPartition(){
        ArrayList<UMLComponent>comps=canvas.components;
        try {
            int startOffset = diagramNotes.getLineStartOffset(0);
            int endOffset = diagramNotes.getLineEndOffset(0);

            //first line aka the name of the class
            String firstLine = diagramNotes.getText(startOffset, endOffset - startOffset).trim();
            for(UMLComponent component:comps){
                if(Objects.equals(component.name, firstLine)){
                    //draw a line
                    component.setDrawPartition(true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Canvas to display draggable, editable, and deletable components
    public class UMLCanvas extends JPanel {
        private ArrayList<UMLComponent> components;
        private UMLComponent selectedComponent;
        private boolean resizing;

        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing=false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    if (selectedComponent != null && (selectedComponent.type!="Class" &&selectedComponent.type!="Comment") && selectedComponent.isInResizeHandle(e.getPoint())) {
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
                        String newName = JOptionPane.showInputDialog("Enter new name:");
                        if (newName != null) {
                            selectedComponent.setName(newName);
                            repaint();
                        }
                    } else if (selectedComponent!=null && (Objects.equals(selectedComponent.type, "Class") || Objects.equals(selectedComponent.type, "Comment"))) {
                        if(Objects.equals(selectedComponent.type, "Class")){
                            //fill the textArea wih this components attributes
                            diagramNotes.setText("");
                            diagramNotes.append(selectedComponent.name);
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
                            if(Objects.equals(selectedComponent.type, "Class") || Objects.equals(selectedComponent.type, "Comment"))
                                selectedComponent.move(e.getPoint());
                            else
                                selectedComponent.moveLine(e.getPoint());
                        }
                        repaint();
                    }
                }
            });
        }

        // Create a new component at a default position
        public void createNewComponent(String type) {
            if(type=="Class" || type=="Comment")
                components.add(new UMLComponent(type, new Point(100, 100)));
            else
                components.add(new UMLComponent(type, new Point(100, 100), new Point(200, 200)));
            repaint();
        }

        // Select a component at a specific point
        private void selectComponentAt(Point point) {
            for (UMLComponent component : components) {
                if ((Objects.equals(component.type, "Class") || Objects.equals(component.type, "Comment"))&& component.contains(point)) {
                    selectedComponent = component;
                    return;
                }
                else if((component.type!="Class" && component.type!="Comment") && component.containsLine(point)){

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
            g.drawRect(x, y, 100, 50);
            g.drawString("Comment...", x + 10, y + 25);
        }
    }

    // UML Component class
    public class UMLComponent {
        private String type;
        private Point position;
        private String name;
        private Point start;
        private Point end;
        private int noOfPartitions;
        private boolean drawPartition;

        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start=null;
            this.end=null;
            noOfPartitions=0;
        }
        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position=null;
            noOfPartitions=0;
        }
        public void move(Point newPoint) {
            position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
        }
        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
        }
        public boolean contains(Point point) {
            return point.x >= position.x && point.x <= position.x + 100
                    && point.y >= position.y && point.y <= position.y + 50;
        }
        public boolean containsLine(Point point) {
            return new Rectangle(start.x - 5, start.y - 5, 10, 10).contains(point) ||
                    new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        public boolean isInResizeHandle(Point point) {
            return new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        public void resize(Point point) {
            end.setLocation(point);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void draw(Graphics g)//, boolean drawPartition) {
        {
            int x = 0, y = 0;
            if (position != null) {
                x = position.x;
                y = position.y;
            }
            g.setColor(Color.BLACK);
            switch (type) {
                case "Class":
                    g.drawRect(x, y, 100, 50);
                    g.drawLine(x, y + 15, x + 100, y + 15);
                    int secondy = position.y + (10 * noOfPartitions);
                    g.drawLine(x, secondy + 15, x + 100, secondy + 15);
                    break;
                case "Association":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Inheritance":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    drawArrowHead(g, start, end, false); // Hollow arrowhead
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    //g.drawPolygon(new int[]{x + 100, x + 105, x + 100}, new int[]{y + 20, y + 25, y + 30}, 3);
                    break;
                case "Aggregation":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    drawDiamond(g, end, false);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    //g.drawPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
                    break;
                case "Composition":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    drawDiamond(g, end, true);
                    g.drawString(name, (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    //g.fillPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
                    break;
                case "Comment":
                    g.drawRect(x, y, 100, 50);
                    g.drawString("", x + 10, y + 25);
                    break;
            }
            if (position != null)
                g.drawString(name, x + 10, y + 10);
            if (drawPartition) {
                noOfPartitions++;
                drawPartition=false;
                repaint();
            }
        }
        public void setDrawPartition(boolean val){
            drawPartition=true;
            repaint();
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

    public static void main(String[] args) {
        new ClassDiagramUISelectable();
    }
}