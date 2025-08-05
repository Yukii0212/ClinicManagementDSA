package clinic.boundary;

import clinic.control.MedicalRecordControl;
import clinic.control.PatientControl;
import clinic.entity.MedicalRecord;
import clinic.entity.Patient;
import clinic.entity.Doctor;

import java.util.Scanner;

public class MedicalRecordUI {
    private final MedicalRecordControl control;
    private final Scanner sc = new Scanner(System.in);

    public MedicalRecordUI() {
        control = new MedicalRecordControl(50); // arbitrary
    }

    public MedicalRecordUI(MedicalRecordControl controller) {
        this.controller = controller;
    }

    public void run(Patient[] patients, Doctor[] doctors) {
        int choice;
        do {
            System.out.println("\n--- Medical Record Management ---");
            System.out.println("1. Add Record");
            System.out.println("2. View All Records");
            System.out.println("0. Back");
            System.out.print("Enter option: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> add(patients, doctors);
                case 2 -> viewAll();
                case 0 -> System.out.println("Returning to main...");
                default -> System.out.println("Invalid.");
            }
        } while (choice != 0);
    }

    private void add(Patient[] patients, Doctor[] doctors) {
        if (patients.length == 0 || doctors.length == 0) {
            System.out.println("Need at least 1 patient and 1 doctor.");
            return;
        }

        System.out.println("Select Patient:");
        for (int i = 0; i < patients.length; i++) {
            System.out.printf("%d. %s\n", i + 1, patients[i].getName());
        }
        int pIndex = sc.nextInt() - 1; sc.nextLine();

        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.length; i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, doctors[i].getName(), doctors[i].getSpecialization());
        }
        int dIndex = sc.nextInt() - 1; sc.nextLine();

        System.out.print("Record ID: ");
        String id = sc.nextLine();
        System.out.print("Diagnosis: ");
        String diag = sc.nextLine();
        System.out.print("Treatment: ");
        String treat = sc.nextLine();
        System.out.print("Date: ");
        String date = sc.nextLine();

        boolean ok = control.addRecord(id, patients[pIndex], doctors[dIndex], diag, treat, date);
        System.out.println(ok ? "Record saved." : "Storage full.");
    }

    private void viewAll() {
        MedicalRecord[] records = control.getAllRecords();
        if (records.length == 0) {
            System.out.println("No records found.");
        } else {
            for (MedicalRecord r : records) {
                System.out.println("\n" + r);
            }
        }
    }
}
