package data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngImageDAO {
    public void exportToPNG(String filePath, BufferedImage image) throws IOException {
        ImageIO.write(image, "png", new File(filePath));
    }
}
