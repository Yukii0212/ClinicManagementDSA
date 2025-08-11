package clinic.entity;

public class MedicalRecord {
    private String recordId;      
    private String patientId;     
    private String doctorId;      
    private String date;
    private String diagnosis;
    private String treatment;

    public MedicalRecord(String recordId, String patientId, String doctorId, String date, String diagnosis, String treatment) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public String getRecordId() { return recordId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDate() { return date; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
}
