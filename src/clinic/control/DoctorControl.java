package clinic.control;

import clinic.adt.CircularQueue;
import clinic.adt.QueueADT;
import clinic.entity.Doctor;
import clinic.util.FileUtil;
import clinic.util.IDGenerator;

public class DoctorControl {
    private final QueueADT<Doctor> queue;
    private final QueueADT<Doctor> emergencyQueue;
    private final int capacity;
    private final String FILE_PATH = "data/doctors.txt";

    public DoctorControl(int capacity) {
        this.capacity = capacity;
        this.queue = new CircularQueue<>(capacity);
        this.emergencyQueue = new CircularQueue<>(capacity); // new
        loadFromFile();
    }

    public boolean addDoctor(String identificationCard, String name, String specialization, String dutyDay, String shiftTime) {
        if (existsByIC(identificationCard)) {
            System.out.println("A doctor with this Identification Card already exists.");
            return false;
        }

        if (queue.isFull()) return false;

        String doctorId = IDGenerator.generateID(FILE_PATH, "D");
        Doctor d = new Doctor(doctorId, identificationCard, name, specialization, dutyDay, shiftTime);
        queue.enqueue(d);
        emergencyQueue.enqueue(d); 
        FileUtil.appendLine(FILE_PATH, serializeDoctor(d));
        return true;
    }

    public Doctor[] getAllDoctors() {
        return toArray(queue);
    }

    private String serializeDoctor(Doctor d) {
        return d.getDoctorId() + "," + d.getIdentificationCard() + "," + d.getName() + "," +
                d.getSpecialization() + "," + d.getDutyDay() + "," + d.getShiftTime();
    }

    public int getCapacity() {
        return capacity;
    }

    public Doctor[] searchBySpecialization(String specialization) {
        Doctor[] arr = getAllDoctors();
        int matchCount = 0;
        Doctor[] matches = new Doctor[arr.length];
        for (Doctor d : arr) {
            if (d.getSpecialization().equalsIgnoreCase(specialization)) {
                matches[matchCount++] = d;
            }
        }
        Doctor[] result = new Doctor[matchCount];
        System.arraycopy(matches, 0, result, 0, matchCount);
        return result;
    }

    private void loadFromFile() {
        String[] lines = FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6 && !queue.isFull()) {
                Doctor d = new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                queue.enqueue(d);
                emergencyQueue.enqueue(d);
            }
        }
    }

    public Doctor callNextEmergencyDoctor() {
        if (emergencyQueue.isEmpty()) {
            return null;
        }
        Doctor d = emergencyQueue.dequeue();
        emergencyQueue.enqueue(d);
        return d;
    }

    public Doctor[] getEmergencyQueue() {
        return toArray(emergencyQueue);
    }

    public Doctor findById(String doctorId) {
        Doctor found = null;
        QueueADT<Doctor> temp = new CircularQueue<>(capacity);

        while (!queue.isEmpty()) {
            Doctor d = queue.dequeue();
            if (d.getDoctorId().equalsIgnoreCase(doctorId)) {
                found = d;
            }
            temp.enqueue(d);
        }

        while (!temp.isEmpty()) {
            queue.enqueue(temp.dequeue());
        }

        return found;
    }

    private boolean existsByIC(String ic) {
        boolean exists = false;
        QueueADT<Doctor> temp = new CircularQueue<>(capacity);

        while (!queue.isEmpty()) {
            Doctor d = queue.dequeue();
            if (d.getIdentificationCard().equalsIgnoreCase(ic)) {
                exists = true;
            }
            temp.enqueue(d);
        }

        while (!temp.isEmpty()) {
            queue.enqueue(temp.dequeue());
        }

        return exists;
    }

    public int totalDoctors() {
        return queue.size();
    }
    
    public boolean addToEmergencyQueue(String doctorId) {
        Doctor d = findById(doctorId);
        if (d == null) {
            return false;
        }
        if (emergencyQueue.isFull()) {
            return false;
        }
        emergencyQueue.enqueue(d);
        return true;
    }

    private Doctor[] toArray(QueueADT<Doctor> q) {
        Doctor[] arr = new Doctor[q.size()];
        QueueADT<Doctor> temp = new CircularQueue<>(capacity);
        int i = 0;
        while (!q.isEmpty()) {
            Doctor d = q.dequeue();
            arr[i++] = d;
            temp.enqueue(d);
        }
        while (!temp.isEmpty()) {
            q.enqueue(temp.dequeue());
        }
        return arr;
    }
}
