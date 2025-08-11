package clinic.util;

import java.util.Scanner;

public class InputUtil {
    public static int getInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); 
            }
        }
    }

    public static double getDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }
        }
    }
}
