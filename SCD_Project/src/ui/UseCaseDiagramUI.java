package ui;

import business.Actor;
import business.Project;
import business.UseCaseArrow;
import business.Usecase;
import business.UCDSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The {@code UseCaseDiagramUI} class represents the user interface for designing UML Use Case Diagrams.
 * It provides tools for adding use cases, actors, arrows, and systems, as well as features for saving,
 * loading, and exporting projects and diagrams.
 *
 * This UI includes functionality for:
 * <ul>
 *     <li>Saving and loading UML diagrams</li>
 *     <li>Exporting diagrams as images (JPEG/PNG)</li>
 *     <li>Switching to a Class Diagram UI</li>
 * </ul>
 *
 * The class is built using Swing components and integrates with the business layer for project operations.
 */
public class UseCaseDiagramUI extends JFrame {
    /** Panel displaying the title of the application. */
    JPanel pageTitlePanel;
/** Top panel containing title and buttons for saving and loading. */
    JPanel topPanel;
    /** Panel for displaying and editing diagram notes. */
    JPanel diagramNotesPanel;
    /** Bottom panel (reserved for additional UI components). */
    JPanel bottomPanel;
    /** The canvas for drawing the UML Use Case Diagram. */
    UMLCanvas canvas;
    /** Panel displaying sample components for the UML diagram. */
    SamplesPanel samplesPanel;
    /** Scrollable container for the UML canvas. */
    JScrollPane canvasScrollPanel;
    /** Right-side panel containing sample components and mouse coordinates. */
    JPanel rightPanel;
    /** Text area for entering diagram notes. */
    JTextArea diagramNotes;
    /** List of use cases in the diagram. */
    ArrayList<business.Usecase> usecases;
    /** List of actors in the diagram. */
    ArrayList<business.Actor> actors;
    /** List of arrows connecting use cases and actors. */
    ArrayList<UseCaseArrow> arrows;
    /** List of systems in the diagram. */
    ArrayList<UCDSystem> systems;
    /** The current project being worked on. */
    Project project;
    /** Button for saving the diagram as an image. */
    JButton saveImage;
    /** Button for saving the project. */
    JButton saveProject;
    /** Button for loading an existing project. */
    JButton loadProject;
    /** Button for switching to the Class Diagram UI. */
    JButton toCDButton;
    /** Panel containing buttons for file operations. */
    JPanel topButtonPanel;
    /** Panel containing navigation buttons. */
    JPanel topRightButtonPanel;
    /** Label displaying the current mouse coordinates on the canvas. */
    JLabel mouseCoords;
    /** Panel containing the mouse coordinates label. */
    JPanel bottomCoords;


    /**
     * Constructs the {@code UseCaseDiagramUI} and initializes the user interface components.
     * Sets up action listeners for various operations such as saving, loading, and exporting diagrams.
     */
    public UseCaseDiagramUI() {

        //business layer objects
        arrows = new ArrayList<>();
        actors=new ArrayList<>();
        usecases=new ArrayList<>();
        systems=new ArrayList<UCDSystem>();
        project=new Project();

        saveImage=new JButton("\uD83D\uDDBC\uFE0F Save Image");
        saveProject=new JButton("\uD83D\uDCBE Save Project");
        loadProject= new JButton("⏫ Load Project");
        toCDButton=new JButton("Class Diagram ➡\uFE0F");

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

        JLabel pageTitle = new JLabel("UML Use Case Diagram");
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
        topRightButtonPanel.add(toCDButton);
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
                        UseCaseDiagramUI.this,
                        "Enter File Name:",
                        "Save Image",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (fileName == null || fileName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(UseCaseDiagramUI.this,
                            "Project name cannot be empty.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Destination");
                fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG or PNG", "jpeg", "png"));
                fileChooser.setSelectedFile(new File(fileName));

                int userSelection = fileChooser.showSaveDialog(UseCaseDiagramUI.this);
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
                            project.exportToJPEG(filePath, width, height, UseCaseDiagramUI.this);
                        } else if (filePath.endsWith(".png")) {
                            project.exportToPNG(filePath,width,height,UseCaseDiagramUI.this);
                        }
                        JOptionPane.showMessageDialog(UseCaseDiagramUI.this,
                                "Image saved successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(UseCaseDiagramUI.this,
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
                ArrayList<business.Component> components=project.loadProject(UseCaseDiagramUI.this);
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
                        systems.add((UCDSystem)components.get(i));
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
                for(int i=0;i<systems.size();i++){
                    UMLComponent c=new UMLComponent("System",systems.get(i).getPosition());
                    c.name=systems.get(i).getName();
                    c.height=systems.get(i).getHeight();
                    c.width=systems.get(i).getWidth();
                    comps.add(c);
                }
                canvas.components=comps;
                canvas.repaint();
            }
        });
        toCDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!canvas.components.isEmpty()){
                    int choice = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to save the project before moving to Class Diagram?",
                            "Save Project",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        project.saveUCDProject(usecases,actors,arrows,systems,UseCaseDiagramUI.this);
                        frame.dispose();
                    } else if (choice == JOptionPane.NO_OPTION) {
                        frame.dispose();
                    }
                }
                frame.dispose();
                new ClassDiagramUI();
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
                        project.saveUCDProject(usecases,actors,arrows,systems,UseCaseDiagramUI.this);
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
    /**
     * Exports the UML diagram as a buffered image with the specified dimensions.
     *
     * @param width  the width of the image.
     * @param height the height of the image.
     * @return a {@code BufferedImage} containing the UML diagram.
     */
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
     * Inner class for handling project-saving operations.
     */
    private class SaveProjectActionListener implements ActionListener {
        /**
         * Handles the save project action when triggered by the user.
         *
         * @param e the event triggered by the save project button.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            project.saveUCDProject(usecases,actors,arrows,systems,UseCaseDiagramUI.this);
        }
    }
    /**
     * The {@code UMLCanvas} class represents a custom JPanel for drawing and interacting
     * with UML components such as Use Cases, Actors, Systems, and connecting lines (arrows).
     * This canvas supports the creation, dragging, resizing, renaming, and deletion of components.
     */
    //Canvas to display draggable, editable, and deletable components
    public class UMLCanvas extends JPanel {

        /**
         * List of all UML components displayed on the canvas.
         */
        private ArrayList<UMLComponent> components;
        /**
         * The currently selected component for interaction.
         */
        private UMLComponent selectedComponent;
        /**
         * Flag indicating whether the selected component is being resized.
         */
        private boolean resizing;
        /**
         * Maximum width of the canvas.
         */
        private final int maxWidth = 2000;
        /**
         * Maximum height of the canvas.
         */
        private final int maxHeight = 2000;
        /**
         * Returns the preferred size of the canvas.
         *
         * @return a {@code Dimension} object representing the preferred size.
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(maxWidth, maxHeight);
        }
        /**
         * Constructs a new {@code UMLCanvas}, initializing mouse listeners
         * to handle component interactions.
         */
        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing = false;

            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                /**
                 * Handles mouse press events to select components or initiate resizing.
                 *
                 * @param e the {@code MouseEvent} object containing mouse details.
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());
                    if (selectedComponent != null && (selectedComponent.type != "Usecase" && selectedComponent.type != "Actor" && selectedComponent.type!="System") && selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
                    else if (selectedComponent != null && selectedComponent.type.equals("System") && selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
                }
                /**
                 * Handles mouse press events to select components or initiate resizing.
                 *
                 * @param e the {@code MouseEvent} object containing mouse details.
                 */
                @Override
                public void mouseReleased(MouseEvent e) {
                    resizing = false;
                    //selectedComponent=null;
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
                            else if(selectedComponent.type=="System")
                                systems.remove(selectedComponent.id);
                            else
                                arrows.remove(selectedComponent.id);
                            selectedComponent = null;
                            repaint();
                        }
                    }
                }
                /**
                 * Handles mouse click events to rename components or update notes.
                 *
                 * @param e the {@code MouseEvent} object containing mouse details.
                 */
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
                /**
                 * Handles mouse drag events to move or resize components.
                 *
                 * @param e the {@code MouseEvent} object containing mouse details.
                 */
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedComponent != null) {
                        if (resizing) {
                            selectedComponent.resize(e.getPoint());
                        } else {
                            if (Objects.equals(selectedComponent.type, "Usecase") || Objects.equals(selectedComponent.type, "Actor") || selectedComponent.type.equals("System"))
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
         * Creates a new UML component of the specified type and adds it to the canvas.
         *
         * @param type the type of the component (e.g., "Usecase", "Actor", "System").
         */
        // Create a new component at a default position
        public void createNewComponent(String type) {
            if (type == "Usecase" || type == "Actor" || type=="System") {
                UMLComponent comp = new UMLComponent(type, new Point(100, 100));
                components.add(comp);
                if (type == "Usecase") {
                    Usecase c = new Usecase();
                    usecases.add(c);
                    int i = usecases.indexOf(c);
                    comp.id = i;
                }
                else if(type=="Actor"){
                    Actor c=new Actor();
                    c.setPosition(new Point(100,100));
                    actors.add(c);
                    int i=actors.indexOf(c);
                    comp.id=i;
                }
                else{
                    UCDSystem s=new UCDSystem();
                    s.setPosition(new Point(100,100));
                    systems.add(s);
                    int i=systems.indexOf(s);
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

        /**
         * Selects a component at the specified point, if any exists.
         *
         * @param point the point to check for a component.
         */
        private void selectComponentAt(Point point) {
            for (UMLComponent component : components) {
                if ((Objects.equals(component.type, "Usecase") || Objects.equals(component.type, "Actor") || Objects.equals(component.type, "System")) && component.contains(point)) {
                    selectedComponent = component;
                    return;
                } else if ((component.type != "Usecase" && component.type != "Actor" && component.type!="System") && component.containsLine(point)) {

                    selectedComponent = component;
                    return;
                }
            }
            selectedComponent = null;
        }
        /**
         * Paints the canvas and its components, including a grid background.
         *
         * @param g the {@code Graphics} object used for drawing.
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
     * The {@code SamplesPanel} class represents a side panel that displays static UML samples
     * (e.g., Use Case, Actor, System, etc.) and allows users to create these components
     * on a linked {@link UMLCanvas} by clicking on the samples.
     */
    // Panel to display static samples
    public class SamplesPanel extends JPanel {
        /**
         * The {@link UMLCanvas} instance linked to this panel, where components are created.
         */
        private UMLCanvas canvas;
        /**
         * Constructs a {@code SamplesPanel} with the specified {@link UMLCanvas}.
         * Sets up mouse listeners to detect clicks on the samples and add components to the canvas.
         *
         * @param canvas the UML canvas linked to this panel
         */
        public SamplesPanel(UMLCanvas canvas) {
            this.canvas = canvas;
            this.setPreferredSize(new Dimension(150, 0));
            this.setBackground(new Color(200, 200, 200));

            // Add mouse listener to detect clicks on samples
            this.addMouseListener(new MouseAdapter() {
                /**
                 * Detects mouse presses and creates a new UML component on the canvas
                 * if a sample is clicked.
                 *
                 * @param e the {@code MouseEvent} object containing mouse details
                 */
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
         * Detects which sample is clicked based on the point coordinates.
         *
         * @param point the point where the mouse was clicked
         * @return the type of the clicked sample, or {@code null} if no sample is clicked
         */
        // Detect which sample is clicked
        private String detectSampleClick(Point point) {
            int y = point.y;
            if (y < 60) return "Usecase";
            else if (y < 180) return "Actor";
            else if (y < 230) return "Arrow";
            else if (y < 280) return "Include";
            else if (y < 330) return "Extend";
            else if (y < 380) return "System";
            return null;
        }
        /**
         * Paints the panel and draws the static UML samples.
         *
         * @param g the {@code Graphics} object used for painting
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSamples(g);
        }
        /**
         * Draws static UML samples (Use Case, Actor, System, etc.) on the panel.
         *
         * @param g the {@code Graphics} object used for drawing
         */
        // Draw static samples
        private void drawSamples(Graphics g) {
            int x = 20, y = 10;
            g.setColor(Color.BLACK);

            //Usecase bubble
            g.drawOval(x, y, 100, 50);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth("Usecase");
            int textHeight = fm.getHeight();
            g.drawString("Usecase", (100 - textWidth) / 2 + 10, (50 + textHeight / 2) / 2 + 10);
            //g.drawString("Usecase", x + 35, y + 10);

            //Actor
            y += 60;
            x += 50;
            g.drawOval(x - 15, y, 30, 30);  // Head
            g.drawLine(x, y + 30, x, y + 60);   // Body
            g.drawLine(x - 20, y + 45, x + 20, y + 45);  // Arms
            g.drawLine(x, y + 60, x - 20, y + 90);  // Left leg
            g.drawLine(x, y + 60, x + 20, y + 90);  // Right leg
            fm = g.getFontMetrics();
            textWidth = fm.stringWidth("Actor");
            g.drawString("Actor", x - textWidth / 2, y + 110);

            //Arrow
            x -= 50;
            y += 120;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            //Include
            y += 50;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            fm = g2d.getFontMetrics();
            textWidth = fm.stringWidth("include");
            g.drawString("<<include>>", (getWidth() - textWidth) / 2, y - 5);

            //Extend
            y += 50;
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
            g.drawLine(x, y, 100, y);
            g.drawLine(100, y, 100 - 10, y - 10);
            g.drawLine(100, y, 100 - 10, y + 10);

            fm = g2d.getFontMetrics();
            textWidth = fm.stringWidth("extend");
            g.drawString("<<extend>>", (getWidth() - textWidth) / 2, y - 5);

            //UCDSystem
            y += 60;
            g2d.setStroke(new BasicStroke(1));
            g.drawRect(x, y, 100, 50);
            g.drawString("System", x + 35, y + 30);

        }
    }
    //UML Component class
    /**
     * The {@code UMLComponent} class represents a component in a UML diagram.
     * It can represent various UML elements, such as Use Case, Actor, Arrow, Include, Extend, or System.
     * This class provides functionalities to manage the position, size, and drawing of the component.
     */
    public class UMLComponent {
        /**
         * * The type of UML component (e.g., "Usecase", "Actor", "Arrow", "Include", "Extend", "System").
         */
        private String type;

        /**
         * The position of the UML component on the canvas.
         */

        private Point position;
        /**
         * The name or label of the UML component.
         */
        private String name;
        /**
         * The start point of a line-based UML component (e.g., Arrow, Include, Extend).
         */
        private Point start;

        /**
         * The end point of a line-based UML component (e.g., Arrow, Include, Extend).
         */
        private Point end;
        /**
         * The unique identifier of the UML component.
         */
        public int id;
        /**
         * The width of the component (used for "System").
         */
        private int width;
        /**
         * The height of the component (used for "System").
         */
        private int height;
        /**
         * Constructs a UML component with a type and a position.
         * This constructor is used for components like Usecase, Actor, and System.
         *
         * @param type     the type of the UML component
         * @param position the position of the component on the canvas
         */
        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start = null;
            this.end = null;
            this.width = 150; // Default width for UCDSystem
            this.height = 100; // Default height for UCDSystem
        }
        /**
         * Constructs a UML component with a type, start point, and end point.
         * This constructor is used for components like Arrow, Include, and Extend.
         *
         * @param type  the type of the UML component
         * @param start the start point of the component
         * @param end   the end point of the component
         */
        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position = null;
        }
        /**
         * Moves the UML component to a new position on the canvas.
         * The movement logic depends on the type of the component.
         *
         * @param newPoint the new position
         */
        public void move(Point newPoint) {
            if(type=="Usecase")
                position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
            else if (type.equals("System"))
                position = new Point(newPoint.x - width / 2, newPoint.y - height / 2);
            else
                position=new Point(newPoint.x,newPoint.y-50);

            if(type=="Usecase")
                usecases.get(id).setPosition(position);
            else if(type=="Actor")
                actors.get(id).setPosition(position);
            else if(type=="System")
                systems.get(id).setPosition(position);
        }
        /**
         * Moves a line-based UML component by translating its start and end points.
         *
         * @param newPoint the new position to move the line
         */
        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
            arrows.get(id).setStartPoint(start);
            arrows.get(id).setEndPoint(end);
        }
        /**
         * Checks if a given point is within the bounds of the UML component.
         *
         * @param point the point to check
         * @return {@code true} if the point is within the component, otherwise {@code false}
         */
        public boolean contains(Point point) {
            if(type=="Usecase")
                return point.x >= position.x && point.x <= position.x + 100
                        && point.y >= position.y && point.y <= position.y + 50;
            else if (type.equals("System"))
                return point.x >= position.x && point.x <= position.x + width
                        && point.y >= position.y && point.y <= position.y + height;
            else
                return point.x >= position.x-20 && point.x <= position.x + 20
                        && point.y >= position.y && point.y <= position.y + 110;
        }
        /**
         * Checks if a given point is within the bounds of a line-based UML component.
         *
         * @param point the point to check
         * @return {@code true} if the point is near the start or end points, otherwise {@code false}
         */
        public boolean containsLine(Point point) {
            return new Rectangle(start.x - 5, start.y - 5, 10, 10).contains(point) ||
                    new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }
        /**
         * Checks if a given point is within the resize handle of the UML component.
         * The resize handle is typically located at the bottom-right corner of components like "System".
         *
         * @param point the point to check
         * @return {@code true} if the point is within the resize handle, otherwise {@code false}
         */
        public boolean isInResizeHandle(Point point) {
            if (type.equals("System")) {
                //bottom-right corner is the resize handle (10x10 px area)
                int handleSize = 10;
                Rectangle resizeHandle = new Rectangle(position.x + width - handleSize, position.y + height - handleSize, handleSize, handleSize);
                return resizeHandle.contains(point);
            }
            return new Rectangle(end.x - 5, end.y - 5, 10, 10).contains(point);
        }

        /**
         * Resizes the UML component based on the given point.
         * Resizing is only applicable for certain types like "System".
         *
         * @param point the point to determine the new size
         */
        public void resize(Point point) {
            if(type=="System"){
                width = Math.max(50, point.x - position.x); // Minimum width = 50
                height = Math.max(30, point.y - position.y); // Minimum height = 30
                systems.get(id).setWidth(width);
                systems.get(id).setHeight(height);
            }
            else {
                end.setLocation(point);
                UseCaseArrow a = arrows.get(id);
                a.setEndPoint(end);
            }
        }
        /**
         * Sets the name or label of the UML component.
         *
         * @param name the new name of the component
         */
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
        /**
         * Draws the UML component on the canvas.
         * The drawing logic is specific to the type of component.
         *
         * @param g the {@code Graphics} object used for drawing
         */
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
                int padding = 20; // Extra space around the text
                int ovalWidth = textWidth + padding; // Adjust oval width
                int ovalHeight = textHeight + padding; // Adjust oval height
                g.drawOval(x, y, ovalWidth, ovalHeight);
                g.drawString(name, x + (ovalWidth - textWidth) / 2, y + (ovalHeight + textHeight / 2) / 2);
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
            case "System":
                g2d.setStroke(new BasicStroke(1));
                g.drawRect(x, y, width, height); // Draw system rectangle

                // Calculate top-center position for the name
                int textX = x + (width - textWidth) / 2; // Horizontally center the text
                int textY = y + textHeight;              // Place text just below the top border

                g.drawString(name, textX, textY); // Draw the name at the top-center

                // Draw resize handle at the bottom-right corner
                g.fillRect(x + width - 10, y + height - 10, 10, 10);
                break;
        }

        }
        /**
         * Draws the arrowhead for a line-based UML component (e.g., Arrow, Include, Extend).
         *
         * @param g     the {@code Graphics} object used for drawing
         * @param start the start point of the line
         * @param end   the end point of the line
         */
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
        new UseCaseDiagramUI();
    }
}