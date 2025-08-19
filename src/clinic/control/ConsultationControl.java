package clinic.control;

import clinic.entity.Doctor;
import clinic.entity.Patient;
import java.time.LocalDate;

public class ConsultationControl {
    private PatientControl patientControl;
    private MedicalRecordControl medicalRecordControl;

    public ConsultationControl(PatientControl patientControl, MedicalRecordControl medicalRecordControl) {
        this.patientControl = patientControl;
        this.medicalRecordControl = medicalRecordControl;
    }

    public void consultNextPatient(Doctor doctor, String diagnosis, String treatment) {
        Patient p = patientControl.consultNextPatient();
        if (p == null) {
            System.out.println("No patients in queue.");
            return;
        }
        String today = LocalDate.now().toString();

        boolean ok = medicalRecordControl.enqueuePendingRecord(
                p.getPatientId(),
                doctor.getDoctorId(),
                today,
                diagnosis,
                treatment
        );

        if (!ok) {
            System.out.println("Failed to queue medical record (queue full).");
            return;
        }

        patientControl.removeFromQueueFile(p.getPatientId());
        System.out.println("Consultation completed and record queued for processing: " + p.getName());
    }
}
