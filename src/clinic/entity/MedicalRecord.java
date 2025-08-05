package clinic.entity;

public class MedicalRecord {
    private String recordId;
    private Patient patient;
    private Doctor doctor;
    private String diagnosis;
    private String treatment;
    private String date;

    public MedicalRecord(String recordId, Patient patient, Doctor doctor, String diagnosis, String treatment, String date) {
        this.recordId = recordId;
        this.patient = patient;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    public String getRecordId() {
        return recordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Record ID: %s | Date: %s\nPatient: %s\nDoctor: %s\nDiagnosis: %s\nTreatment: %s",
                recordId, date, patient.getName(), doctor.getName(), diagnosis, treatment);
    }
}
