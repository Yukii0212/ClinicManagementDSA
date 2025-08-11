package clinic.boundary;

import clinic.control.ConsultationControl;
import clinic.control.DoctorControl;
import clinic.control.PatientControl;
import clinic.entity.Doctor;
import clinic.entity.Patient;
import static clinic.util.ConsoleUtil.*;
import clinic.util.InputUtil;
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

        System.out.println("Next patient to be consulted:");
        System.out.println(next);
        System.out.println();

        Doctor[] doctors = doctorControl.getAllDoctors();
        if (doctors.length == 0) {
            System.out.println("No doctors available. Add a doctor first.");
            pause();
            return;
        }

        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.length; i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, doctors[i].getName(), doctors[i].getDoctorId());
        }

        int docChoice = InputUtil.getInt(sc, "Choose doctor number: ") - 1;
        sc.nextLine();
        if (docChoice < 0 || docChoice >= doctors.length) {
            System.out.println("Invalid doctor selection.");
            pause();
            return;
        }
        Doctor doctor = doctors[docChoice];

        System.out.print("Diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("Treatment: ");
        String treatment = sc.nextLine();

        control.consultNextPatient(doctor, diagnosis, treatment);
        pause();
    }
}
