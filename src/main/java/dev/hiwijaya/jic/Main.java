package dev.hiwijaya.jic;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class Main {

    private static void compressImage() throws IOException {

        ClassLoader classLoader = Main.class.getClassLoader();

        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("image.jpeg")).getFile());
        BufferedImage image = ImageIO.read(inputFile);

        File outputFile = new File(classLoader.getResource(".").getFile() + "/image-compressed.jpeg");
        OutputStream out = new FileOutputStream(outputFile);

        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if(param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.5f);
        }

        writer.write(null, new IIOImage(image, null, null), param);

        out.close();
        ios.close();
        writer.dispose();

        System.out.println("Exported to: " + outputFile.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException {

        compressImage();

    }
}
