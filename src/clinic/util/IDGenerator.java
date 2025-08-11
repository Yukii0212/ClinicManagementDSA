package clinic.util;

import java.io.*;

public class IDGenerator {

    public static String generateID(String filePath, String prefix) {
        int max = 0;

        File file = new File(filePath);
        if (!file.exists()) return prefix + String.format("%03d", 1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts[0].startsWith(prefix)) {
                        int num = Integer.parseInt(parts[0].substring(prefix.length()));
                        if (num > max) max = num;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for ID generation: " + filePath);
        }

        return prefix + String.format("%03d", max + 1);
    }
}
