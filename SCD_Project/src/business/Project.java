///TJ COMMENTED CODE BECAUSE IT WAS GIVING ERRORS THAT AREN'T CURRENTLY RESOLVED
///
///
///
///
/// *
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package business;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import static javax.swing.plaf.nimbus.ImageScalingHelper.paint;
//
///**
// *
// * @author hp
// */
//// Project Class
//public class Project {
//    private String name;
//    private String path;
//    private Model model;
//    private CodeGenerator codeGenerator;
//
//  public void exportToPNG(String filePath) throws IOException {
//            BufferedImage image = exportToBufferedImage();
//            ImageIO.write(image, "png", new File(filePath));
//        }
//        /**
//         * Exports the use case diagram as a JPEG image
//         * @param filePath Path where the JPEG will be saved
//         * @throws IOException If there's an error writing the file
//         */
//        public void exportToJPEG(String filePath) throws IOException {
//            BufferedImage image = exportToBufferedImage();
//            ImageIO.write(image, "jpg", new File(filePath));
//        }
//
//        /**
//         * Creates a BufferedImage of the entire diagram
//         * @return BufferedImage of the diagram
//         */
//        private BufferedImage exportToBufferedImage() {
//            // Create a buffered image of the diagram's exact size
//            BufferedImage image = new BufferedImage(getWidth(), getHeight(),
//                    BufferedImage.TYPE_INT_RGB);
//
//            // Create graphics context
//            Graphics2D g2d = image.createGraphics();
//
//            // Optional: Set rendering hints for better quality
//            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                    RenderingHints.VALUE_ANTIALIAS_ON);
//            g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//                    RenderingHints.VALUE_RENDER_QUALITY);
//
//            // Paint the diagram onto the buffered image
//            paint(g2d);
//
//            // Dispose of graphics context
//            g2d.dispose();
//
//            return image;
//        }
//}
//
//
