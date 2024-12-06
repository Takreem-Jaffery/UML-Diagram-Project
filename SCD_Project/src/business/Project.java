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
 *
 * @author hp
 */
// Project Class
public class Project {
    private String name;
    private String path;
    private Model model;
    private CodeGenerator codeGenerator;
    private JsonDAO dao;
    private JpegImageDAO jpegImageDAO;
    private PngImageDAO pngImageDAO;

    public Project(){
        dao=new JsonDAO();
        jpegImageDAO=new JpegImageDAO();
        pngImageDAO=new PngImageDAO();
    }
    public void saveCDProject(ArrayList<Class> classes, ArrayList<Association> associations, ArrayList<Comment> comments, JFrame ui){
        ArrayList<Component> components=generateCDComponents(classes,associations,comments);

        //prompt user for project name
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

        //choose destination folder and file format
        //for now only JSON works
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

            //generate and save the file using dao
            try {
                if (filePath.endsWith(".json")) { //DAO Saves to json file here
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
    public void saveUCDProject(ArrayList<Usecase> usecases, ArrayList<Actor> actors, ArrayList<UseCaseArrow> arrows, JFrame ui){
        ArrayList<Component> components=generateUCDComponents(usecases,actors,arrows);

        //prompt user for project name
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

        //choose destination folder and file format
        //for now only JSON works
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

            //generate and save the file using dao
            try {
                if (filePath.endsWith(".json")) { //DAO Saves to json file here
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
    public ArrayList<Component> generateUCDComponents(ArrayList<Usecase> usecases, ArrayList<Actor> actors, ArrayList<UseCaseArrow> arrows){
        ArrayList<Component>components=new ArrayList<>();
        components.addAll(usecases);
        components.addAll(actors);
        components.addAll(arrows);
        return components;
    }
    public ArrayList<Component> generateCDComponents(ArrayList<Class> classes, ArrayList<Association> associations, ArrayList<Comment> comments){
        ArrayList<Component>components=new ArrayList<>();
        components.addAll(classes);
        components.addAll(associations);
        components.addAll(comments);
        return components;
    }

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
    public void exportToPNG(String filePath,float width, float height,ClassDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width,height);
        pngImageDAO.exportToPNG(filePath,image);
    }

    public void exportToJPEG(String filePath,float width, float height,ClassDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width,height);
        jpegImageDAO.exportToJPEG(filePath,image);
    }
    public void exportToPNG(String filePath,float width, float height,UseCaseDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width,height);
        pngImageDAO.exportToPNG(filePath,image);
    }

    public void exportToJPEG(String filePath,float width, float height,UseCaseDiagramUI ui) throws IOException {
        BufferedImage image = ui.exportToBufferedImage(width,height);
        jpegImageDAO.exportToJPEG(filePath,image);
    }

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
}


