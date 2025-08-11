package clinic.control;

import clinic.entity.MedicalRecord;
import clinic.util.FileUtil;
import clinic.util.IDGenerator;

public class MedicalRecordControl {
    private MedicalRecord[] records;
    private int count;
    private final String FILE_PATH = "data/medicalrecords.txt";

    public MedicalRecordControl(int capacity) {
        records = new MedicalRecord[capacity];
        count = 0;
        loadFromFile();
    }

    public boolean addRecord(String patientId, String doctorId, String date, String diagnosis, String treatment) {
        String recordId = IDGenerator.generateID(FILE_PATH, "MR");
        MedicalRecord r = new MedicalRecord(recordId, patientId, doctorId, date, diagnosis, treatment);

        if (count >= records.length) return false;
        records[count++] = r;
        FileUtil.appendLine(FILE_PATH, serializeRecord(r));
        return true;
    }

    public MedicalRecord[] getAllRecords() {
        MedicalRecord[] copy = new MedicalRecord[count];
        for (int i = 0; i < count; i++) {
            copy[i] = records[i];
        }
        return copy;
    }

    private String serializeRecord(MedicalRecord r) {
        return r.getRecordId() + "," + r.getPatientId() + "," + r.getDoctorId() + "," +
               r.getDate() + "," + r.getDiagnosis() + "," + r.getTreatment();
    }

    public int totalRecords() {
        return count;
    }

    private void loadFromFile() {
        String[] lines = FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                records[count++] = new MedicalRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            }
        }
    }
}
