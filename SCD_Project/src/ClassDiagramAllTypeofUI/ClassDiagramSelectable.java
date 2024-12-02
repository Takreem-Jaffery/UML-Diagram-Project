

import business.Class;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
public class ClassDiagramSelectable extends JFrame {
    JPanel pageTitlePanel;
    JPanel diagramNotesPanel;
    JPanel bottomPanel;
    UMLCanvas canvas;
    SamplesPanel samplesPanel;

    JTextArea diagramNotes;
    ArrayList<Class> classes;

    JButton saveImage;
    JButton saveProject;

    public ClassDiagramSelectable() {

        classes=new ArrayList<>();

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
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    void classRemovePartition(int currentPartitions){
        ArrayList<UMLComponent>comps=canvas.components;
        try {
            int startOffset = diagramNotes.getLineStartOffset(0);
            int endOffset = diagramNotes.getLineEndOffset(0);

            //first line aka the name of the class
            String firstLine = diagramNotes.getText(startOffset, endOffset - startOffset).trim();
            for(UMLComponent component:comps){

                if(Objects.equals(component.name, firstLine)){
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
        private Graphics cachedGraphics;
        private JTextArea diagramNotes;

        public UMLCanvas() {
            components = new ArrayList<>();
            selectedComponent = null;
            resizing=false;


            // Add mouse listeners for interaction
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    selectComponentAt(e.getPoint());

                    if (SwingUtilities.isRightMouseButton(e) && selectedComponent != null) {
                        showContextMenu(e);
                    }

                    if (selectedComponent != null && (selectedComponent.type != "Class" && selectedComponent.type != "Comment") &&
                            selectedComponent.isInResizeHandle(e.getPoint())) {
                        resizing = true;
                    }
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedComponent != null && e.getClickCount() == 2) {
                        int mouseX = e.getX();
                        int mouseY = e.getY();

                        // Verify cachedGraphics
                        if (cachedGraphics == null) {
                            cachedGraphics = getGraphics();
                        }

                        if (selectedComponent.getType().equals("Class")) {
                            // Calculate starting Y positions for attributes
                            int attrY = selectedComponent.getPosition().y + 35;
                            for (int i = 0; i < selectedComponent.getAttributes().size(); i++) {
                                // Check if the mouse click is within the bounds of the current attribute
                                if (mouseX >= selectedComponent.getPosition().x &&
                                        mouseX <= selectedComponent.getPosition().x + selectedComponent.getBoxWidth(cachedGraphics) &&
                                        mouseY >= attrY && mouseY <= attrY + 15) {
                                    // Edit the selected attribute
                                    String newAttribute = JOptionPane.showInputDialog("Edit Attribute:", selectedComponent.getAttributes().get(i));
                                    if (newAttribute != null) {
                                        selectedComponent.getAttributes().set(i, newAttribute);
                                        repaint();
                                    }
                                    return;  // Exit after editing one attribute
                                }
                                attrY += 15; // Update position for the next attribute
                            }
                            // Calculate Y positions for methods, starting below attributes
                            int methodY = attrY; // Start after attributes
                            for (int i = 0; i < selectedComponent.getMethods().size(); i++) {
                                // Check if the mouse click is within the bounds of the current method
                                if (mouseX >= selectedComponent.getPosition().x &&
                                        mouseX <= selectedComponent.getPosition().x + selectedComponent.getBoxWidth(cachedGraphics) &&
                                        mouseY >= methodY && mouseY <= methodY + 15) {
                                    // Edit the selected method
                                    String newMethod = JOptionPane.showInputDialog("Edit Method:", selectedComponent.getMethods().get(i));
                                    if (newMethod != null) {
                                        selectedComponent.getMethods().set(i, newMethod);
                                        repaint();
                                    }
                                    return;  // Exit after editing one method
                                }
                                methodY += 15; // Update position for the next method
                            }
                        }

                        // Default rename behavior for the component itself
                        String newName = JOptionPane.showInputDialog("Enter new name:", selectedComponent.getName());
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

        private void updateComponentFromNotes() {
            if (selectedComponent != null && selectedComponent.isClassOrComment()) {
                selectedComponent.updateContentFromNotes(diagramNotes.getText());
                repaint();
            }
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
            if(type=="Class" || type=="Comment") {
                UMLComponent comp=new UMLComponent(type, new Point(100, 100));
                components.add(comp);
                if(type=="Class") {
                    Class c=new Class();
                    classes.add(c);
                    int i=classes.indexOf(c);
                    comp.id=i;
                }
            }
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
            cachedGraphics = g; // Cache the Graphics object
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
        private ArrayList<String> attributes;
        private ArrayList<String> methods;
        private String text;
        public UMLComponent(String type, Point position) {
            this.type = type;
            this.position = position;
            this.name = type;
            this.start=null;
            this.end=null;
            noOfPartitions=0;
            this.attributes = new ArrayList<>();
            this.methods = new ArrayList<>();
            this.text ="";
        }
        public UMLComponent(String type, Point start, Point end) {
            this.type = type;
            this.start = start;
            this.end = end;
            this.name = type;
            this.position=null;
            noOfPartitions=0;
            this.text ="";
        }
        // Setter for updating comment text
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

        // Additional getters and logic for comment text
        public String getCommentText() {
            if (!"Comment".equals(type)) {
                throw new UnsupportedOperationException("getCommentText can only be used for 'Comment' type components.");
            }
            return this.text;
        }
        public void move(Point newPoint) {
            position = new Point(newPoint.x - 50, newPoint.y - 25); // Center the drag
            classes.get(id).setPosition(position);
        }
        public void moveLine(Point newPoint) {
            int dx = newPoint.x - start.x;
            int dy = newPoint.y - start.y;
            start.translate(dx, dy);
            end.translate(dx, dy);
        }
        public ArrayList<String> getAttributes() {
            return attributes;
        }

        public ArrayList<String> getMethods() {
            return methods;
        }
        public int getId() {
            return id;
        }

        public Point getPosition() {
            return position;
        }
        public String getType() {
            return type;
        }
        public String getName() {
            return name;
        }

        public int getBoxWidth(Graphics g) {
            int maxTextWidth = g.getFontMetrics().stringWidth(name);
            for (String attribute : attributes) {
                maxTextWidth = Math.max(maxTextWidth, g.getFontMetrics().stringWidth(attribute));
            }
            for (String method : methods) {
                maxTextWidth = Math.max(maxTextWidth, g.getFontMetrics().stringWidth(method));
            }
            return Math.max(100, maxTextWidth + 10); // Minimum width is 100
        }

        public void addAttribute(String attribute) {
            attributes.add(attribute);
        }

        public void addMethod(String method) {
            methods.add(method);
        }

        public void makeInterface() {
            name = "<<interface>> " + name;
        }

        public void makeAbstract() {
            name = "<html><i>" + name + "</i></html>";
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
            Class c=classes.get(id);
            c.setName(name);
        }
        public boolean isClassOrComment() {
            return "Class".equals(type) || "Comment".equals(type);
        }

        public String getContentForNotes() {
            if ("Class".equals(type)) {
                return name + "\n" + String.join("\n", attributes) + "\n" + String.join("\n", methods);
            } else if ("Comment".equals(type)) {
                return name;
            }
            return "";
        }

        public void updateContentFromNotes(String content) {
            if ("Class".equals(type)) {
                String[] lines = content.split("\n");
                if (lines.length > 0) {
                    name = lines[0];
                    attributes = new ArrayList<>();
                    methods = new ArrayList<>();
                    boolean isMethods = false;
                    for (int i = 1; i < lines.length; i++) {
                        String line = lines[i].trim();
                        if (line.isEmpty()) continue;
                        if (!isMethods && line.startsWith("+")) {
                            attributes.add(line);
                        } else {
                            isMethods = true;
                            methods.add(line);
                        }
                    }
                }
            } else if ("Comment".equals(type)) {
                name = content;
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

                    // Add padding to the maximum text width
                    int boxWidth = Math.max(100, maxTextWidth + 10); // Minimum width is 100

                    // Calculate the total height of the class box
                    int totalHeight = 50 + attributes.size() * 15 + methods.size() * 15;

                    // Draw the class box
                    g.drawRect(x, y, boxWidth, totalHeight);

                    // Draw the name section
                    g.drawLine(x, y + 20, x + boxWidth, y + 20);
                    g.drawString(name, x + 5, y + 15);

                    // Draw the attributes section
                    if (!attributes.isEmpty()) {
                        g.drawLine(x, y + 20 + attributes.size() * 15, x + boxWidth, y + 20 + attributes.size() * 15);
                        int attrY = y + 35;
                        for (String attribute : attributes) {
                            g.drawString(attribute, x + 5, attrY);
                            attrY += 15;
                        }
                    }

                    // Draw the methods section
                    if (!methods.isEmpty()) {
                        int methodY = y + 35 + attributes.size() * 15;
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
                    // Determine the required width of the comment box based on the longest line of text
                    int maxCommentWidth = g.getFontMetrics().stringWidth(text); // Start with the initial comment width

                    // Split the comment into lines based on the width of the box and font metrics
                    String[] commentLines = wrapTextToFitWidth(text, g, maxCommentWidth);

                    // Calculate the maximum width of the comment box (it might change based on line wrapping)
                    for (String line : commentLines) {
                        maxCommentWidth = Math.max(maxCommentWidth, g.getFontMetrics().stringWidth(line));
                    }

                    // Add padding to the maximum text width
                    int commentBoxWidth = Math.max(150, maxCommentWidth + 10); // Minimum width is 150

                    // Calculate the height based on the number of lines
                    int commentBoxHeight = 50 + commentLines.length * 20; // Each line height can be adjusted (e.g., 20px per line)

                    // Draw the comment box
                    g.drawRect(x, y, commentBoxWidth, commentBoxHeight);

                    // Draw the comment text line by line
                    int commentY = y + 20; // Starting Y position after the box header (or padding)
                    for (String line : commentLines) {
                        g.drawString(line, x + 5, commentY); // Add padding to the left (x + 5)
                        commentY += 20; // Increment Y position for each new line
                    }

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
        private String[] wrapTextToFitWidth(String text, Graphics g, int maxWidth) {
            // Split the text into words
            String[] words = text.split(" ");
            StringBuilder currentLine = new StringBuilder();
            List<String> lines = new ArrayList<>();

            for (String word : words) {
                // Check the width of the current line with the new word
                if (g.getFontMetrics().stringWidth(currentLine + word) <= maxWidth) {
                    currentLine.append(word).append(" ");
                } else {
                    // If the word doesn't fit, add the current line to the list and start a new line
                    lines.add(currentLine.toString().trim());
                    currentLine = new StringBuilder(word + " ");
                }
            }

            // Add the last line if it's not empty
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString().trim());
            }

            // Return the wrapped lines as an array
            return lines.toArray(new String[0]);
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
        new ClassDiagramSelectable();
    }
}
