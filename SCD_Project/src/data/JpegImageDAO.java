package data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A Data Access Object (DAO) class for handling JPEG image files.
 * Provides functionality to export a {@link BufferedImage} to a JPEG file.
 *
 * <p>This class uses the {@link ImageIO} utility for image writing operations.</p>
 */
public class JpegImageDAO {

    /**
     * Exports a given {@link BufferedImage} to a JPEG file at the specified file path.
     *
     * @param filePath the file path where the image will be saved
     * @param image    the {@code BufferedImage} to export
     * @throws IOException if an error occurs during the image writing process
     */
    public void exportToJPEG(String filePath, BufferedImage image) throws IOException {
        ImageIO.write(image, "jpg", new File(filePath));
    }
}
