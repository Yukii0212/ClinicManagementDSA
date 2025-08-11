package clinic.boundary;

import clinic.control.PatientControl;
import clinic.entity.Patient;
import static clinic.util.ConsoleUtil.*;
import clinic.util.InputUtil;
import java.util.Scanner;

public class PatientUI {
    private final PatientControl controller;
    private final Scanner sc = new Scanner(System.in);

    public PatientUI(PatientControl controller) {
        this.controller = controller;
    }

    public void run() {
        int choice;
        do {
            clearScreen();
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Register Patient");
            System.out.println("2. View Patient Queue");
            System.out.println("3. View Previously Registered Patients");
            System.out.println("0. Back");

            choice = InputUtil.getInt(sc, "Select option: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> { registerPatient(); pause(); }
                case 2 -> { viewQueue(); pause(); }
                case 3 -> { viewPreviouslyRegistered(); pause(); }
                case 0 -> System.out.println("Returning to main menu...");
                default -> { System.out.println("Invalid choice."); pause(); }
            }
        } while (choice != 0);
    }

    private void registerPatient() {
        System.out.println("Enter 'X' at any prompt to cancel.");
        System.out.print("Identification Card (IC): ");
        String ic = sc.nextLine();
        if (ic.equalsIgnoreCase("X")) return;

        System.out.print("Name: ");
        String name = sc.nextLine();
        if (name.equalsIgnoreCase("X")) return;

        int age = InputUtil.getInt(sc, "Age: ");
        sc.nextLine();

        System.out.print("Gender: ");
        String gender = sc.nextLine();
        if (gender.equalsIgnoreCase("X")) return;

        System.out.print("Phone: ");
        String phone = sc.nextLine();
        if (phone.equalsIgnoreCase("X")) return;

        System.out.print("Illness description: ");
        String illness = sc.nextLine();
        if (illness.equalsIgnoreCase("X")) return;

        boolean ok = controller.addPatient(ic, name, age, gender, phone, illness);
        System.out.println(ok ? "Patient registered successfully." : "Failed to register patient (maybe queue full or IC exists).");
    }

    private void viewQueue() {
        Patient[] list = controller.getAllPatients();
        if (list.length == 0) {
            System.out.println("No patients in queue.");
            return;
        }
        System.out.println("Patients in queue:");
        for (int i = 0; i < list.length; i++) {
            System.out.printf("%d. %s\n", i + 1, list[i]);
        }
    }

    private void viewPreviouslyRegistered() {
        clearScreen();
        Patient[] allPatients = controller.getAllPatientsFromFile();
        if (allPatients.length == 0) {
            System.out.println("No patients registered yet.");
            return;
        }
        System.out.println("Previously registered patients:");
        System.out.printf("%-5s %-15s %-20s %-3s %-6s %-12s %s%n",
                "ID", "IC", "Name", "Age", "Gender", "Phone", "Illness");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Patient p : allPatients) {
            System.out.printf("%-5s %-15s %-20s %-3d %-6s %-12s %s%n",
                    p.getPatientId(),
                    p.getIdentificationCard(),
                    p.getName(),
                    p.getAge(),
                    p.getGender(),
                    p.getPhone(),
                    p.getIllnessDescription());
        }
    }
}
