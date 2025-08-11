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
            System.out.println("0. Back");
            choice = InputUtil.getInt(sc, "Enter choice: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    addDoctor();
                    pause();
                }
                case 2 -> {
                    showAll();
                    pause();
                }
                case 3 -> {
                    searchSpecialization();
                    pause();
                }
                case 0 -> System.out.println("Returning to main menu...");
                default -> {
                    System.out.println("Invalid.");
                    pause();
                }
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
        System.out.printf("%-5s %-15s %-20s %-20s %-12s %-10s%n",
                "ID", "IC", "Name", "Specialization", "Duty Day", "Shift");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Doctor d : docs) {
            System.out.printf("%-5s %-15s %-20s %-20s %-12s %-10s%n",
                    d.getDoctorId(),
                    d.getIdentificationCard(),
                    d.getName(),
                    d.getSpecialization(),
                    d.getDutyDay(),
                    d.getShiftTime());
        }
    }

    private void searchSpecialization() {
        clearScreen();
        System.out.print("Enter specialization: ");
        String spec = sc.nextLine();
        Doctor[] result = control.searchBySpecialization(spec);
        if (result.length == 0) {
            System.out.println("No doctors with that specialization.");
        } else {
            for (Doctor d : result) System.out.println(d);
        }
    }
}
