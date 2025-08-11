package clinic.boundary;

import clinic.control.DoctorControl;
import clinic.control.MedicalRecordControl;
import clinic.control.PatientControl;
import clinic.entity.Doctor;
import clinic.entity.MedicalRecord;
import clinic.entity.Patient;
import static clinic.util.ConsoleUtil.*;
import clinic.util.InputUtil;
import java.util.Scanner;

public class MedicalRecordUI {
    private final MedicalRecordControl control;
    private final PatientControl patientControl;
    private final DoctorControl doctorControl;
    private final Scanner sc = new Scanner(System.in);

    public MedicalRecordUI(MedicalRecordControl control, PatientControl patientControl, DoctorControl doctorControl) {
        this.control = control;
        this.patientControl = patientControl;
        this.doctorControl = doctorControl;
    }

    public void run() {
        int choice;
        do {
            clearScreen();
            System.out.println("\n--- Medical Record Management ---");
            System.out.println("1. Add Record (manual)");
            System.out.println("2. View All Records");
            System.out.println("0. Back");
            choice = InputUtil.getInt(sc, "Enter option: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> { addRecord(); pause(); }
                case 2 -> { viewAll(); pause(); }
                case 0 -> System.out.println("Returning to main..."); 
                default -> { System.out.println("Invalid."); pause(); }
            }
        } while (choice != 0);
    }

    private void addRecord() {
        clearScreen();
        System.out.println("Enter 'X' at any prompt to cancel.");

        System.out.print("Patient ID: ");
        String patientId = sc.nextLine();
        if (patientId.equalsIgnoreCase("X")) return;

        System.out.print("Doctor ID: ");
        String doctorId = sc.nextLine();
        if (doctorId.equalsIgnoreCase("X")) return;

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        if (date.equalsIgnoreCase("X")) return;

        System.out.print("Diagnosis: ");
        String diagnosis = sc.nextLine();
        if (diagnosis.equalsIgnoreCase("X")) return;

        System.out.print("Treatment: ");
        String treatment = sc.nextLine();
        if (treatment.equalsIgnoreCase("X")) return;

        boolean ok = control.addRecord(patientId, doctorId, date, diagnosis, treatment);
        System.out.println(ok ? "Medical record added successfully." : "Failed to add record.");
    }

    private void viewAll() {
        clearScreen();
        MedicalRecord[] recs = control.getAllRecords();
        if (recs.length == 0) {
            System.out.println("No medical records found.");
            return;
        }

        System.out.println("=== Medical Records ===");
        System.out.printf("%-8s %-15s %-15s %-12s %-20s %-20s%n",
                "Rec ID", "Patient", "Doctor", "Date", "Diagnosis", "Treatment");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (MedicalRecord r : recs) {
            String patientName = "Unknown";
            String doctorName = "Unknown";

            Patient p = patientControl.findById(r.getPatientId());
            if (p != null) patientName = p.getName();

            Doctor d = doctorControl.findById(r.getDoctorId());
            if (d != null) doctorName = d.getName();

            System.out.printf("%-8s %-15s %-15s %-12s %-20s %-20s%n",
                    r.getRecordId(),
                    patientName + " (" + r.getPatientId() + ")",
                    doctorName + " (" + r.getDoctorId() + ")",
                    r.getDate(),
                    r.getDiagnosis(),
                    r.getTreatment());
        }
    }
}
