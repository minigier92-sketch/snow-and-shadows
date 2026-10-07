package dev.snowshadows.util;

import dev.snowshadows.util.RpaReader;
import java.io.File;
import java.io.FileOutputStream;

public class TestRpa {
    public static void main(String[] args) throws Exception {
        File rpaFile = new File("D:\\SteamLibrary\\steamapps\\common\\Chasing Tails -A Promise in the Snow-\\game\\images.rpa");
        System.out.println("Opening RPA...");
        RpaReader reader = new RpaReader(rpaFile);
        
        System.out.println("Available files:");
        for (String key : reader.getKeys()) {
            if (key.contains("rin") && key.contains("sprites")) {
                System.out.println("Match Rin: " + key);
            }
        }
        reader.close();
        
        File lucyRpaFile = new File("D:\\SteamLibrary\\steamapps\\common\\Lucy Got Problems\\game\\images.rpa");
        RpaReader lucyReader = new RpaReader(lucyRpaFile);
        for (String key : lucyReader.getKeys()) {
            if (key.contains("sprites")) {
                if (key.contains("lucy")) System.out.println("Match Lucy: " + key);
                if (key.contains("ellie")) System.out.println("Match Ellie: " + key);
                if (key.contains("thea")) System.out.println("Match Thea: " + key);
            }
        }
        lucyReader.close();
    }
}
