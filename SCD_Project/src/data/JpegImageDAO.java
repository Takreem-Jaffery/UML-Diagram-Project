package data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JpegImageDAO {

    public void exportToJPEG(String filePath, BufferedImage image) throws IOException {
        ImageIO.write(image, "jpg", new File(filePath));
    }
}
