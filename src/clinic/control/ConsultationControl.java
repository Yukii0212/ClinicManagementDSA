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
        medicalRecordControl.addRecord(
                p.getPatientId(),
                doctor.getDoctorId(),
                today,
                diagnosis,
                treatment
        );
        System.out.println("Consultation recorded for patient: " + p.getName());
    }
}
