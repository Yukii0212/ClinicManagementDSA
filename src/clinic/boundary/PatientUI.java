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
        clearScreen();
        System.out.println("--- Register / Re-Enqueue Patient ---");
        System.out.println("1. Register new patient");
        System.out.println("2. Search and re-add existing patient to queue");
        System.out.print("Select option (or 'X' to cancel): ");
        String choiceInput = sc.nextLine();
        if (choiceInput.equalsIgnoreCase("X")) return;

        int choice;
        try {
            choice = Integer.parseInt(choiceInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            pause();
            return;
        }

        if (choice == 2) {
            // Search existing patients
            System.out.print("Search by (1) IC or (2) Name: ");
            String searchInput = sc.nextLine();
            if (searchInput.equalsIgnoreCase("X")) return;

            int searchChoice;
            try {
                searchChoice = Integer.parseInt(searchInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                pause();
                return;
            }

            Patient found = null;

            if (searchChoice == 1) {
                System.out.print("Enter IC: ");
                String icSearch = sc.nextLine();
                if (icSearch.equalsIgnoreCase("X")) return;
                found = controller.searchByIC(icSearch);

            } else if (searchChoice == 2) {
                System.out.print("Enter Name: ");
                String nameSearch = sc.nextLine();
                if (nameSearch.equalsIgnoreCase("X")) return;

                Patient[] matches = controller.searchByName(nameSearch);
                if (matches.length == 0) {
                    System.out.println("No matching patient found.");
                    pause();
                    return;
                } else if (matches.length > 1) {
                    System.out.println("Multiple matches found:");
                    for (int i = 0; i < matches.length; i++) {
                        System.out.printf("%d. %s (IC: %s, ID: %s)\n",
                                i + 1, matches[i].getName(),
                                matches[i].getIdentificationCard(),
                                matches[i].getPatientId());
                    }
                    int pick = InputUtil.getInt(sc, "Select: ") - 1;
                    sc.nextLine();
                    if (pick >= 0 && pick < matches.length) {
                        found = matches[pick];
                    }
                } else {
                    found = matches[0];
                }
            }

            if (found != null) {
                System.out.println("Patient found:");
                System.out.println(found);
                System.out.print("Add to queue? (Y/N): ");
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    boolean ok = controller.enqueueExisting(found);
                    System.out.println(ok ? "Added to queue." : "Patient already in queue or queue full.");
                }
            } else {
                System.out.println("No matching patient found.");
            }
            pause();
            return;
        }

        if (choice != 1) {
            System.out.println("Invalid choice.");
            pause();
            return;
        }

        // Original NEW patient registration flow
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
        clearScreen();
        Patient[] list = controller.getAllPatients();
        if (list.length == 0) {
            System.out.println("No patients in queue.");
            return;
        }
        System.out.println("=== Patients in Queue ===");
        for (Patient p : list) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID       : " + p.getPatientId());
            System.out.println("IC       : " + p.getIdentificationCard());
            System.out.println("Name     : " + p.getName());
            System.out.println("Age      : " + p.getAge());
            System.out.println("Gender   : " + p.getGender());
            System.out.println("Phone    : " + p.getPhone());
            System.out.println("Illness  : " + p.getIllnessDescription());
        }
        System.out.println("--------------------------------------------------");
    }

    private void viewPreviouslyRegistered() {
        clearScreen();
        Patient[] allPatients = controller.getAllPatientsFromFile();
        if (allPatients.length == 0) {
            System.out.println("No patients registered yet.");
            return;
        }
        System.out.println("=== Previously Registered Patients ===");
        for (Patient p : allPatients) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID       : " + p.getPatientId());
            System.out.println("IC       : " + p.getIdentificationCard());
            System.out.println("Name     : " + p.getName());
            System.out.println("Age      : " + p.getAge());
            System.out.println("Gender   : " + p.getGender());
            System.out.println("Phone    : " + p.getPhone());
            System.out.println("Illness  : " + p.getIllnessDescription());
        }
        System.out.println("--------------------------------------------------");
    }
}
