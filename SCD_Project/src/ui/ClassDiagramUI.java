package ui;

import business.Association;
import business.Class;
import business.Comment;
import business.Project;

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


public class ClassDiagramUI extends JFrame {
    JPanel pageTitlePanel;
    JPanel topPanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;
    JScrollPane canvasScrollPanel;
    JPanel rightPanel;

    JTextArea diagramNotes;
    ArrayList<business.Class> classes;
    ArrayList<business.Association> associations;
    ArrayList<Comment> comments;
    Project project;

    JButton saveImage;
    JButton saveProject;
    JButton loadProject;
    JButton generateCode;
    JButton toUCDButton;
    JPanel topButtonPanel;
    JPanel topRightButtonPanel;
    JLabel mouseCoords;
    JPanel bottomCoords;

    public ClassDiagramUI() {

        //business layer objects
        classes = new ArrayList<>();
        associations=new ArrayList<>();
        comments=new ArrayList<>();
        project=new Project();


        saveImage=new JButton("\uD83D\uDDBC\uFE0F Save Image");
        saveProject=new JButton("\uD83D\uDCBE Save Project");
        loadProject= new JButton("⏫ Load Project");
        generateCode=new JButton("\uD83D\uDC68\u200D\uD83D\uDCBB Generate Code");
        toUCDButton=new JButton("Use Case Diagram ➡\uFE0F");

        pageTitlePanel = new JPanel();
        topPanel=new JPanel();
        canvas = new UMLCanvas();
        canvas.setPreferredSize(new Dimension(2000, 2000));
        canvasScrollPanel=new JScrollPane(canvas);
        canvasScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        canvasScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        diagramNotesPanel = new JPanel();
        bottomPanel = new JPanel();
        samplesPanel = new SamplesPanel(canvas);
        samplesPanel.setBackground(Color.white);
        topButtonPanel=new JPanel();
        topRightButtonPanel=new JPanel();
        mouseCoords=new JLabel();
        mouseCoords.setBackground(Color.white);
        bottomCoords=new JPanel();
        bottomCoords.add(mouseCoords);
        bottomCoords.setBackground(Color.white);
        rightPanel=new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(samplesPanel,BorderLayout.CENTER);
        rightPanel.add(bottomCoords,BorderLayout.SOUTH);
        rightPanel.setBackground(Color.white);

        JLabel pageTitle = new JLabel("UML Class Diagram");
        pageTitle.setForeground(Color.white);
        pageTitle.setBorder(BorderFactory.createEmptyBorder(0,0,0,300));
        topPanel.setLayout(new BorderLayout());
        pageTitlePanel.add(pageTitle);
        pageTitlePanel.setBackground(new Color(51, 51, 51));

        topButtonPanel.setBackground(new Color(51, 51, 51));
        topRightButtonPanel.setBackground(new Color(51,51,51));
        topButtonPanel.add(saveImage);
        topButtonPanel.add(saveProject);
        topButtonPanel.add(loadProject);
        topButtonPanel.add(generateCode);
        topRightButtonPanel.add(toUCDButton);
        topPanel.add(topButtonPanel,BorderLayout.WEST);
        topPanel.add(pageTitlePanel,BorderLayout.CENTER);
        topPanel.add(topRightButtonPanel,BorderLayout.EAST);

        diagramNotes = new JTextArea();
        diagramNotes.setSize(150, 250);
        diagramNotes.setLineWrap(true);
        JScrollPane diagramNotesScrollPane = new JScrollPane(diagramNotes);
        diagramNotesPanel.setLayout(new BorderLayout());
        diagramNotesPanel.add(diagramNotesScrollPane);

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 150));
        bottomPanel.add(diagramNotesPanel, BorderLayout.EAST);

        JFrame frame=this;
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
                        ClassDiagramUI.this,
                        "Enter File Name:",
                        "Save Image",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (fileName == null || fileName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(ClassDiagramUI.this,
                            "Project name cannot be empty.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Destination");
                fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG or PNG", "jpeg", "png"));
                fileChooser.setSelectedFile(new File(fileName));

                int userSelection = fileChooser.showSaveDialog(ClassDiagramUI.this);
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
                        float width=frame.getWidth();
                        float height=frame.getHeight();
                        if (filePath.endsWith(".jpeg")) {
                            project.exportToJPEG(filePath, width, height, ClassDiagramUI.this);
                        } else if (filePath.endsWith(".png")) {
                            project.exportToPNG(filePath,width,height,ClassDiagramUI.this);
                        }
                        JOptionPane.showMessageDialog(ClassDiagramUI.this,
                                "Image saved successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(ClassDiagramUI.this,
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
                ArrayList<business.Component> components=project.loadProject(ClassDiagramUI.this);
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
                    c.attributes=classes.get(i).getAttributes();
                    c.methods=classes.get(i).getMethods();
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
        toUCDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!canvas.components.isEmpty()){
                    int choice = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to save the project before moving to Use Case Diagram?",
                            "Save Project",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        project.saveCDProject(classes,associations,comments,ClassDiagramUI.this); //probably send class + comment + association list
                        frame.dispose();
                    } else if (choice == JOptionPane.NO_OPTION) {
                        frame.dispose();
                    }
                }
                frame.dispose();
                new UseCaseDiagramUI();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseCoords.setText("("+e.getX()+","+e.getY()+")");
                repaint();
                revalidate();
            }
        });
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
        this.add(rightPanel, BorderLayout.EAST);
        this.add(canvasScrollPanel, BorderLayout.CENTER);
        //this.add(bottomPanel, BorderLayout.SOUTH);

        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent automatic closing
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!canvas.components.isEmpty()) {
                    int choice = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to save the project before exiting?",
                            "Save Project",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        project.saveCDProject(classes,associations,comments,ClassDiagramUI.this);
                        frame.dispose();
                    } else if (choice == JOptionPane.NO_OPTION) {
                        frame.dispose();
                    }
                }
                else frame.dispose();
            }
        });
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

            project.saveCDProject(classes,associations,comments,ClassDiagramUI.this); //probably send class + comment + association list

        }
    }


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
        private final int maxWidth = 2000;
        private final int maxHeight = 2000;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(maxWidth, maxHeight);
        }
        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing = false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());

                    if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null && (selectedComponent.type=="Class" ||selectedComponent.type=="Comment")) {
                        showContextMenu(e);
                    }

                    if (selectedComponent != null && (selectedComponent.type != "Class" && selectedComponent.type != "Comment") && selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    resizing = false;
                    if(selectedComponent!=null) {
                        if (selectedComponent.type != "Class" && selectedComponent.type != "Comment") {
                            if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null) {
                                int response = JOptionPane.showConfirmDialog(
                                        UMLCanvas.this,
                                        "Do you want to delete this component?",
                                        "Delete Component",
                                        JOptionPane.OK_CANCEL_OPTION
                                );
                                if (response == JOptionPane.OK_OPTION) {
                                    components.remove(selectedComponent);
                                    associations.remove(selectedComponent.id);
                                    selectedComponent = null;
                                    repaint();
                                }
                            }
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
        private void showContextMenu(MouseEvent e) {
            JPopupMenu menu = new JPopupMenu();

            // Simple Edit option (no submenu) for "Comment" type
            if (Objects.equals(selectedComponent.getType(), "Comment")) {
                JMenuItem editComment = new JMenuItem("Edit");
                editComment.addActionListener(ev -> {
                    String newContent = JOptionPane.showInputDialog("Edit comment text:");
                    if (newContent != null) {
                        selectedComponent.setCommentText(newContent); // Assuming setCommentText exists for comments
                        repaint();
                    }
                });
                menu.add(editComment);
            } else {
                // Edit menu for Class and other non-Comment types
                JMenu editMenu = new JMenu("Edit");
                JMenuItem addAttribute = new JMenuItem("Add Attribute");
                JMenuItem addMethod = new JMenuItem("Add Method");
                JMenuItem makeInterface = new JMenuItem("Make Interface");
                JMenuItem makeAbstract = new JMenuItem("Make Abstract");

                addAttribute.addActionListener(ev -> {
                    String attribute = JOptionPane.showInputDialog("Enter attribute (e.g., +attribute1):");
                    if (attribute != null && selectedComponent != null) {
                        selectedComponent.addAttribute(attribute);
                        repaint();
                    }
                });

                addMethod.addActionListener(ev -> {
                    String method = JOptionPane.showInputDialog("Enter method (e.g., +operation1()):");
                    if (method != null && selectedComponent != null) {
                        selectedComponent.addMethod(method);
                        repaint();
                    }
                });

                makeInterface.addActionListener(ev -> {
                    selectedComponent.makeInterface();
                    repaint();
                });

                makeAbstract.addActionListener(ev -> {
                    selectedComponent.makeAbstract();
                    repaint();
                });

                editMenu.add(addAttribute);
                editMenu.add(addMethod);
                editMenu.add(makeInterface);
                editMenu.add(makeAbstract);
                menu.add(editMenu);
            }

            // Rename option
            JMenuItem rename = new JMenuItem("Rename");
            rename.addActionListener(ev -> {
                String newName = JOptionPane.showInputDialog("Enter new name:");
                if (newName != null) {
                    selectedComponent.setName(newName);
                    repaint();
                }
            });

            // Delete option
            JMenuItem delete = new JMenuItem("Delete");
            delete.addActionListener(ev -> {
                int response = JOptionPane.showConfirmDialog(
                        UMLCanvas.this,
                        "Do you want to delete this component?",
                        "Delete Component",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (response == JOptionPane.OK_OPTION) {
                    components.remove(selectedComponent);
                    if(selectedComponent.type=="Class")
                        classes.remove(selectedComponent.id);
                    else
                        comments.remove(selectedComponent.id);
                    selectedComponent = null;
                    repaint();
                }
            });

            menu.add(rename);
            menu.add(delete);

            // Display the menu
            menu.show(this, e.getX(), e.getY());
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

//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            for (UMLComponent component : components) {
//                component.draw(g);
//            }
//        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Define the fixed grid cell size
            final int cellSize = 20; // Adjust this to set the size of each grid cell

            // Get the dimensions of the canvas
            int canvasWidth = getWidth();
            int canvasHeight = getHeight();

            // Draw the grid
            g2d.setColor(Color.LIGHT_GRAY); // Set the grid line color
            for (int x = 0; x <= canvasWidth; x += cellSize) {
                g2d.drawLine(x, 0, x, canvasHeight); // Vertical lines
            }
            for (int y = 0; y <= canvasHeight; y += cellSize) {
                g2d.drawLine(0, y, canvasWidth, y); // Horizontal lines
            }

            // Dispose of the graphics object
            g2d.dispose();

            // Draw UML components on top of the grid
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
            //g.drawRect(x, y, 100, 50);
            //g.drawString("Comment...", x + 10, y + 25);

            int width=100, height=50;
            String commentText="Comment...";
            int[] commentX={x,width-5,width,width,x};
            int[] commentY={y,y,y+5,y+height,y+height};

            int[] triangleX={width-5,width-5,width};
            int[] triangleY={y,y+5,y+5};
            g.drawPolygon(commentX,commentY,5);
            g.drawPolygon(triangleX,triangleY,3);
            g.drawString(commentText,x+10,y+25);
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
        private ArrayList<String> attributes;
        private ArrayList<String> methods;
        public int id;
        private int maxWidth;
        private int maxHeight;
        private String text;

        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start = null;
            this.end = null;
            noOfPartitions = 0;
            this.attributes = new ArrayList<>();
            this.methods = new ArrayList<>();
            maxHeight=50;
            maxWidth=100;
            text="";
        }

        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position = null;
            noOfPartitions = 0;
        }
        public String getType() {
            return type;
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
            return point.x >= position.x && point.x <= position.x + maxWidth
                    && point.y >= position.y && point.y <= position.y + maxHeight;
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
        public int calcMaxWidth(Graphics g){
            // Determine the required width of the class box based on the longest text
            int maxTextWidth = g.getFontMetrics().stringWidth(name); // Start with the name width

            // Calculate the maximum width among attributes
            for (String attribute : attributes) {
                maxTextWidth = Math.max(maxTextWidth, g.getFontMetrics().stringWidth(attribute));
            }

            // Calculate the maximum width among methods
            for (String method : methods) {
                maxTextWidth = Math.max(maxTextWidth, g.getFontMetrics().stringWidth(method));
            }
            int max=Math.max(100, maxTextWidth + 10); // Minimum width is 100
            maxWidth=max;
            return max;
        }
        public int calcMaxHeight(Graphics g){
            int max=50 + attributes.size() * 15 + methods.size() * 15;
            maxHeight=max;
            return max;
        }
        public void addAttribute(String attribute) {
            attributes.add(attribute);
            classes.get(id).addAttribute(attribute);
        }

        public void addMethod(String method) {
            methods.add(method);
            classes.get(id).addMethod(method);
        }

        public void makeInterface() {
            name = "<<interface>> " + name;
            classes.get(id).setName(name);
        }

        public void makeAbstract() {
            name = "<html><i>" + name + "</i></html>";
            classes.get(id).setName(name);
        }

        public void setCommentText(String newContent) {
            if (!"Comment".equals(type)) {
                throw new UnsupportedOperationException("setCommentText can only be used for 'Comment' type components.");
            }
            if (newContent == null || newContent.trim().isEmpty()) {
                throw new IllegalArgumentException("Comment content cannot be null or empty.");
            }
            this.text = newContent;
            System.out.println("Comment updated to: " + this.text);
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
                    int boxWidth = calcMaxWidth(g);
                    int totalHeight = calcMaxHeight(g);

                    // Draw the class box
                    g.drawRect(x, y, boxWidth, totalHeight);

                    // Draw the name section
                    g.drawLine(x, y + 20, x + boxWidth, y + 20);

                    // Draw the attributes section
                    if (!attributes.isEmpty()) {
                        g.drawLine(x, y + 30 + attributes.size() * 15, x + boxWidth, y + 30 + attributes.size() * 15);
                        int attrY = y + 35;
                        for (String attribute : attributes) {
                            g.drawString(attribute, x + 5, attrY);
                            attrY += 15;
                        }
                    }

                    // Draw the methods section
                    if (!methods.isEmpty()) {
                        int methodY = y + 45 + attributes.size() * 15;
                        for (String method : methods) {
                            g.drawString(method, x + 5, methodY);
                            methodY += 15;
                        }
                    }

                    // Draw partitions (if any)
                    if (noOfPartitions > 0) {
                        for (int i = 0; i < noOfPartitions; i++) {
                            int partitionY = y + (10 * (i + 1)); // Adjust the Y position for each partition
                            g.drawLine(x, partitionY + 15, x + boxWidth, partitionY + 15);
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
                    //g.drawRect(x, y, 100, 50);
                    //g.drawString("", x + 10, y + 25);
                    int width=100,height=50;
                    if(!this.text.isEmpty()){
                        Graphics2D g2d =(Graphics2D) g;
                        FontMetrics fontMetrics = g2d.getFontMetrics();
                        width = 20+fontMetrics.stringWidth(text);
                    }
                    int[] commentX={x,x+width-5,x+width,x+width,x};
                    int[] commentY={y,y,y+5,y+height,y+height};

                    int[] triangleX={x+width-5,x+width-5,x+width};
                    int[] triangleY={y,y+5,y+5};
                    g.drawPolygon(commentX,commentY,5);
                    g.drawPolygon(triangleX,triangleY,3);
                    g.drawString(this.text,x+10,y+25);
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
        new ClassDiagramUI();
    }
}