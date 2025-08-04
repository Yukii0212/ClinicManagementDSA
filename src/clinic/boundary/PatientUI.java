package clinic.boundary;

import clinic.control.PatientControl;
import clinic.entity.Patient;

import java.util.Scanner;

public class PatientUI {
    private final PatientControl controller;
    private final Scanner sc = new Scanner(System.in);

    public PatientUI() {
        this.controller = new PatientControl(10); 
    }

    public void run() {
        int choice;
        do {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Register Patient");
            System.out.println("2. Consult Next Patient");
            System.out.println("3. View Patient Queue");
            System.out.println("0. Exit");
            System.out.print("Select option: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> consultPatient();
                case 3 -> controller.showAllPatients();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void registerPatient() {
        System.out.print("Patient ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Illness: ");
        String illness = sc.nextLine();

        Patient p = new Patient(id, name, age, gender, phone, illness);
        if (controller.addPatient(p)) {
            System.out.println("Patient registered successfully.");
        } else {
            System.out.println("Queue is full. Cannot register new patient.");
        }
    }

    private void consultPatient() {
        Patient p = controller.consultNextPatient();
        if (p == null) {
            System.out.println("No patients to consult.");
        } else {
            System.out.println("Consulting patient:\n" + p);
        }
    }
}