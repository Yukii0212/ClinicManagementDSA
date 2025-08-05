package clinic.boundary;

import clinic.control.DoctorControl;
import clinic.control.PatientControl;
import clinic.entity.Doctor;

import java.util.Scanner;

public class DoctorUI {
    private final DoctorControl control;
    private final Scanner sc = new Scanner(System.in);

    public DoctorUI() {
        control = new DoctorControl(20); // arbitrary capacity
    }

    public DoctorUI(DoctorControl controller) {
        this.controller = controller;
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search by Specialization");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> showAll();
                case 3 -> searchSpecialization();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid.");
            }
        } while (choice != 0);
    }

    private void addDoctor() {
        System.out.print("Doctor ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Specialization: ");
        String spec = sc.nextLine();
        System.out.print("Duty Day: ");
        String duty = sc.nextLine();
        System.out.print("Shift (Morning/Evening): ");
        String shift = sc.nextLine();

        Doctor d = new Doctor(id, name, spec, duty, shift);
        if (control.addDoctor(d)) {
            System.out.println("Doctor added.");
        } else {
            System.out.println("Capacity full.");
        }
    }

    private void showAll() {
        Doctor[] docs = control.getAllDoctors();
        if (docs.length == 0) {
            System.out.println("No doctors available.");
        } else {
            for (Doctor d : docs) {
                System.out.println(d);
            }
        }
    }

    private void searchSpecialization() {
        System.out.print("Enter specialization: ");
        String spec = sc.nextLine();
        Doctor[] result = control.searchBySpecialization(spec);
        if (result.length == 0) {
            System.out.println("No doctors with that specialization.");
        } else {
            for (Doctor d : result) {
                System.out.println(d);
            }
        }
    }
}
