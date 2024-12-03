package ui;

import business.*;
import business.Class;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class UseCaseDiagramUISelectable extends JFrame {
    JPanel pageTitlePanel;
    JPanel topPanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    JTextArea diagramNotes;
    ArrayList<business.Usecase> usecases;
    ArrayList<business.Actor> actors;
    ArrayList<UseCaseArrow> arrows;
    Project project;

    JButton saveImage;
    JButton saveProject;
    JButton loadProject;
    JPanel topButtonPanel;

    public UseCaseDiagramUISelectable() {

        //business layer objects
        arrows = new ArrayList<>();
        actors=new ArrayList<>();
        usecases=new ArrayList<>();
        project=new Project();

        saveImage=new JButton("Save Image");
        saveProject=new JButton("Save Project");
        loadProject= new JButton("Load Project");

        pageTitlePanel = new JPanel();
        topPanel=new JPanel();
        canvas = new UMLCanvas();
        diagramNotesPanel = new JPanel();
        bottomPanel = new JPanel();
        samplesPanel = new SamplesPanel(canvas);
        topButtonPanel=new JPanel();

        JLabel pageTitle = new JLabel("UML Use Case Diagram");
        pageTitle.setForeground(Color.white);
        topPanel.setLayout(new BorderLayout());
        pageTitlePanel.add(pageTitle);
        pageTitlePanel.setBackground(new Color(51, 51, 51));

        topButtonPanel.setBackground(new Color(51, 51, 51));
        topButtonPanel.add(saveImage);
        topButtonPanel.add(saveProject);
        topButtonPanel.add(loadProject);
        topPanel.add(topButtonPanel,BorderLayout.WEST);
        topPanel.add(pageTitlePanel,BorderLayout.CENTER);

        diagramNotes = new JTextArea();
        diagramNotes.setSize(150, 250);
        diagramNotes.setLineWrap(true);
        JScrollPane diagramNotesScrollPane = new JScrollPane(diagramNotes);
        diagramNotesPanel.setLayout(new BorderLayout());
        diagramNotesPanel.add(diagramNotesScrollPane);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 150));
        bottomPanel.add(diagramNotesPanel, BorderLayout.EAST);

        saveImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Save as JPG", "Save as PNG", "Cancel"};

                // Show the option dialog
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Choose an option to save the image:",
                        "Save Image",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );


                //prompt user for project name
                String fileName = JOptionPane.showInputDialog(
                        UseCaseDiagramUISelectable.this,
                        "Enter File Name:",
                        "Save Image",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (fileName == null || fileName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(UseCaseDiagramUISelectable.this,
                            "Project name cannot be empty.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Destination");
                fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG or PNG", "jpeg", "png"));
                fileChooser.setSelectedFile(new File(fileName));

                int userSelection = fileChooser.showSaveDialog(UseCaseDiagramUISelectable.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    if (choice == JOptionPane.YES_OPTION && !filePath.endsWith(".jpeg"))
                        filePath += ".jpeg";
                    else if (choice == JOptionPane.NO_OPTION && !filePath.endsWith(".png"))
                        filePath += ".png";
                    else {
                        System.out.println("User cancelled the operation.");
                    }

                    try {
                        float width=canvas.getWidth();
                        float height=canvas.getHeight();
                        if (filePath.endsWith(".jpeg")) {
                            project.exportToJPEG(filePath, width, height, UseCaseDiagramUISelectable.this);
                        } else if (filePath.endsWith(".png")) {
                            project.exportToPNG(filePath,width,height,UseCaseDiagramUISelectable.this);
                        }
                        JOptionPane.showMessageDialog(UseCaseDiagramUISelectable.this,
                                "Image saved successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(UseCaseDiagramUISelectable.this,
                                "Failed to save the image: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        saveProject.addActionListener(new SaveProjectActionListener());

        loadProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<business.Component> components=project.loadProject(UseCaseDiagramUISelectable.this);
                if(components==null)
                    return;

                //clear current components
                usecases=new ArrayList<Usecase>();
                actors=new ArrayList<Actor>();
                arrows=new ArrayList<UseCaseArrow>();

                for(int i=0;i<components.size();i++){
                    if(components.get(i).getClassType()=="UseCase")
                        usecases.add((Usecase) components.get(i));
                    else if(components.get(i).getClassType()=="Arrow")
                        arrows.add((UseCaseArrow) components.get(i));
                    else if(components.get(i).getClassType()=="Actor")
                        actors.add((Actor)components.get(i));
                    else
                        continue;
                    //not a component of uml class diagram
                }
                ArrayList<UMLComponent> comps=new ArrayList<UMLComponent>();
                for(int i=0;i<usecases.size();i++) {
                    UMLComponent c = new UMLComponent("Usecase", usecases.get(i).getPosition());
                    c.id=i;
                    c.name=usecases.get(i).getName();
                    comps.add(c);
                }
                for(int i=0;i<arrows.size();i++){
                    UMLComponent c=new UMLComponent(arrows.get(i).getType(),arrows.get(i).getStartPoint(),arrows.get(i).getEndPoint());
                    c.name=arrows.get(i).getName();
                    c.id=i;
                    comps.add(c);
                }
                for(int i=0;i<actors.size();i++){
                    UMLComponent c=new UMLComponent("Actor",actors.get(i).getPosition());
                    c.name=actors.get(i).getName();
                    comps.add(c);
                }
                canvas.components=comps;
                canvas.repaint();
            }
        });

        //Abandon textArea implementation...

//        String[] previousText = {""};
//        final String[] currentText = {diagramNotes.getText()};
//        diagramNotes.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    currentText[0] = diagramNotes.getText();
//                    //get the new text after enter was pressed
//                    String newText;
//                    if (previousText[0].isEmpty()) {
//                        newText = currentText[0].substring(previousText[0].length());
//                    } else {
//                        //System.out.println("In here");
//                        newText = currentText[0].substring(previousText[0].length() + 1);
//                    }
//                    System.out.println("New text entered: " + newText);
//                    if (newText.equals("--")) {
//                        classDrawPartition();
//                    }
//                    // Update previousText to currentText
//                    previousText[0] = currentText[0];
//                }
//            }
//        });
//        diagramNotes.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                SwingUtilities.invokeLater(() -> {
//                    try {
//                        String currentText = diagramNotes.getText();
//
//                        // Iterate through all lines in the text area
//                        String[] lines = currentText.split("\n");
//                        ArrayList<String> lineList = new ArrayList<>(java.util.Arrays.asList(lines));
//
//                        // Iterate through canvas components to adjust partitions
//                        // Count current number of `--` lines associated with this component
//                        int currentPartitions = 0;
//                        for (String line : lineList) {
//                            if (line.trim().equals("--")) {
//                                currentPartitions++;
//                            }
//                        }
//                        classRemovePartition(currentPartitions);
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                });
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });

        // Configure main frame layout
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(samplesPanel, BorderLayout.EAST);
        this.add(canvas, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setSize(900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //used to save image in project class
    public BufferedImage exportToBufferedImage(float width, float height) {
        // Create a buffered image of the diagram's exact size
        BufferedImage image = new BufferedImage((int) width, (int) height,
                BufferedImage.TYPE_INT_RGB);

        // Create graphics context
        Graphics2D g2d = image.createGraphics();

        // Optional: Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        // Paint the diagram onto the buffered image
        paint(g2d);

        // Dispose of graphics context
        g2d.dispose();

        return image;
    }
    private class SaveProjectActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            project.saveUCDProject(usecases,actors,arrows,UseCaseDiagramUISelectable.this);
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
            resizing = false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    if (selectedComponent != null && (selectedComponent.type != "Usecase" && selectedComponent.type != "Actor") && selectedComponent.isInResizeHandle(e.getPoint())) {
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
                            if(selectedComponent.type=="Usecase")
                                usecases.remove(selectedComponent.id);
                            else if(selectedComponent.type=="Actor")
                                actors.remove(selectedComponent.id);
                            else
                                arrows.remove(selectedComponent.id);
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
                    else if (selectedComponent != null && (Objects.equals(selectedComponent.type, "Usecase") || Objects.equals(selectedComponent.type, "Actor"))) {
                        if (Objects.equals(selectedComponent.type, "Usecase")) {
                            //fill the textArea wih this components attributes
                            String notes = usecases.get(selectedComponent.id).getDiagramNotes();
                            diagramNotes.setText("");
                            diagramNotes.append(notes);
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
                            if (Objects.equals(selectedComponent.type, "Usecase") || Objects.equals(selectedComponent.type, "Actor"))
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
            if (type == "Usecase" || type == "Actor") {
                UMLComponent comp = new UMLComponent(type, new Point(100, 100));
                components.add(comp);
                if (type == "Usecase") {
                    Usecase c = new Usecase();
                    usecases.add(c);
                    int i = usecases.indexOf(c);
                    comp.id = i;
                }
                else{
                    Actor c=new Actor();
                    c.setPosition(new Point(100,100));
                    actors.add(c);
                    int i=actors.indexOf(c);
                    comp.id=i;
                }
            } else {
                UMLComponent comp=new UMLComponent(type,new Point(100,100),new Point(200,200));
                components.add(comp);
                UseCaseArrow a=new UseCaseArrow(type,type,new Point(100,100), new Point(200,200));
                arrows.add(a);
                int i=arrows.indexOf(a);
                comp.id=i;

            }
            repaint();
        }

        // Select a component at a specific point
        private void selectComponentAt(Point point) {
            for (UMLComponent component : components) {
                if ((Objects.equals(component.type, "Usecase") || Objects.equals(component.type, "Actor")) && component.contains(point)) {
                    selectedComponent = component;
                    return;
                } else if ((component.type != "Usecase" && component.type != "Actor") && component.containsLine(point)) {

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
            if (y < 60) return "Usecase";
            else if (y < 180) return "Actor";
            else if (y < 230) return "Arrow";
            else if (y < 280) return "Include";
            else if (y < 330) return "Extend";
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSamples(g);
        }

        // Draw static samples
        private void drawSamples(Graphics g) {
            int x = 20, y = 10;
            g.setColor(Color.BLACK);

            // Usecase bubble
            g.drawOval(x, y, 100, 50);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth("Usecase");
            int textHeight = fm.getHeight();
            g.drawString("Usecase", (100 - textWidth) / 2+10, (50 + textHeight / 2) / 2+10);
            //g.drawString("Usecase", x + 35, y + 10);

            // Actor
            y+=60;
            x+=50;
            g.drawOval(x - 15, y, 30, 30);  // Head
            g.drawLine(x, y+30, x, y+60);   // Body
            g.drawLine(x - 20, y+45, x + 20, y+45);  // Arms
            g.drawLine(x, y+60, x - 20, y+90);  // Left leg
            g.drawLine(x, y+60, x + 20, y+90);  // Right leg
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth("Actor");
            g.drawString("Actor", x - textWidth / 2, y+110);
//
//            g.drawLine(x, y + 25, x + 100, y + 25);
//            g.drawString("Association", x + 35, y + 10);

            // Arrow
            x-=50;
            y += 120;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            // Include
            y += 50;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            fm = g2d.getFontMetrics();
            textWidth = fm.stringWidth("include");
            g.drawString("<<include>>", (getWidth() - textWidth) / 2, y - 5);

            // Extend
            y += 50;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            fm = g2d.getFontMetrics();
            textWidth = fm.stringWidth("extend");
            g.drawString("<<extend>>", (getWidth() - textWidth) / 2, y - 5);

        }
    }

    // UML Component class
    public class UMLComponent {
        private String type;
        private Point position;
        private String name;
        private Point start;
        private Point end;
        public int id;

        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start = null;
            this.end = null;
        }

        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position = null;
        }

        public void move(Point newPoint) {
            if(type=="Usecase")
                position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
            else
                position=new Point(newPoint.x,newPoint.y-50);
            if(type=="Usecase")
                usecases.get(id).setPosition(position);
            else
                actors.get(id).setPosition(position);
        }

        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
            arrows.get(id).setStartPoint(start);
            arrows.get(id).setEndPoint(end);
        }

        public boolean contains(Point point) {
            if(type=="Usecase")
                return point.x >= position.x && point.x <= position.x + 100
                    && point.y >= position.y && point.y <= position.y + 50;
            else
                return point.x >= position.x-20 && point.x <= position.x + 20
                        && point.y >= position.y && point.y <= position.y + 110;
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
            UseCaseArrow a=arrows.get(id);
            a.setEndPoint(end);
        }

        public void setName(String name) {
            this.name = name;
            if(type=="Usecase") {
                Usecase c = usecases.get(id);
                c.setName(name);
            }
            else if(type=="Actor") {
                Actor c=actors.get(id);
                c.setName(name);
            }else{
                UseCaseArrow a=arrows.get(id);
                a.setName(name);
            }
        }

        public void draw(Graphics g)//, boolean drawPartition) {
        {
            int x = 0, y = 0;
            if (position != null) {
                x = position.x;
                y = position.y;
            }
            g.setColor(Color.BLACK);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(name);
            int textHeight = fm.getHeight();
            Graphics2D g2d = (Graphics2D) g;
            if(type=="Arrow"||type=="Include"||type=="Extend") {
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            }switch (type) {
                case "Usecase":
                    g2d.setStroke(new BasicStroke());
                    g.drawOval(x, y, 100, 50);
                    g.drawString(name, x+20, y+30);
                    break;
                case "Actor":
                    g2d.setStroke(new BasicStroke());
                    g.drawOval(x - 15, y, 30, 30);  // Head
                    g.drawLine(x, y+30, x, y+60);   // Body
                    g.drawLine(x - 20, y+45, x + 20, y+45);  // Arms
                    g.drawLine(x, y+60, x - 20, y+90);  // Left leg
                    g.drawLine(x, y+60, x + 20, y+90);  // Right leg
                    g.drawString(name, x - textWidth / 2, y+110);
                    break;
                case "Arrow":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g2d.setStroke(new BasicStroke());
                    drawArrowHead(g,start,end);
                    break;
                case "Include":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g2d.setStroke(new BasicStroke());
                    drawArrowHead(g,start,end);
                    fm = g2d.getFontMetrics();
                    textWidth = fm.stringWidth("include");
                    g.drawString("<<include>>", (start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
                case "Extend":
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g2d.setStroke(new BasicStroke());
                    drawArrowHead(g,start,end);

                    fm = g2d.getFontMetrics();
                    textWidth = fm.stringWidth("extend");
                    g.drawString("<<extend>>",(start.x + end.x) / 2, (start.y + end.y) / 2 - 5);
                    break;
            }

        }

        private void drawArrowHead(Graphics g, Point start, Point end) {
            int dx = end.x - start.x;
            int dy = end.y - start.y;
            double angle = Math.atan2(dy, dx);
            int arrowLength = 15;
            int arrowWidth = 7;

            int x1 = end.x - (int) (arrowLength * Math.cos(angle - Math.PI / 6));
            int y1 = end.y - (int) (arrowLength * Math.sin(angle - Math.PI / 6));
            int x2 = end.x - (int) (arrowLength * Math.cos(angle + Math.PI / 6));
            int y2 = end.y - (int) (arrowLength * Math.sin(angle + Math.PI / 6));

            g.drawLine(x1, y1, end.x,end.y);
            g.drawLine(x2, y2, end.x,end.y);

        }
    }

    public static void main(String[] args) {
        new UseCaseDiagramUISelectable();
    }
}