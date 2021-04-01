import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AutoResizer {

    public static void main(String[] args) {
        int height = 0;
        int width = 0;
        boolean verbose = false;
        if (args.length == 3) {
            try {
                verbose = args[0].equalsIgnoreCase("-v");
                width = Integer.parseInt(args[1]);
                height = Integer.parseInt(args[2]);
            } catch (Exception e) {
                System.out
                    .println("Inputs did not match specifications. Expected: -v <width> <height>");
            }
        } else if (args.length == 2) {
            try {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Correct Number of inputs, but inputs were not integer values.");
            }
        } else {
            System.out.println("AutoResizer requires two inputs: <width> <height> (in pixels)\n"
                + "or (-v | -V) <width> <height> (in pixels; verbose opt)");
            return;
        }
        String location = "picturesToResize";
        File picturesFolder = new File(location);
        if (picturesFolder.exists()) {
            if (verbose)
                System.out.println("File found");
        } else {
            System.out.println("File not found.");
            picturesFolder.mkdir();
            System.out.println(
                "Creating file \"picturesToResize\", place pictures to resize in it, then re run.");
        }
        String[] fileNames = picturesFolder.list();
        File outputFolder = new File("resizedImages");
        if (!outputFolder.exists())
            outputFolder.mkdir();
        int imgNum = 0;
        for (String fileName : fileNames) {
            if (verbose)
                System.out.println("Resizing image: " + fileName);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(location + "/" + fileName));
                if (verbose)
                    System.out.println("Succesfully read image.");
                Image resized = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                imgNum++;
                try {
                    if (verbose)
                        System.out.println("Printing image " + imgNum + " of " + fileNames.length);
                    BufferedImage reIm =
                        new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = reIm.createGraphics();
                    g2d.drawImage(resized, 0, 0, null);
                    g2d.dispose();
                    ImageIO.write(reIm, "png", new File("resizedImages" + File.separator
                        + fileName.substring(0, fileName.length() - 4) + ".png"));
                    if (verbose)
                        System.out.println("Printed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("Error resizing image");
            }
        }
        System.out.println("\nFinished Printing.");
    }
}
