package clinic.boundary;

import clinic.control.ConsultationControl;
import clinic.control.DoctorControl;
import clinic.control.PatientControl;
import clinic.entity.Doctor;
import clinic.entity.Patient;
import static clinic.util.ConsoleUtil.*;
import java.util.Scanner;

public class ConsultationUI {
    private final ConsultationControl control;
    private final PatientControl patientControl;
    private final DoctorControl doctorControl;
    private final Scanner sc = new Scanner(System.in);

    public ConsultationUI(ConsultationControl control, PatientControl patientControl, DoctorControl doctorControl) {
        this.control = control;
        this.patientControl = patientControl;
        this.doctorControl = doctorControl;
    }

    public void run() {
        clearScreen();
        if (patientControl.isQueueEmpty()) {
            System.out.println("No patients in the queue to consult.");
            pause();
            return;
        }

        Patient next = patientControl.peekNextPatient();
        if (next == null) {
            System.out.println("No available patient.");
            pause();
            return;
        }

        System.out.println("=== Next Patient to be Consulted ===");
        System.out.println("--------------------------------------------------");
        System.out.println("ID       : " + next.getPatientId());
        System.out.println("IC       : " + next.getIdentificationCard());
        System.out.println("Name     : " + next.getName());
        System.out.println("Age      : " + next.getAge());
        System.out.println("Gender   : " + next.getGender());
        System.out.println("Phone    : " + next.getPhone());
        System.out.println("Illness  : " + next.getIllnessDescription());
        System.out.println("--------------------------------------------------\n");

        Doctor[] doctors = doctorControl.getAllDoctors();
        if (doctors.length == 0) {
            System.out.println("No doctors available. Add a doctor first.");
            pause();
            return;
        }

        System.out.println("=== Available Doctors ===");
        for (int i = 0; i < doctors.length; i++) {
            Doctor d = doctors[i];
            System.out.println("--------------------------------------------------");
            System.out.println((i + 1) + ". Name     : " + d.getName());
            System.out.println("   ID       : " + d.getDoctorId());
            System.out.println("   IC       : " + d.getIdentificationCard());
            System.out.println("   Special. : " + d.getSpecialization());
            System.out.println("   Duty Day : " + d.getDutyDay());
            System.out.println("   Shift    : " + d.getShiftTime());
        }
        System.out.println("--------------------------------------------------");

        System.out.println();
        System.out.print("Select Doctor (or 'X' to cancel): ");

        String docInput = sc.nextLine();
        if (docInput.equalsIgnoreCase("X")) return;
        int docChoice;
        try {
            docChoice = Integer.parseInt(docInput) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid doctor selection.");
            pause();
            return;
        }
        if (docChoice < 0 || docChoice >= doctors.length) {
            System.out.println("Invalid doctor selection.");
            pause();
            return;
        }

        Doctor doctor = doctors[docChoice];

        System.out.print("Diagnosis (or 'X' to cancel): ");
        String diagnosis = sc.nextLine();
        if (diagnosis.equalsIgnoreCase("X")) return;

        System.out.print("Treatment (or 'X' to cancel): ");
        String treatment = sc.nextLine();
        if (treatment.equalsIgnoreCase("X")) return;

        control.consultNextPatient(doctor, diagnosis, treatment);
        pause();
    }
}
