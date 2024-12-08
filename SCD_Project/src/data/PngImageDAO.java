package data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A Data Access Object (DAO) class for handling PNG image files.
 * Provides functionality to export a {@link BufferedImage} to a PNG file.
 *
 * <p>This class uses the {@link ImageIO} utility for image writing operations.</p>
 */
public class PngImageDAO {

    /**
     * Exports a given {@link BufferedImage} to a PNG file at the specified file path.
     *
     * @param filePath the file path where the image will be saved
     * @param image    the {@code BufferedImage} to export
     * @throws IOException if an error occurs during the image writing process
     */
    public void exportToPNG(String filePath, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", new File(filePath));
    }
}
