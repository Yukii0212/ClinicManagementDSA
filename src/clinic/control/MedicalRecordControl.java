package clinic.control;

import clinic.adt.CircularQueue;
import clinic.entity.MedicalRecord;
import clinic.util.FileUtil;
import clinic.util.IDGenerator;

public class MedicalRecordControl {
    private MedicalRecord[] records;
    private int count;
    private final String FILE_PATH = "data/medicalrecords.txt";
    private CircularQueue<MedicalRecord> recordQueue;
    private int capacity;

    public MedicalRecordControl(int capacity) {
        this.capacity = capacity;
        records = new MedicalRecord[capacity];
        count = 0;
        recordQueue = new CircularQueue<>(capacity);
        loadFromFile();
    }

    public boolean addRecord(String patientId, String doctorId, String date, String diagnosis, String treatment) {
        String recordId = IDGenerator.generateID(FILE_PATH, "MR");
        MedicalRecord r = new MedicalRecord(recordId, patientId, doctorId, date, diagnosis, treatment);

        if (count >= records.length) return false;
        records[count++] = r;
        recordQueue.enqueue(r);  // <-- enqueue into queue
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

    public MedicalRecord processNextRecord() {
        if (recordQueue.isEmpty()) {
            return null;
        }
        MedicalRecord r = recordQueue.dequeue();

        FileUtil.appendLine(FILE_PATH, serializeRecord(r));

        if (count < records.length) {
            records[count++] = r;
        }

        return r;
    }

    public MedicalRecord peekNextRecord() {
        if (recordQueue.isEmpty()) return null;
        return recordQueue.peek();
    }

    public MedicalRecord[] getQueueSnapshot() {
        MedicalRecord[] snapshot = new MedicalRecord[recordQueue.size()];
        CircularQueue<MedicalRecord> temp = new CircularQueue<>(capacity);

        int i = 0;
        
        while (!recordQueue.isEmpty()) {
            MedicalRecord r = recordQueue.dequeue();
            snapshot[i++] = r;
            temp.enqueue(r);
        }

        while (!temp.isEmpty()) {
            recordQueue.enqueue(temp.dequeue());
        }

        return snapshot;
    }

    public boolean enqueuePendingRecord(String patientId, String doctorId, String date, String diagnosis, String treatment) {
        if (recordQueue.isFull()) return false;
        String recordId = IDGenerator.generateID(FILE_PATH, "MR");
        MedicalRecord r = new MedicalRecord(recordId, patientId, doctorId, date, diagnosis, treatment);
        recordQueue.enqueue(r);
        return true;
    }

}
