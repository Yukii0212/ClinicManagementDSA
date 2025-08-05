package clinic.entity;

public class Consultation {
    private String consultationId;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String notes;

    public Consultation(String consultationId, Patient patient, Doctor doctor, String date, String time, String notes) {
        this.consultationId = consultationId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return String.format("Consultation ID: %s | Date: %s %s\nPatient: %s\nDoctor: %s\nNotes: %s",
                consultationId, date, time,
                patient.getName(), doctor.getName(), notes);
    }
}
