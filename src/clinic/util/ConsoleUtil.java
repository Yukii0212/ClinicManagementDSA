package clinic.util;

public class ConsoleUtil {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause() {
        System.out.println("\nPress Enter to continue...");
        try {
            System.in.read();
        } catch (Exception ignored) {}
    }
}
