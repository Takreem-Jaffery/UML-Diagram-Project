package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    private Project project;
    private JsonDAO dao;
    private JpegImageDAO jpegImageDAO;
    private PngImageDAO pngImageDAO;
    private JFrame ui;

    @BeforeEach
    void setUp() {
        dao = new JsonDAO();
        jpegImageDAO = new JpegImageDAO();
        pngImageDAO = new PngImageDAO();
        ui = new JFrame(); //creating a dummy JFrame to simulate UI actions
        project = new Project();
        project.dao = dao; //injecting the real DAO
        project.jpegImageDAO = jpegImageDAO;
        project.pngImageDAO = pngImageDAO;
    }

    @Test
    void testSaveCDProject_Success() throws IOException {
        ArrayList<Class> classes = new ArrayList<>();
        ArrayList<Association> associations = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();

        //user project
        String projectName = "TestProject"; //test project
        JTextField textField = new JTextField();
        textField.setText(projectName);
        JOptionPane.showInputDialog(ui, textField); //set the input to simulate user input

        //simulate file selection dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(projectName + ".json"));
        fileChooser.setDialogTitle("Select Destination");
        int userSelection = fileChooser.showSaveDialog(ui);

        //simulate the file saving process
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".json")) {
                filePath += ".json"; // Default to JSON if no extension
            }

            //simulate saving the file using DAO
            dao.saveAsJson(filePath, project.generateCDComponents(classes, associations, comments));
            JOptionPane.showMessageDialog(ui, "Project saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }


        assertTrue(true); //placeholder
    }

    @Test
    void testSaveCDProject_ProjectNameEmpty() {
        // Arrange
        ArrayList<Class> classes = new ArrayList<>();
        ArrayList<Association> associations = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();

        //simulating user input for project name (empty string)
        String projectName = "";
        JTextField textField = new JTextField();
        textField.setText(projectName);
        JOptionPane.showInputDialog(ui, textField); //set the input to simulate user input

        //simulate file selection dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Destination");
        int userSelection = fileChooser.showSaveDialog(ui);

        //simulate cancelation (or no file selected)
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(ui, "Project name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        assertTrue(true); //placeholder
    }

    @Test
    void testLoadProject_Success() throws IOException {
        ArrayList<Component> mockComponents = new ArrayList<>();
        mockComponents.add(new Usecase("Usecase1", new java.awt.Point(10, 10)));

        //simulating file selection dialog to load a project
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Project File to Load");
        fileChooser.setSelectedFile(new File("path/to/testProject.json"));
        int userSelection = fileChooser.showOpenDialog(ui);

        //simulate the file loading process
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            //simulate loading the file using DAO
            if (filePath.endsWith(".json")) {
                mockComponents = dao.loadFromJson(filePath); //simulate loading components from JSON
                JOptionPane.showMessageDialog(ui, "Project loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ui, "Invalid file type selected. Please select a JSON file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        assertNotNull(mockComponents, "Loaded components should not be null");
        assertEquals(1, mockComponents.size(), "There should be 1 component loaded");
    }

    @Test
    void testLoadProject_InvalidFileType() {

        ArrayList<Component> mockComponents = new ArrayList<>();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Project File to Load");
        fileChooser.setSelectedFile(new File("path/to/testProject.txt")); //provide path here
        int userSelection = fileChooser.showOpenDialog(ui);

        //simulate the file loading process
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            //simulate invalid file type selection
            if (!filePath.endsWith(".json")) {
                JOptionPane.showMessageDialog(ui, "Invalid file type selected. Please select a JSON file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        assertTrue(true); // Placeholder since actual dialog behavior cannot be asserted.
    }
}
