package clinic.util;

import java.io.*;

public class FileUtil {

    public static String[] readLines(String filename) {
        String[] lines = new String[1000];
        int count = 0;

        File file = new File(filename);
        if (!file.exists()) return new String[0];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines[count++] = line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename);
        }

        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = lines[i];
        }
        return result;
    }

    public static void appendLine(String filename, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + filename);
        }
    }
}
