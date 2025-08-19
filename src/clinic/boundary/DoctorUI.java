package clinic.boundary;

import clinic.control.DoctorControl;
import clinic.entity.Doctor;
import static clinic.util.ConsoleUtil.*;
import clinic.util.InputUtil;
import java.util.Scanner;

public class DoctorUI {
    private final DoctorControl control;
    private final Scanner sc = new Scanner(System.in);

    public DoctorUI(DoctorControl control) {
        this.control = control;
    }

    public void run() {
        int choice;
        do {
            clearScreen();
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search by Specialization");
            System.out.println("4. Emergency Doctor Queue");
            System.out.println("0. Back");
            choice = InputUtil.getInt(sc, "Enter choice: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> { addDoctor(); pause(); }
                case 2 -> { showAll(); pause(); }
                case 3 -> { searchSpecialization(); pause(); }
                case 4 -> { emergencyMenu(); }
                case 0 -> System.out.println("Returning to main menu...");
                default -> { System.out.println("Invalid."); pause(); }
            }
        } while (choice != 0);
    }

    private void addDoctor() {
        clearScreen();
        System.out.println("Enter 'X' at any prompt to cancel.");

        System.out.print("Identification Card (IC): ");
        String ic = sc.nextLine();
        if (ic.equalsIgnoreCase("X")) return;

        System.out.print("Name: ");
        String name = sc.nextLine();
        if (name.equalsIgnoreCase("X")) return;

        System.out.print("Specialization: ");
        String spec = sc.nextLine();
        if (spec.equalsIgnoreCase("X")) return;

        System.out.print("Duty Day: ");
        String duty = sc.nextLine();
        if (duty.equalsIgnoreCase("X")) return;

        System.out.print("Shift (Morning/Evening): ");
        String shift = sc.nextLine();
        if (shift.equalsIgnoreCase("X")) return;

        boolean ok = control.addDoctor(ic, name, spec, duty, shift);
        System.out.println(ok ? "Doctor added successfully." : "Failed to add doctor (maybe IC exists or capacity full).");
    }

    private void showAll() {
        clearScreen();
        Doctor[] docs = control.getAllDoctors();
        if (docs.length == 0) {
            System.out.println("No doctors available.");
            return;
        }

        System.out.println("=== List of Doctors ===");
        for (Doctor d : docs) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID          : " + d.getDoctorId());
            System.out.println("IC          : " + d.getIdentificationCard());
            System.out.println("Name        : " + d.getName());
            System.out.println("Specializ.  : " + d.getSpecialization());
            System.out.println("Duty Day    : " + d.getDutyDay());
            System.out.println("Shift       : " + d.getShiftTime());
        }
        System.out.println("--------------------------------------------------");
    }

    private void searchSpecialization() {
        clearScreen();
        System.out.print("Enter specialization: ");
        String spec = sc.nextLine();
        Doctor[] result = control.searchBySpecialization(spec);
        if (result.length == 0) {
            System.out.println("No doctors with that specialization.");
        } else {
            System.out.println("=== Doctors with Specialization: " + spec + " ===");
            for (Doctor d : result) {
                System.out.println("--------------------------------------------------");
                System.out.println("ID          : " + d.getDoctorId());
                System.out.println("IC          : " + d.getIdentificationCard());
                System.out.println("Name        : " + d.getName());
                System.out.println("Specializ.  : " + d.getSpecialization());
                System.out.println("Duty Day    : " + d.getDutyDay());
                System.out.println("Shift       : " + d.getShiftTime());
            }
            System.out.println("--------------------------------------------------");
        }
    }

    private void emergencyMenu() {
        int choice;
        do {
            clearScreen();
            System.out.println("\n--- Emergency Doctor Queue ---");
            System.out.println("1. Call Next Doctor");
            System.out.println("2. View Current Queue");
            System.out.println("0. Back");
            choice = InputUtil.getInt(sc, "Enter choice: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> { callNextDoctor(); pause(); }
                case 2 -> { viewEmergencyQueue(); pause(); }
                case 0 -> {}
                default -> { System.out.println("Invalid."); pause(); }
            }
        } while (choice != 0);
    }

    private void callNextDoctor() {
        Doctor d = control.callNextEmergencyDoctor();
        clearScreen();
        if (d == null) {
            System.out.println("No doctors available in the emergency queue.");
        } else {
            System.out.println("Doctor " + d.getName() + " (" + d.getSpecialization() + ") is now handling an emergency!");
        }
    }

    private void viewEmergencyQueue() {
        Doctor[] docs = control.getEmergencyQueue();
        clearScreen();
        if (docs.length == 0) {
            System.out.println("Emergency queue is empty.");
            return;
        }

        System.out.println("=== Emergency Doctor Queue ===");

        System.out.println("CURRENTLY HANDLING EMERGENCY → " 
                        + docs[0].getName() 
                        + " (" + docs[0].getSpecialization() + ")");

        if (docs.length > 1) {
            System.out.println("NEXT IN LINE → " 
                            + docs[1].getName() 
                            + " (" + docs[1].getSpecialization() + ")");
        }

        if (docs.length > 2) {
            System.out.println("");
            System.out.println("--- Waiting List ---");
            for (int i = 2; i < docs.length; i++) {
                System.out.println((i - 1) + ". " + docs[i].getName() 
                                + " (" + docs[i].getSpecialization() + ")");
            }
        }
    }
}
