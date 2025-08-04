package clinic.control;

import clinic.adt.CircularQueue;
import clinic.entity.Patient;

public class PatientControl {
    private CircularQueue<Patient> patientQueue;

    public PatientControl(int capacity) {
        this.patientQueue = new CircularQueue<>(capacity);
    }

    public boolean addPatient(Patient p) {
        if (patientQueue.isFull()) return false;
        patientQueue.enqueue(p);
        return true;
    }

    public Patient consultNextPatient() {
        if (patientQueue.isEmpty()) return null;
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

    public int totalPatients() {
        return patientQueue.size();
    }

    public boolean isQueueFull() {
        return patientQueue.isFull();
    }

    public boolean isQueueEmpty() {
        return patientQueue.isEmpty();
    }
}