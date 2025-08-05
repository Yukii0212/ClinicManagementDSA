package clinic.control;

import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.entity.Doctor;

public class ConsultationControl {
    private Consultation[] consultations;
    private int count;

    public ConsultationControl(int capacity) {
        consultations = new Consultation[capacity];
        count = 0;
    }

    public boolean scheduleConsultation(String id, Patient patient, Doctor doctor, String date, String time, String notes) {
        if (count >= consultations.length) return false;
        consultations[count++] = new Consultation(id, patient, doctor, date, time, notes);
        return true;
    }

    public Consultation[] getAllConsultations() {
        Consultation[] result = new Consultation[count];
        System.arraycopy(consultations, 0, result, 0, count);
        return result;
    }

    public int totalConsultations() {
        return count;
    }
}
