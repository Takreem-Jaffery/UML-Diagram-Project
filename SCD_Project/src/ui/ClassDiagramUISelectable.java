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


public class ClassDiagramUISelectable extends JFrame {
    JPanel pageTitlePanel;
    JPanel topPanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    JTextArea diagramNotes;
    ArrayList<business.Class> classes;
    ArrayList<business.Association> associations;
    ArrayList<Comment> comments;
    Project project;

    JButton saveImage;
    JButton saveProject;
    JButton loadProject;
    JPanel topButtonPanel;

    public ClassDiagramUISelectable() {

        //business layer objects
        classes = new ArrayList<>();
        associations=new ArrayList<>();
        comments=new ArrayList<>();
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

        JLabel pageTitle = new JLabel("UML Class Diagram");
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
                        ClassDiagramUISelectable.this,
                        "Enter File Name:",
                        "Save Image",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (fileName == null || fileName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(ClassDiagramUISelectable.this,
                            "Project name cannot be empty.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Destination");
                fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG or PNG", "jpeg", "png"));
                fileChooser.setSelectedFile(new File(fileName));

                int userSelection = fileChooser.showSaveDialog(ClassDiagramUISelectable.this);
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
                            project.exportToJPEG(filePath, width, height, ClassDiagramUISelectable.this);
                        } else if (filePath.endsWith(".png")) {
                            project.exportToPNG(filePath,width,height,ClassDiagramUISelectable.this);
                        }
                        JOptionPane.showMessageDialog(ClassDiagramUISelectable.this,
                                "Image saved successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ClassDiagramUISelectable.this,
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
                ArrayList<business.Component> components=project.loadProject(ClassDiagramUISelectable.this);
                if(components==null)
                    return;

                //clear current components
                classes=new ArrayList<business.Class>();
                associations=new ArrayList<business.Association>();
                comments=new ArrayList<Comment>();

                for(int i=0;i<components.size();i++){
                    if(components.get(i).getClassType()=="Class")
                        classes.add((Class)components.get(i));
                    else if(components.get(i).getClassType()=="Association")
                        associations.add((Association) components.get(i));
                    else if(components.get(i).getClassType()=="Comment")
                        comments.add((Comment)components.get(i));
                    else
                        continue;
                        //not a component of uml class diagram
                }
                ArrayList<UMLComponent> comps=new ArrayList<UMLComponent>();
                for(int i=0;i<classes.size();i++) {
                    UMLComponent c = new UMLComponent("Class", classes.get(i).getPosition());
                    c.id=i;
                    c.noOfPartitions=classes.get(i).getNoOfPartitions();
                    c.name=classes.get(i).getName();
                    comps.add(c);
                }
                for(int i=0;i<associations.size();i++){
                    UMLComponent c=new UMLComponent(associations.get(i).getType(),associations.get(i).getStartPoint(),associations.get(i).getEndPoint());
                    c.name=associations.get(i).getName();
                    c.id=i;
                    comps.add(c);
                }
                for(int i=0;i<comments.size();i++){
                    UMLComponent c=new UMLComponent("Comment",comments.get(i).getPosition());
                    c.name=comments.get(i).getCommentText();
                    comps.add(c);
                }
                canvas.components=comps;
                canvas.repaint();
            }
        });

        String[] previousText = {""};
        final String[] currentText = {diagramNotes.getText()};
        diagramNotes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    currentText[0] = diagramNotes.getText();
                    //get the new text after enter was pressed
                    String newText;
                    if (previousText[0].isEmpty()) {
                        newText = currentText[0].substring(previousText[0].length());
                    } else {
                        //System.out.println("In here");
                        newText = currentText[0].substring(previousText[0].length() + 1);
                    }
                    System.out.println("New text entered: " + newText);
                    if (newText.equals("--")) {
                        classDrawPartition();
                    }
                    // Update previousText to currentText
                    previousText[0] = currentText[0];
                }
            }
        });
        diagramNotes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        String currentText = diagramNotes.getText();

                        // Iterate through all lines in the text area
                        String[] lines = currentText.split("\n");
                        ArrayList<String> lineList = new ArrayList<>(java.util.Arrays.asList(lines));

                        // Iterate through canvas components to adjust partitions
                        // Count current number of `--` lines associated with this component
                        int currentPartitions = 0;
                        for (String line : lineList) {
                            if (line.trim().equals("--")) {
                                currentPartitions++;
                            }
                        }
                        classRemovePartition(currentPartitions);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

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

            project.saveProject(classes,associations,comments,ClassDiagramUISelectable.this); //probably send class + comment + association list

        }
    }


    // Save the UML components as XML
//    private void saveAsXml(String filePath, ArrayList<UMLComponent> components) throws IOException {
//        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.writeValue(new File(filePath), components);
//    }

    //UP TO HERE*************************************************************

    void classDrawPartition() {
        ArrayList<UMLComponent> comps = canvas.components;
        try {
            int startOffset = diagramNotes.getLineStartOffset(0);
            int endOffset = diagramNotes.getLineEndOffset(0);

            //first line aka the name of the class
            String firstLine = diagramNotes.getText(startOffset, endOffset - startOffset).trim();
            for (UMLComponent component : comps) {
                if (Objects.equals(component.name, firstLine)) {
                    //draw a line
                    component.setDrawPartition(true);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void classRemovePartition(int currentPartitions) {
        ArrayList<UMLComponent> comps = canvas.components;
        try {
            int startOffset = diagramNotes.getLineStartOffset(0);
            int endOffset = diagramNotes.getLineEndOffset(0);

            //first line aka the name of the class
            String firstLine = diagramNotes.getText(startOffset, endOffset - startOffset).trim();
            for (UMLComponent component : comps) {

                if (Objects.equals(component.name, firstLine)) {
                    //draw a line
                    // Update the number of partitions
                    if (component.noOfPartitions > currentPartitions) {
                        component.noOfPartitions = currentPartitions;
                        classes.get(component.id).setNoOfPartitions(currentPartitions);
                        canvas.repaint();
                    }

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
            resizing = false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    if (selectedComponent != null && (selectedComponent.type != "Class" && selectedComponent.type != "Comment") && selectedComponent.isInResizeHandle(e.getPoint())) {
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
                    } else if (selectedComponent != null && (Objects.equals(selectedComponent.type, "Class") || Objects.equals(selectedComponent.type, "Comment"))) {
                        if (Objects.equals(selectedComponent.type, "Class")) {
                            //fill the textArea wih this components attributes
                            String notes = classes.get(selectedComponent.id).getDiagramNotes();
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
                            if (Objects.equals(selectedComponent.type, "Class") || Objects.equals(selectedComponent.type, "Comment"))
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
            if (type == "Class" || type == "Comment") {
                UMLComponent comp = new UMLComponent(type, new Point(100, 100));
                components.add(comp);
                if (type == "Class") {
                    Class c = new Class();
                    classes.add(c);
                    int i = classes.indexOf(c);
                    comp.id = i;
                }
                else{
                    Comment c=new Comment();
                    c.setPosition(new Point(100,100));
                    comments.add(c);
                    int i=comments.indexOf(c);
                    comp.id=i;
                }
            } else {
                UMLComponent comp=new UMLComponent(type,new Point(100,100),new Point(200,200));
                components.add(comp);
                Association a=new Association(type,type,new Point(100,100), new Point(200,200));
                associations.add(a);
                int i=associations.indexOf(a);
                comp.id=i;

            }
            repaint();
        }

        // Select a component at a specific point
        private void selectComponentAt(Point point) {
            for (UMLComponent component : components) {
                if ((Objects.equals(component.type, "Class") || Objects.equals(component.type, "Comment")) && component.contains(point)) {
                    selectedComponent = component;
                    return;
                } else if ((component.type != "Class" && component.type != "Comment") && component.containsLine(point)) {

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
        public int id;

        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start = null;
            this.end = null;
            noOfPartitions = 0;
        }

        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position = null;
            noOfPartitions = 0;
        }

        public void move(Point newPoint) {
            position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
            if(type=="Class")
                classes.get(id).setPosition(position);
            else
                comments.get(id).setPosition(position);
        }

        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
            associations.get(id).setStartPoint(start);
            associations.get(id).setEndPoint(end);
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
            Association a=associations.get(id);
            a.setEndPoint(end);
        }

        public void setName(String name) {
            this.name = name;
            if(type=="Class") {
                Class c = classes.get(id);
                c.setName(name);
            }
            else if(type=="Comment") {
                Comment c=comments.get(id);
                c.setCommentText(name);
            }else{
                Association a=associations.get(id);
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
            switch (type) {
                case "Class":
                    g.drawRect(x, y, 100, 50);
                    //g.drawLine(x, y + 15, x + 100, y + 15);
                    if (noOfPartitions > 0) {
                        for (int i = 0; i < noOfPartitions; i++) {
                            int secondy = position.y + (10 * i);
                            g.drawLine(x, secondy + 15, x + 100, secondy + 15);
                        }
                    }
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
                drawPartition = false;
                repaint();
            }
        }

        public void setDrawPartition(boolean val) {
            drawPartition = true;
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