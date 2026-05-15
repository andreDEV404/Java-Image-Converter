import java.util.Arrays;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    
    public static void main (String[] args) {
        Scanner input = new Scanner (System.in); // Scanner inizialization

        // declaration of the input and output directories as variables
        File inputDir = new File("input_images");
        File outputDir = new File("output_images");

        // if they don't exists create them
        if (!inputDir.exists()) {
            inputDir.mkdir();
        }
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String[] supportedExtensions = {"jpeg", "jpg", "png", "bmp", "gif", "tiff", "wbmp"};

        System.out.println("\n----------Java Image Converter----------\n");
        System.out.println("Place the images you need to convert in the 'input_images' folder.");
        System.out.print("Enter target fomat " + Arrays.toString(supportedExtensions) + ": ");
        String fileType = input.nextLine().toLowerCase().trim();

        while (!Arrays.stream(supportedExtensions).anyMatch(fileType::equals)) {
            System.out.print("Select one of the options above: ");
            fileType = input.nextLine().toLowerCase().trim();
        }

        // process files
        File[] allFiles = inputDir.listFiles();
        
        // if the folder is empty show error message
        if (allFiles == null || allFiles.length == 0) {
            System.out.println("\nERROR: no files found in the 'input_images' folder.\n");
            
            System.out.println("Press Enter to exit...");
            input.nextLine();
            
            input.close();
            return;
        }
        
        System.out.println();

        int successCount = 0;
        for (File f : allFiles) {
            if (f.isFile() && isImage(f, supportedExtensions)) {
                
                if (f.getName().toLowerCase().endsWith(".gif")) {
                    System.out.println("NOTICE: '" + f.getName() + "' is a gif, conversion will only capture the first frame due to API limitations!");
                }
                
                try {
                    convertImage(f, fileType, outputDir);
                    successCount++;
                } catch (IOException e) {
                    System.out.println("Error processing " + f.getName() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("\nSuccessfully converted " + successCount + " images.");
        System.out.println("Check the 'output_images' folder for the converted images.");

        // automatically opens the output folder
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(outputDir);
            } catch (IOException e) {
                System.out.println("Could not automatically open the output folder.");
            }
        }

        System.out.println("\nPress Enter to exit...");
        input.nextLine();
        
        input.close();

    }

    private static boolean isImage(File file, String[] extensions) {
        String name = file.getName().toLowerCase();
        return Arrays.stream(extensions).anyMatch(ext -> name.endsWith("." + ext));
    }

    private static void convertImage(File inputFile, String format, File outputDir) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) return;

        // check if we are moving to a format that doesn't support transparency
        if (format.equals("jpg") || format.equals("jpeg") || format.equals("bmp")) {
            BufferedImage newImage = new BufferedImage(
                    image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            
            var g = newImage.createGraphics();
            g.setColor(java.awt.Color.WHITE); // can be changed to black
            g.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
            g.drawImage(image, 0, 0, null);
            g.dispose();
            image = newImage;
        }

        // strip extension and swap for the target format
        String fileName = inputFile.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

        File outputFile = new File(outputDir, baseName + "." + format);

        // avoids overwriting
        int counter = 1;
        while (outputFile.exists()) {
            outputFile = new File(outputDir, baseName + " (" + counter + ")." + format);
            counter++;
        }

        ImageIO.write(image, format, outputFile);
        System.out.println("Converted: " + fileName + " -> " + outputFile.getName());

    }

}