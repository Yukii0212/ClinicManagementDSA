package clinic.control;

import clinic.entity.Doctor;

public class DoctorControl {
    private Doctor[] doctors;
    private int count;

    public DoctorControl(int capacity) {
        doctors = new Doctor[capacity];
        count = 0;
    }

    public boolean addDoctor(Doctor d) {
        if (count >= doctors.length) return false;
        doctors[count++] = d;
        return true;
    }

    public Doctor[] getAllDoctors() {
        Doctor[] result = new Doctor[count];
        System.arraycopy(doctors, 0, result, 0, count);
        return result;
    }

    public Doctor[] searchBySpecialization(String specialization) {
        Doctor[] matches = new Doctor[count];
        int matchCount = 0;
        for (int i = 0; i < count; i++) {
            if (doctors[i].getSpecialization().equalsIgnoreCase(specialization)) {
                matches[matchCount++] = doctors[i];
            }
        }

        Doctor[] result = new Doctor[matchCount];
        System.arraycopy(matches, 0, result, 0, matchCount);
        return result;
    }

    public int totalDoctors() {
        return count;
    }
}
