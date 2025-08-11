package clinic.control;

import clinic.adt.CircularQueue;
import clinic.entity.Patient;
import clinic.util.FileUtil;
import clinic.util.IDGenerator;

public class PatientControl {
    private CircularQueue<Patient> patientQueue;
    private int count;
    private final String FILE_PATH = "data/patients.txt";

    public PatientControl(int capacity) {
        patientQueue = new CircularQueue<>(capacity);
        count = 0;
        loadFromFile();
    }

    public boolean addPatient(String identificationCard, String name, int age, String gender, String phone, String illness) {
        if (existsByIC(identificationCard)) {
            System.out.println("A patient with this Identification Card already exists.");
            return false;
        }

        String patientId = IDGenerator.generateID(FILE_PATH, "P");
        Patient p = new Patient(patientId, identificationCard, name, age, gender, phone, illness);

        if (patientQueue.isFull()) return false;
        patientQueue.enqueue(p);
        count++;
        FileUtil.appendLine(FILE_PATH, serializePatient(p));
        return true;
    }

    public Patient consultNextPatient() {
        if (patientQueue.isEmpty()) return null;
        count--;
        return patientQueue.dequeue();
    }

    public Patient peekNextPatient() {
        if (patientQueue.isEmpty()) return null;
        return patientQueue.peek();
    }

    public void showAllPatients() {
        int size = patientQueue.size();
        if (size == 0) {
            System.out.println("No patients in queue.");
            return;
        }

        CircularQueue<Patient> temp = new CircularQueue<>(size);
        for (int i = 0; i < size; i++) {
            Patient p = patientQueue.dequeue();
            System.out.println((i + 1) + ". " + p);
            temp.enqueue(p);
        }

        while (!temp.isEmpty()) {
            patientQueue.enqueue(temp.dequeue());
        }
    }

    public Patient[] getAllPatients() {
        int size = patientQueue.size();
        Patient[] arr = new Patient[size];
        CircularQueue<Patient> temp = new CircularQueue<>(size);

        for (int i = 0; i < size; i++) {
            Patient p = patientQueue.dequeue();
            arr[i] = p;
            temp.enqueue(p);
        }
        while (!temp.isEmpty()) {
            patientQueue.enqueue(temp.dequeue());
        }
        return arr;
    }

    private boolean existsByIC(String ic) {
        String[] lines = FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length > 1 && parts[1].equalsIgnoreCase(ic)) {
                return true;
            }
        }
        return false;
    }

    private String serializePatient(Patient p) {
        return p.getPatientId() + "," + p.getIdentificationCard() + "," + p.getName() + "," +
               p.getAge() + "," + p.getGender() + "," + p.getPhone() + "," + p.getIllnessDescription();
    }

    public int totalPatients() {
        return patientQueue.size();
    }

    public boolean isQueueFull() {
        return patientQueue.isFull();
    }

    public boolean isQueueEmpty() {
        return patientQueue.isEmpty();
    }

    public Patient findById(String patientId) {
        String[] lines = clinic.util.FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 1 && parts[0].equalsIgnoreCase(patientId)) {
                try {
                    return new Patient(parts[0], parts[1], parts[2],
                            Integer.parseInt(parts[3]), parts[4], parts[5], parts[6]);
                } catch (Exception e) { return null; }
            }
        }
        return null;
    }

    private void loadFromFile() {
        String[] lines = FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 7) {
                Patient p = new Patient(parts[0], parts[1], parts[2],
                                        Integer.parseInt(parts[3]),
                                        parts[4], parts[5], parts[6]);
                patientQueue.enqueue(p);
                count++;
            }
        }
    }

    public Patient[] getAllPatientsFromFile() {
        String[] lines = clinic.util.FileUtil.readLines(FILE_PATH);
        Patient[] arr = new Patient[lines.length];
        int idx = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 7) {
                arr[idx++] = new Patient(parts[0], parts[1], parts[2],
                                        Integer.parseInt(parts[3]),
                                        parts[4], parts[5], parts[6]);
            }
        }
        Patient[] result = new Patient[idx];
        System.arraycopy(arr, 0, result, 0, idx);
        return result;
    }
}