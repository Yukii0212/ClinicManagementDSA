package clinic.control;

import clinic.entity.Doctor;
import clinic.util.FileUtil;

public class DoctorControl {
    private Doctor[] doctors;
    private int count;
    private final String FILE_PATH = "data/doctors.txt";

    public DoctorControl(int capacity) {
        doctors = new Doctor[capacity];
        count = 0;
        loadFromFile();
    }

    public boolean addDoctor(Doctor d) {
        if (count >= doctors.length) return false;
        doctors[count++] = d;
        FileUtil.appendLine(FILE_PATH, serializeDoctor(d));
        return true;
    }

    public Doctor[] getAllDoctors() {
        Doctor[] copy = new Doctor[count];
        for (int i = 0; i < count; i++) {
            copy[i] = doctors[i];
        }
        return copy;
    }

    private String serializeDoctor(Doctor d) {
        return d.getDoctorId() + "," + d.getName() + "," +
               d.getSpecialization() + "," + d.getDutyDay() + "," + d.getShiftTime();
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

    private void loadFromFile() {
        String[] lines = FileUtil.readLines(FILE_PATH);
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split(",");
            if (parts.length == 5) {
                doctors[count++] = new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
        }
    }

    public int totalDoctors() {
        return count;
    }
}
