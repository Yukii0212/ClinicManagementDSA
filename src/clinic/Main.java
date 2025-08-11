package clinic;

import clinic.boundary.*;
import clinic.control.*;
import static clinic.util.ConsoleUtil.*;
import clinic.util.InputUtil;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final PatientControl patientControl = new PatientControl(200);
    private static final DoctorControl doctorControl = new DoctorControl(100);
    private static final MedicalRecordControl medicalRecordControl = new MedicalRecordControl(1000);
    private static final ConsultationControl consultationControl =
            new ConsultationControl(patientControl, medicalRecordControl);

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

            choice = InputUtil.getInt(sc, "Enter option: ");
            sc.nextLine(); 

            switch (choice) {
                case 1 -> new PatientUI(patientControl).run();
                case 2 -> new DoctorUI(doctorControl).run();
                case 3 -> new ConsultationUI(consultationControl, patientControl, doctorControl).run();
                case 4 -> new MedicalRecordUI(medicalRecordControl, patientControl, doctorControl).run();
                case 0 -> System.out.println("Thank you. Exiting...");
                default -> {
                    System.out.println("Invalid choice.");
                    pause();
                }
            }
        } while (choice != 0);
    }
}
