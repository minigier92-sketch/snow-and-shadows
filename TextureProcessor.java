import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TextureProcessor {
    public static void main(String[] args) throws Exception {
        String brainDir = "C:\\Users\\New\\.gemini\\antigravity-ide\\brain\\94d8eb91-0398-44b3-86ec-5d3274333b1b\\";
        String outDir = "c:\\Users\\New\\Desktop\\MyRobloxGame\\SnowAndShadows\\src\\main\\resources\\assets\\snowshadows\\textures\\item\\";
        new File(outDir).mkdirs();

        process(brainDir, "ghost_lantern", outDir);
        process(brainDir, "fox_tail", outDir);
        process(brainDir, "elven_bow", outDir);
        process(brainDir, "succubus_scroll", outDir);
    }

    private static void process(String inDir, String name, String outDir) throws Exception {
        // Find the generated jpg file
        File dir = new File(inDir);
        File[] files = dir.listFiles((d, f) -> f.startsWith(name) && f.endsWith(".jpg"));
        if (files == null || files.length == 0) return;
        File inFile = files[0];

        BufferedImage img = ImageIO.read(inFile);
        BufferedImage out = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(img, 0, 0, 32, 32, null);
        g.dispose();

        // Make black/dark background transparent
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                int rgb = out.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g_col = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                if (r < 25 && g_col < 25 && b < 25) { // Threshold for black
                    out.setRGB(x, y, 0x00000000);
                }
            }
        }

        File outFile = new File(outDir + name + ".png");
        ImageIO.write(out, "png", outFile);
        System.out.println("Processed " + name + " -> " + outFile.getAbsolutePath());
    }
}
