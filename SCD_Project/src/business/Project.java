package business;

import data.JpegImageDAO;
import data.JsonDAO;
import data.PngImageDAO;
import ui.ClassDiagramUI;
import ui.UseCaseDiagramUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Project class handles the saving, loading, and exporting of project data.
 * It manages the creation and storage of various project components such as
 * class diagrams and use case diagrams. The class also integrates with
 * Data Access Object (DAO) classes for saving/loading data and image
 * exporters for exporting diagram images in different formats.
 */
public class Project {
    private String name;
    private String path;
    private Model model;
    private CodeGenerator codeGenerator;
    private JsonDAO dao;
    private JpegImageDAO jpegImageDAO;
    private PngImageDAO pngImageDAO;

    /**
     * Constructor for initializing the Project with default DAOs.
     */
    public Project(){
        dao = new JsonDAO();
        jpegImageDAO = new JpegImageDAO();
        pngImageDAO = new PngImageDAO();
    }

    /**
     * Saves the class diagram project by prompting the user for a project name
     * and destination folder. The project data (classes, associations, comments)
     * are saved in JSON format.
     *
     * @param classes      List of classes in the class diagram.
     * @param associations List of associations in the class diagram.
     * @param comments     List of comments in the class diagram.
     * @param ui           The UI frame to interact with the user.
     */
    public void saveCDProject(ArrayList<Class> classes, ArrayList<Association> associations, ArrayList<Comment> comments, JFrame ui){
        ArrayList<Component> components = generateCDComponents(classes, associations, comments);

        // Prompt user for project name
        String projectName = JOptionPane.showInputDialog(
                ui,
                "Enter Project Name:",
                "Save Project",
                JOptionPane.PLAIN_MESSAGE
        );

        if (projectName == null || projectName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui,
                    "Project name cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Choose destination folder and file format
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Destination");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        fileChooser.setSelectedFile(new File(projectName)); // Pre-fill project name

        int userSelection = fileChooser.showSaveDialog(ui);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".json")) {
                filePath += ".json"; // Default to JSON if no extension
            }

            // Generate and save the file using dao
            try {
                if (filePath.endsWith(".json")) {
                    dao.saveAsJson(filePath, components);
                }
                JOptionPane.showMessageDialog(ui,
                        "Project saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ui,
                        "Failed to save the project: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Saves the use case diagram project by prompting the user for a project name
     * and destination folder. The project data (usecases, actors, arrows, systems)
     * are saved in JSON format.
     *
     * @param usecases List of use cases in the use case diagram.
     * @param actors   List of actors in the use case diagram.
     * @param arrows   List of arrows in the use case diagram.
     * @param systems  List of systems in the use case diagram.
     * @param ui       The UI frame to interact with the user.
     */
    public void saveUCDProject(ArrayList<Usecase> usecases, ArrayList<Actor> actors, ArrayList<UseCaseArrow> arrows, ArrayList<UCDSystem> systems, JFrame ui){
        ArrayList<Component> components = generateUCDComponents(usecases, actors, arrows, systems);

        // Prompt user for project name
        String projectName = JOptionPane.showInputDialog(
                ui,
                "Enter Project Name:",
                "Save Project",
                JOptionPane.PLAIN_MESSAGE
        );

        if (projectName == null || projectName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(ui,
                    "Project name cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Choose destination folder and file format
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Destination");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        fileChooser.setSelectedFile(new File(projectName)); // Pre-fill project name

        int userSelection = fileChooser.showSaveDialog(ui);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".json")) {
                filePath += ".json"; // Default to JSON if no extension
            }

            // Generate and save the file using dao
            try {
                if (filePath.endsWith(".json")) {
                    dao.saveAsJson(filePath, components);
                }
                JOptionPane.showMessageDialog(ui,
                        "Project saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ui,
                        "Failed to save the project: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Generates a list of components (usecases, actors, arrows, systems)
     * for a use case diagram project.
     *
     * @param usecases List of use cases in the diagram.
     * @param actors   List of actors in the diagram.
     * @param arrows   List of arrows in the diagram.
     * @param systems  List of systems in the diagram.
     * @return A list of components for the use case diagram project.
     */
    public ArrayList<Component> generateUCDComponents(ArrayList<Usecase> usecases, ArrayList<Actor> actors, ArrayList<UseCaseArrow> arrows, ArrayList<UCDSystem> systems){
        ArrayList<Component> components = new ArrayList<>();
        components.addAll(usecases);
        components.addAll(actors);
        components.addAll(arrows);
        components.addAll(systems);
        return components;
    }

    /**
     * Generates a list of components (classes, associations, comments)
     * for a class diagram project.
     *
     * @param classes      List of classes in the diagram.
     * @param associations List of associations in the diagram.
     * @param comments     List of comments in the diagram.
     * @return A list of components for the class diagram project.
     */
    public ArrayList<Component> generateCDComponents(ArrayList<Class> classes, ArrayList<Association> associations, ArrayList<Comment> comments){
        ArrayList<Component> components = new ArrayList<>();
        components.addAll(classes);
        components.addAll(associations);
        components.addAll(comments);
        return components;
    }

    /**
     * Loads a project from a selected JSON file.
     *
     * @param ui The UI frame to interact with the user.
     * @return A list of components loaded from the selected project file.
     */
    public ArrayList<business.Component> loadProject(JFrame ui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Project File to Load");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

        int userSelection = fileChooser.showOpenDialog(ui);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            // Validate file extension
            if (!filePath.endsWith(".json")) {
                JOptionPane.showMessageDialog(ui,
                        "Invalid file type selected. Please select a JSON file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Load the file using dao
            try {
                ArrayList<business.Component> components = dao.loadFromJson(filePath);
                JOptionPane.showMessageDialog(ui,
                        "Project loaded successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                return components;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ui,
                        "Failed to load the project: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null; // Return null if no file was loaded
    }

    /**
     * Exports the class diagram UI to a PNG image.
     *
     * @param filePath The path where the PNG image will be saved.
     * @param width    The width of the exported image.
     * @param height   The height of the exported image.
     * @param ui       The class diagram UI to export.
     * @throws IOException If an error occurs during the export.
     */
    public void exportToPNG(String filePath, float width, float height, ClassDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width, height);
        pngImageDAO.exportToPNG(filePath, image);
    }

    /**
     * Exports the class diagram UI to a JPEG image.
     *
     * @param filePath The path where the JPEG image will be saved.
     * @param width    The width of the exported image.
     * @param height   The height of the exported image.
     * @param ui       The class diagram UI to export.
     * @throws IOException If an error occurs during the export.
     */
    public void exportToJPEG(String filePath, float width, float height, ClassDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width, height);
        jpegImageDAO.exportToJPEG(filePath, image);
    }

    /**
     * Exports the use case diagram UI to a PNG image.
     *
     * @param filePath The path where the PNG image will be saved.
     * @param width    The width of the exported image.
     * @param height   The height of the exported image.
     * @param ui       The use case diagram UI to export.
     * @throws IOException If an error occurs during the export.
     */
    public void exportToPNG(String filePath, float width, float height, UseCaseDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width, height);
        pngImageDAO.exportToPNG(filePath, image);
    }

    /**
     * Exports the use case diagram UI to a JPEG image.
     *
     * @param filePath The path where the JPEG image will be saved.
     * @param width    The width of the exported image.
     * @param height   The height of the exported image.
     * @param ui       The use case diagram UI to export.
     * @throws IOException If an error occurs during the export.
     */
    public void exportToJPEG(String filePath, float width, float height, UseCaseDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width, height);
        jpegImageDAO.exportToJPEG(filePath, image);
    }

    // Getter and setter methods for DAOs

    public JsonDAO getDao() {
        return dao;
    }

    public void setDao(JsonDAO dao) {
        this.dao = dao;
    }

    public JpegImageDAO getJpegImageDAO() {
        return jpegImageDAO;
    }

    public void setJpegImageDAO(JpegImageDAO jpegImageDAO) {
        this.jpegImageDAO = jpegImageDAO;
    }

    public PngImageDAO getPngImageDAO() {
        return pngImageDAO;
    }

    public void setPngImageDAO(PngImageDAO pngImageDAO) {
        this.pngImageDAO = pngImageDAO;
    }

    public void saveUCDProject(ArrayList<Usecase> usecases, ArrayList<Actor> actors, ArrayList<UseCaseArrow> arrows, UseCaseDiagramUI useCaseDiagramUI) {
    }
}
