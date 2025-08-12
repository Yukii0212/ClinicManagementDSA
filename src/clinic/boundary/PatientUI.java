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
        System.out.println("Enter 'X' at any prompt to cancel.");
        System.out.print("Identification Card (IC) or Name: ");
        String searchInput = sc.nextLine().trim();
        if (searchInput.equalsIgnoreCase("X")) return;

        Patient selectedPatient = null;
        if (searchInput.matches(".*\\d.*")) {
            // contains digit -> assume IC search
            selectedPatient = controller.searchByIC(searchInput);
            if (selectedPatient == null) {
                System.out.println("No patient found with IC: " + searchInput);
            }
        } else {
            // name search (partial)
            Patient[] matches = controller.searchByNamePartial(searchInput);
            if (matches.length == 0) {
                System.out.println("No patient found with name containing: " + searchInput);
            } else {
                clearScreen();
                System.out.println("Multiple matches found:");
                for (int i = 0; i < matches.length; i++) {
                    System.out.printf("%d. %s (IC: %s) - ID: %s%n",
                            i + 1,
                            matches[i].getName(),
                            matches[i].getIdentificationCard(),
                            matches[i].getPatientId());
                }
                System.out.println("0. Register new patient with this name");
                System.out.println("");
                System.out.println("X or -1. Cancel/Exit");
                System.out.println("");

                String choiceInput;
                int choice = -2;
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Select patient number (0 for new, X/-1 to exit): ");
                    choiceInput = sc.nextLine().trim();
                    if (choiceInput.equalsIgnoreCase("X") || choiceInput.equals("-1")) {
                        return;
                    }
                    try {
                        choice = Integer.parseInt(choiceInput);
                        if (choice == 0 || (choice > 0 && choice <= matches.length)) {
                            validInput = true;
                        } else {
                            System.out.println("Invalid selection. Please try again.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number, X, or -1.");
                    }
                }

                if (choice == 0) {
                    selectedPatient = null; // Proceed to register new patient
                } else if (choice > 0 && choice <= matches.length) {
                    selectedPatient = matches[choice - 1];
                } else {
                    System.out.println("Invalid selection.");
                    pause();
                    return;
                }
            }
        }

        if (selectedPatient != null) {
            System.out.println("\nSelected patient:");
            System.out.println(selectedPatient);
            System.out.print("Enter new illness description (or 'X' to cancel): ");
            String newIllness = sc.nextLine();
            if (newIllness.equalsIgnoreCase("X")) return;

            Patient updatedPatient = new Patient(
                    selectedPatient.getPatientId(),
                    selectedPatient.getIdentificationCard(),
                    selectedPatient.getName(),
                    selectedPatient.getAge(),
                    selectedPatient.getGender(),
                    selectedPatient.getPhone(),
                    newIllness
            );

            boolean reQueued = controller.enqueueExisting(updatedPatient);
            System.out.println(reQueued ? "Patient re-added to queue successfully."
                    : "Failed to re-add patient (maybe already in queue or queue full).");
            pause();
            return;
        }

        String icForNew;
            if (searchInput.matches(".*\\d.*")) {
                System.out.print("Use \"" + searchInput + "\" as Identification Card (IC)? (Y/n or 'X' to cancel): ");
                String use = sc.nextLine().trim();

            if (use.equalsIgnoreCase("X")) return;

            if (use.equalsIgnoreCase("n") || use.equalsIgnoreCase("N")) {
                clearScreen();
                System.out.println("Register New Patient (Enter 'x' at any moment to exit) ");
                System.out.print("Identification Card (IC): ");
                icForNew = sc.nextLine().trim();
                if (icForNew.equalsIgnoreCase("X")) return;
            } else {
                icForNew = searchInput;
            }
        } else {
            System.out.print("Identification Card (IC): ");
            icForNew = sc.nextLine().trim();
            if (icForNew.equalsIgnoreCase("X")) return;
        }

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

        boolean ok = controller.addPatient(icForNew, name, age, gender, phone, illness);
        if (!ok) {
            System.out.println("Failed to register patient. This IC may already exist or the queue is full.");
        } else {
            System.out.println("Patient registered successfully and added to queue.");
        }
        pause();
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