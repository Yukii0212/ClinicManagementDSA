package clinic;

import clinic.boundary.*;
import clinic.control.*;
import clinic.entity.*;

import static clinic.util.ConsoleUtil.clearScreen;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final PatientControl patientControl = new PatientControl(20);
    private static final DoctorControl doctorControl = new DoctorControl(20);
    private static final ConsultationControl consultationControl = new ConsultationControl(20);
    private static final MedicalRecordControl medicalRecordControl = new MedicalRecordControl(50);

    public static void main(String[] args) {
        int choice;
        do {
            clearScreen();
            System.out.println("\n=== Clinic Management System ===");
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Consultation Management");
            System.out.println("4. Medical Record Management");
            System.out.println("0. Exit");
            System.out.print("Enter option: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> new PatientUI().run();
                case 2 -> new DoctorUI().run();
                case 3 -> new ConsultationUI().run(
                        patientControl.getAllPatients(),
                        doctorControl.getAllDoctors()
                );
                case 4 -> new MedicalRecordUI().run(
                        patientControl.getAllPatients(),
                        doctorControl.getAllDoctors()
                );
                case 0 -> System.out.println("Thank you. Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }
}
