package ui;

import business.Association;
import business.Class;
import business.Comment;
import business.Project;
import business.CodeGenerator;
import business.*;

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

/**
 * The {@code ClassDiagramUI} class represents the user interface for creating and managing UML Class Diagrams.
 * It provides functionality for:
 * <ul>
 *     <li>Drawing UML class diagrams</li>
 *     <li>Saving and loading projects</li>
 *     <li>Exporting diagrams as images</li>
 *     <li>Generating Java code from UML diagrams</li>
 *     <li>Switching between Class Diagrams and Use Case Diagrams</li>
 * </ul>
 * This class extends {@link JFrame} and includes various UI components and event listeners to facilitate user interactions.
 */
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

    /**
     * Constructs a new {@code ClassDiagramUI} instance and initializes the UI components and event listeners.
     */
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
                            project.exportToPNG(filePath,width,height, ClassDiagramUI.this);
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
                    String state=classes.get(i).getState();
                    if(Objects.equals(state, "Abstract"))
                        c.isAbstract=true;
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
                    c.text=comments.get(i).getCommentText();
                    c.name=comments.get(i).getName();
                    comps.add(c);
                }
                canvas.components=comps;
                canvas.repaint();
            }
        });
        JFrame ui=this;
        generateCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //String currentDirectory = UCDSystem.getProperty("user.dir");
                    //UCDSystem.out.println("Current working directory: " + currentDirectory);

                    //get user to select json file to convert
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select Project File to Load");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

                    String filePath="";
                    int userSelection = fileChooser.showOpenDialog(ui);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        filePath = file.getAbsolutePath();

                        // Validate file extension
                        if (!filePath.endsWith(".json")) {
                            JOptionPane.showMessageDialog(ui,
                                    "Invalid file type selected. Please select a JSON file.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    }

                    //get user to enter the name and file directory for output file
                    //prompt user for file name
                    String fileName = JOptionPane.showInputDialog(
                            ClassDiagramUI.this,
                            "Enter Java Output File Name:",
                            "Generate Code",
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (fileName == null || fileName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(ClassDiagramUI.this,
                                "File name cannot be empty.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select Destination");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("JAVA", "java"));
                    fileChooser.setSelectedFile(new File(fileName));

                    userSelection = fileChooser.showSaveDialog(ClassDiagramUI.this);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String filePathOutput = file.getAbsolutePath();
                        if (!filePathOutput.endsWith(".java"))
                            filePathOutput += ".java";

                        CodeGenerator converter = new CodeGenerator();
                        converter.processJson(filePath, filePathOutput);

                        JOptionPane.showMessageDialog(ClassDiagramUI.this,
                                "Java code generated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                        new SaveProjectActionListener();
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
                        //UCDSystem.out.println("In here");
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
                        new SaveProjectActionListener();
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

    /**
     * Exports the current UML diagram to a BufferedImage.
     * The diagram is rendered as an image with the specified width and height.
     *
     * @param width  The width of the resulting image.
     * @param height The height of the resulting image.
     * @return A BufferedImage representing the diagram.
     */
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
    /**
     * ActionListener for saving the UML class diagram project.
     * This listener triggers the save process of the current diagram's
     * classes, associations, and comments to the project file.
     */
    private class SaveProjectActionListener implements ActionListener {
        /**
         * Invoked when the save button is clicked.
         * It calls the saveCDProject method in the project object to save
         * the current diagram data (classes, associations, comments)
         * into the project file.
         *
         * @param e The action event triggered by the button click.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            project.saveCDProject(classes,associations,comments,ClassDiagramUI.this); //probably send class + comment + association list

        }
    }

    /**
     * Draws a partition line for the class in the UML diagram.
     * It identifies the class name from the diagram notes and updates
     * the associated UMLComponent to draw a partition line below it.
     */
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

    /**
     * Removes a partition line from a class in the UML diagram.
     * It checks the current number of partitions and updates the UMLComponent
     * accordingly, repainting the canvas to reflect the changes.
     *
     * @param currentPartitions The number of partitions to be updated for the class.
     */
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
                    //update the number of partitions
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

    /**
     * A custom JPanel that provides a canvas for displaying draggable, editable,
     * and deletable UML components such as classes, comments, and associations.
     * It supports interaction with components via mouse events, including resizing,
     * moving, and context menu actions.
     */
    //Canvas to display draggable, editable, and deletable components
    public class UMLCanvas extends JPanel {
        private ArrayList<UMLComponent> components;
        private UMLComponent selectedComponent;
        private boolean resizing;
        private final int maxWidth = 2000;
        private final int maxHeight = 2000;

        /**
         * Returns the preferred size of the canvas.
         *
         * @return The preferred size of the canvas, set to a maximum width and height.
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(maxWidth, maxHeight);
        }
        /**
         * Constructs a new UMLCanvas and initializes necessary fields.
         * Sets up mouse listeners for interaction with components.
         */
        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing = false;

            //Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    selectComponentAt(e.getPoint());
//
//                    if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null && (selectedComponent.type == "Class" || selectedComponent.type == "Comment")) {
//                        showContextMenu(e);
//                    }
//
//                    if (selectedComponent != null && (selectedComponent.type != "Class" && selectedComponent.type != "Comment") && selectedComponent.isInResizeHandle(e.getPoint())) {
//                        resizing = true;
//                    }
//
//                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    resizing = false;
                    if (selectedComponent != null) {
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
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    //selectComponentAt(e.getPoint());

                    if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null && (selectedComponent.type == "Class" || selectedComponent.type == "Comment")) {
                        showContextMenu(e);
                    }

                    if (selectedComponent != null && (selectedComponent.type != "Class" && selectedComponent.type != "Comment") && selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
                    //Check if right-click is on an attribute or method
                    if (selectedComponent != null && selectedComponent.type=="Class") {
                        // Right-click on attribute
                        if (selectedComponent.containsAttributeOrMethod(e.getPoint(), "attribute")) {
                            //Show context menu to edit attribute
                            showEditAttributeMenu(e);
                        }
                        //Right-click on method
                        else if (selectedComponent.containsAttributeOrMethod(e.getPoint(), "method")) {
                            //Show context menu to edit method
                            showEditMethodMenu(e);
                        }
                        //Handle class or comment specific logic
                        else if (SwingUtilities.isRightMouseButton(e) && (selectedComponent.type.equals("Class") || selectedComponent.type.equals("Comment"))) {
                            showContextMenu(e);
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
        /**
         * Displays a context menu when the user right-clicks on a selected component.
         * The menu offers options such as renaming, deleting, or editing the component.
         *
         * @param e The mouse event triggered by the right-click.
         */
        private void showContextMenu(MouseEvent e) {
            JPopupMenu menu = new JPopupMenu();

            //Simple Edit option (no submenu) for "Comment" type
            if (Objects.equals(selectedComponent.getType(), "Comment")) {
                JMenuItem editComment = new JMenuItem("Edit");
                editComment.addActionListener(ev -> {
                    String newContent = JOptionPane.showInputDialog("Edit comment text:");
                    if (newContent != null) {
                        selectedComponent.setCommentText(newContent);
                        comments.get(selectedComponent.id).setCommentText(newContent);
                        repaint();
                    }
                });
                menu.add(editComment);
            } else {
                //Edit menu for Class and other non-Comment types
                JMenu editMenu = new JMenu("Edit");
                JMenuItem addAttribute = new JMenuItem("Add Attribute");
                JMenuItem addMethod = new JMenuItem("Add Method");
                JMenuItem makeInterface = new JMenuItem("Make Interface");
                JMenuItem makeAbstract = new JMenuItem("Make Abstract");

                addAttribute.addActionListener(ev -> {
                    String attribute = JOptionPane.showInputDialog("Enter attribute (e.g., + attribute1):");
                    if (attribute != null && selectedComponent != null) {
                        selectedComponent.addAttribute(attribute);
                        repaint();
                    }
                });

                addMethod.addActionListener(ev -> {
                    String method = JOptionPane.showInputDialog("Enter method (e.g., + operation1()):");
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

            //Rename option
            JMenuItem rename = new JMenuItem("Rename");
            rename.addActionListener(ev -> {
                String newName = JOptionPane.showInputDialog("Enter new name:");
                if (newName != null) {
                    selectedComponent.setName(newName);
                    repaint();
                }
            });

            //Delete option
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

            //Display the menu
            menu.show(this, e.getX(), e.getY());
        }
        /**
         * Creates a new UML component at a default position on the canvas.
         *
         * @param type The type of the component to create (e.g., "Class", "Comment").
         */
        //Create a new component at a default position
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
        /**
         * Displays a context menu for editing an attribute of a class.
         *
         * @param e The mouse event triggered by right-clicking on an attribute.
         */
        private void showEditAttributeMenu(MouseEvent e) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem renameAttribute = new JMenuItem("Rename Attribute");
            renameAttribute.addActionListener(ev -> {
                String newAttribute = JOptionPane.showInputDialog("Edit attribute name:");
                if (newAttribute != null) {
                    // Find which attribute is clicked and rename it
                    for (int i = 0; i < selectedComponent.attributes.size(); i++) {
                        if (new Rectangle(selectedComponent.position.x + 5, selectedComponent.position.y + 35 + i * 15, selectedComponent.maxWidth, 15).contains(e.getPoint())) {
                            selectedComponent.attributes.set(i, newAttribute); // Rename attribute
                            repaint();
                            break;
                        }
                    }
                }
            });
            menu.add(renameAttribute);
            menu.show(this, e.getX(), e.getY());
        }
        /**
         * Displays a context menu for editing a method of a class.
         *
         * @param e The mouse event triggered by right-clicking on a method.
         */
        private void showEditMethodMenu(MouseEvent e) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem renameMethod = new JMenuItem("Rename Method");
            renameMethod.addActionListener(ev -> {
                String newMethod = JOptionPane.showInputDialog("Edit method name:");
                if (newMethod != null) {
                    // Find which method is clicked and rename it
                    for (int i = 0; i < selectedComponent.methods.size(); i++) {
                        if (new Rectangle(selectedComponent.position.x + 5, selectedComponent.position.y + 45 + selectedComponent.attributes.size() * 15 + i * 15, selectedComponent.maxWidth, 15).contains(e.getPoint())) {
                            selectedComponent.methods.set(i, newMethod); // Rename method
                            repaint();
                            break;
                        }
                    }
                }
            });
            menu.add(renameMethod);
            menu.show(this, e.getX(), e.getY());
        }
        /**
         * Selects a component at the specified point on the canvas.
         *
         * @param point The point to check for a component.
         */
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
        /**
         * Paints the canvas, drawing the grid and UML components.
         *
         * @param g The Graphics object used for painting the canvas.
         */
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
    /**
     * The SamplesPanel class is a JPanel that displays a set of UML component samples (e.g., Class, Association,
     * Inheritance, Aggregation, Composition, Comment, Comment Line) that can be clicked to add new components to a UML canvas.
     * The panel listens for mouse clicks and determines which sample the user clicked on, then creates the corresponding
     * UML component on the provided UMLCanvas.
     */
    // Panel to display static samples
    public class SamplesPanel extends JPanel {
        private UMLCanvas canvas;
        /**
         * Constructs a SamplesPanel with the specified UMLCanvas.
         *
         * @param canvas the UMLCanvas to which new components will be added when clicked
         */
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
        /**
         * Detects which UML sample was clicked based on the given point.
         *
         * @param point the point where the mouse was clicked
         * @return the type of UML component (e.g., "Class", "Association", etc.) or null if no component is clicked
         */
        // Detect which sample is clicked
        private String detectSampleClick(Point point) {
            int y = point.y;
            if (y < 50) return "Class";
            else if (y < 100) return "Association";
            else if (y < 150) return "Inheritance";
            else if (y < 200) return "Aggregation";
            else if (y < 250) return "Composition";
            else if (y < 300) return "Comment";
            else if(y<370) return "Comment Line";
            return null;
        }
        /**
         * Paints the UML component samples onto the panel. This method draws the shapes and labels for each component.
         *
         * @param g the Graphics object used to draw the samples
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSamples(g);
        }

        /**
         * Draws the UML component samples on the panel, including Class, Association, Inheritance, Aggregation, Composition,
         * Comment, and Comment Line. Each sample is drawn with its corresponding label.
         *
         * @param g the Graphics object used to draw the samples
         */
        //Draw samples
        private void drawSamples(Graphics g) {
            int x = 10, y = 10;
            g.setColor(Color.BLACK);

            //Class
            g.drawRect(x, y, 100, 50);
            g.drawLine(x, y + 15, x + 100, y + 15);
            g.drawString("Class", x + 35, y + 10);

            //Association
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawString("Association", x + 35, y + 10);

            //Inheritance
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x + 100, x + 105, x + 100}, new int[]{y + 20, y + 25, y + 30}, 3);
            g.drawString("Inheritance", x + 25, y + 10);

            //Aggregation
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
            g.drawString("Aggregation", x + 20, y + 10);

            //Composition
            y += 50;
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.fillPolygon(new int[]{x + 100, x + 105, x + 110, x + 105}, new int[]{y + 25, y + 20, y + 25, y + 30}, 4);
            g.drawString("Composition", x + 20, y + 10);

            //Comment
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

            //Comment Line
            y+=70;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y + 25, x + 100, y + 25);
            g.drawString("Comment Line", x + 25, y + 10);
        }
    }

    /**
     * The UMLComponent class is a class that draws a set of UML components (e.g., Class, Association,
     * Inheritance, Aggregation, Composition, Comment, Comment Line)  onto the canvas, that can be clicked to edit or delete.
     *
     */
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
        public boolean isAbstract;
        /**
         * Constructs a UML component with the specified type and position.
         * @param type the type of the UML component (e.g., "Class", "Association", etc.)
         * @param position the position of the component in the form of a Point (x, y).
         */
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
            isAbstract = false;
        }
        /**
         * Constructs a UML component with the specified type and start/end points for line-based components.
         * @param type the type of the UML component (e.g., "Association", "Inheritance", etc.)
         * @param start the starting point of the component (e.g., for associations, lines, etc.)
         * @param end the ending point of the component (e.g., for associations, lines, etc.)
         */
        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position = null;
            noOfPartitions = 0;
        }
        /**
         * Gets the type of the UML component.
         * @return the type of the component.
         */
        public String getType() {
            return type;
        }
        /**
         * Moves the non-line based UML component  to a new position, centering the component on the new point.
         * @param newPoint the new point to move the component to.
         */
        public void move(Point newPoint) {
            position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
            if(type=="Class")
                classes.get(id).setPosition(position);
            else
                comments.get(id).setPosition(position);
        }
        /**
         * Moves the line-based UML component (e.g., Association, Inheritance etc) to a new position.
         * @param newPoint the new point to move the line to.
         */
        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
            associations.get(id).setStartPoint(start);
            associations.get(id).setEndPoint(end);
        }
        /**
         * Checks if the given point is inside the bounds of the UML component.
         * @param point the point to check.
         * @return true if the point is inside the component's bounds, false otherwise.
         */
        public boolean contains(Point point) {
            return point.x >= position.x && point.x <= position.x + maxWidth
                    && point.y >= position.y && point.y <= position.y + maxHeight;
        }
        /**
         * Checks if the given point is inside a line-based component (e.g., Association).
         * @param point the point to check.
         * @return true if the point is inside the line's bounds, false otherwise.
         */
        public boolean containsLine(Point point) {
            return new Rectangle(start.x - 5, start.y - 5, 10, 10).contains(point) ||
                    new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        /**
         * Checks if the given point is within the resize handle of the UML component.
         * @param point the point to check.
         * @return true if the point is inside the resize handle, false otherwise.
         */
        public boolean isInResizeHandle(Point point) {
            return new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        /**
         * Resizes the UML component by adjusting its end point.
         * @param point the new end point for resizing.
         */
        public void resize(Point point) {
            end.setLocation(point);
            Association a=associations.get(id);
            a.setEndPoint(end);
        }
        /**
         * Sets the name of the UML component and updates it in the respective list.
         * @param name the new name for the UML component.
         */
        public void setName(String name) {
            this.name = name;
            if(type=="Class") {
                Class c = classes.get(id);
                c.setName(name);
            }
            else if(type=="Comment") {
                Comment c=comments.get(id);
                c.setName(name);
            }else{
                Association a=associations.get(id);
                a.setName(name);
            }
        }
        /**
         * Calculates the maximum width required for the UML component, based on its name, attributes, and methods.
         * @param g the Graphics object used to measure text width.
         * @return the maximum width required for the component.
         */
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
        /**
         * Calculates the maximum height required for the UML component based on its attributes and methods.
         * @param g the Graphics object used to calculate height.
         * @return the maximum height required for the component.
         */
        public int calcMaxHeight(Graphics g){
            int max=50 + attributes.size() * 15 + methods.size() * 15;
            maxHeight=max;
            return max;
        }
        /**
         * Adds an attribute to the UML component and updates it in the respective class.
         * @param attribute the attribute to add to the UML component.
         */
        public void addAttribute(String attribute) {
            attributes.add(attribute);
            classes.get(id).addAttribute(attribute);
        }
        /**
         * Checks if the point is within an attribute or method section of the UML component.
         * @param point the point to check.
         * @param type the type of section to check ("attribute" or "method").
         * @return true if the point is inside the attribute or method section, false otherwise.
         */
        public boolean containsAttributeOrMethod(Point point, String type) {


            int x = position.x;
            int y = position.y;
            int width = maxWidth;

            // Determine y position of attributes or methods
            int startY = y + 35;

            if (type.equals("attribute")) {
                for (String attribute : attributes) {
                    if (new Rectangle(x + 5, startY, width, 15).contains(point)) {
                        return true;
                    }
                    startY += 15;
                }
            } else if (type.equals("method")) {
                startY = y + 45 + attributes.size() * 15;
                for (String method : methods) {
                    if (new Rectangle(x + 5, startY, width, 15).contains(point)) {
                        return true;
                    }
                    startY += 15;
                }
            }
            return false;
        }
        /**
         * Adds a method to the UML component and updates it in the respective class.
         * @param method the method to add to the UML component.
         */
        public void addMethod(String method) {
            methods.add(method);
            classes.get(id).addMethod(method);
        }
        /**
         * Makes the UML component an interface by adding the appropriate prefix to the name.
         */
        public void makeInterface() {
            if (!name.startsWith("<<interface>> ")) { // Prevent duplicate prefix
                name = "<<interface>> " + name;
            }
            classes.get(id).setName(name);
            classes.get(id).setState("Interface");
        }
        /**
         * Makes the UML component abstract by setting the appropriate flags and updating its state.
         */
        public void makeAbstract() {
            name = name; // Keep the name unchanged
            classes.get(id).setName(name);
            isAbstract = true; // Use a flag to indicate the abstract state
            classes.get(id).setState("Abstract");
        }
        /**
         * Sets the comment text for the UML component, throwing exceptions if invalid input is provided.
         * @param newContent the new comment text.
         * @throws UnsupportedOperationException if the component type is not "Comment".
         * @throws IllegalArgumentException if the comment text is null or empty.
         */
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
        /**
         * Draws the UML component on the provided Graphics object, handling different types of components.
         * @param g the Graphics object used to draw the component.
         */
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
                    // Set font for name (italic if abstract)
                    Font originalFont = g.getFont();
                    if (isAbstract) {
                        g.setFont(originalFont.deriveFont(Font.ITALIC));
                        g.drawString(name, x + 10, y + 15);
                        g.setFont(originalFont.deriveFont(Font.PLAIN));
                    }
                    else
                        g.drawString(name, x + 10, y + 15);
                    // Reset font after drawing the name
                    g.setFont(originalFont);
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
                case "Comment Line":
                    Graphics2D g2d=(Graphics2D)g;
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                    g.drawLine(start.x, start.y, end.x, end.y);
                    g2d.setStroke(new BasicStroke());

                    break;
            }
            if (position != null && type=="Comment")
                g.drawString(name, x + 10, y + 15);
            if (drawPartition) {
                noOfPartitions++;
                drawPartition = false;
                repaint();
            }
        }
        /**
         * Sets the flag to indicate that the component should draw a partition.
         * @param val the value indicating whether a partition should be drawn.
         */
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