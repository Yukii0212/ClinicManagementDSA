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
            System.out.println("3. View Processing Queue");
            System.out.println("4. Process Next Record (FIFO)");
            System.out.println("0. Back");
            choice = InputUtil.getInt(sc, "Enter option: ");
            sc.nextLine();

            switch (choice) {
                case 1 -> { addRecord(); pause(); }
                case 2 -> { viewAll(); pause(); }
                case 3 -> { viewQueue(); pause(); }
                case 4 -> { processNext(); pause(); }
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
        
        for (MedicalRecord r : recs) {
            String patientName = "Unknown";
            String doctorName = "Unknown";

            Patient p = patientControl.findById(r.getPatientId());
            if (p != null) patientName = p.getName();

            Doctor d = doctorControl.findById(r.getDoctorId());
            if (d != null) doctorName = d.getName();

            System.out.println("--------------------------------------------------");
            System.out.println("Rec ID     : " + r.getRecordId());
            System.out.println("Patient    : " + patientName + " (" + r.getPatientId() + ")");
            System.out.println("Doctor     : " + doctorName + " (" + r.getDoctorId() + ")");
            System.out.println("Date       : " + r.getDate());
            System.out.println("Diagnosis  : " + r.getDiagnosis());
            System.out.println("Treatment  : " + r.getTreatment());
        }
        System.out.println("--------------------------------------------------");
    }

    private void viewQueue() {
        clearScreen();
        MedicalRecord[] queue = control.getQueueSnapshot();
        if (queue.length == 0) {
            System.out.println("No records in processing queue.");
            return;
        }

        System.out.println("=== Record Processing Queue (FIFO) ===");
        System.out.println("NEXT TO PROCESS → " + queue[0].getRecordId() + " (" + queue[0].getDate() + ")");
        for (int i = 1; i < queue.length; i++) {
            System.out.println((i) + ". " + queue[i].getRecordId() + " (" + queue[i].getDate() + ")");
        }
    }

    private void processNext() {
        clearScreen();
        MedicalRecord next = control.peekNextRecord(); // look at front without removing
        if (next == null) {
            System.out.println("No records available to process.");
            return;
        }

        System.out.println("Next record in queue:");
        System.out.println("Rec ID   : " + next.getRecordId());
        System.out.println("Patient  : " + next.getPatientId());
        System.out.println("Doctor   : " + next.getDoctorId());
        System.out.println("Date     : " + next.getDate());
        System.out.println("Diagnosis: " + next.getDiagnosis());
        System.out.println("Treatment: " + next.getTreatment());

        System.out.print("\nConfirm process this record? (Y/N): ");
        String confirm = sc.nextLine();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Cancelled.");
            return;
        }

        MedicalRecord r = control.processNextRecord();
        System.out.println("Processed and saved record " + r.getRecordId() + " successfully.");
    }

}
