package clinic.control;

import clinic.entity.Doctor;
import clinic.entity.MedicalRecord;
import clinic.entity.Patient;

public class MedicalRecordControl {
    private MedicalRecord[] records;
    private int count;

    public MedicalRecordControl(int capacity) {
        this.records = new MedicalRecord[capacity];
        this.count = 0;
    }

    public boolean addRecord(String id, Patient p, Doctor d, String diagnosis, String treatment, String date) {
        if (count >= records.length) return false;
        records[count++] = new MedicalRecord(id, p, d, diagnosis, treatment, date);
        return true;
    }

    public MedicalRecord[] getAllRecords() {
        MedicalRecord[] result = new MedicalRecord[count];
        System.arraycopy(records, 0, result, 0, count);
        return result;
    }

    public int totalRecords() {
        return count;
    }
}
