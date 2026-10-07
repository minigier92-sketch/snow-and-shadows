package dev.snowshadows.util;

import net.razorvine.pickle.Unpickler;
import java.io.*;
import java.util.*;
import java.util.zip.InflaterInputStream;

public class RpaReader {
    private final RandomAccessFile file;
    private final Map<String, Object> index;
    private final long obfuscationKey;

    public RpaReader(File rpaFile) throws IOException {
        this.file = new RandomAccessFile(rpaFile, "r");
        
        // Read header: RPA-3.0 offset key
        String header = file.readLine();
        if (header == null || !header.startsWith("RPA-3.0 ")) {
            throw new IOException("Not a valid RPA-3.0 file");
        }
        
        String[] parts = header.split(" ");
        long indexOffset = Long.parseLong(parts[1], 16);
        this.obfuscationKey = Long.parseLong(parts[2], 16);
        
        // Read index
        file.seek(indexOffset);
        InputStream in = new InflaterInputStream(new FileInputStream(file.getFD()));
        Unpickler unpickler = new Unpickler();
        this.index = (Map<String, Object>) unpickler.load(in);
        in.close();
    }

    public byte[] readFile(String internalPath) throws IOException {
        Object entryInfo = index.get(internalPath);
        if (entryInfo == null) {
            throw new FileNotFoundException("File not found in RPA: " + internalPath);
        }
        
        // entryInfo is usually a List of tuples/arrays. We take the first one.
        // Format: [offset, length, start_prefix_length]
        Object[] tuple = (Object[]) ((List<?>) entryInfo).get(0);
        long offset = ((Number) tuple[0]).longValue();
        int length = ((Number) tuple[1]).intValue();
        
        // Read data
        byte[] data = new byte[length];
        file.seek(offset);
        file.readFully(data);
        
        // Deobfuscate
        if (obfuscationKey != 0) {
            for (int i = 0; i < Math.min(36, data.length); i++) {
                data[i] ^= (byte) (obfuscationKey >> ((i % 4) * 8));
            }
        }
        
        return data;
    }
    
    public Set<String> getKeys() {
        return index.keySet();
    }
    
    public void close() throws IOException {
        file.close();
    }
}
