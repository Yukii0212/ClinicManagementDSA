package clinic.control;

import clinic.entity.Doctor;
import clinic.util.FileUtil;
import clinic.util.IDGenerator;

public class DoctorControl {
    private Doctor[] doctors;
    private int count;
    private final String FILE_PATH = "data/doctors.txt";

    public DoctorControl(int capacity) {
        doctors = new Doctor[capacity];
        count = 0;
        loadFromFile();
    }

    public boolean addDoctor(String identificationCard, String name, String specialization, String dutyDay, String shiftTime) {
        if (existsByIC(identificationCard)) {
            System.out.println("A doctor with this Identification Card already exists.");
            return false;
        }

        String doctorId = IDGenerator.generateID(FILE_PATH, "D");
        Doctor d = new Doctor(doctorId, identificationCard, name, specialization, dutyDay, shiftTime);

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
        return d.getDoctorId() + "," + d.getIdentificationCard() + "," + d.getName() + "," +
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
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                doctors[count++] = new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            }
        }
    }

    public Doctor findById(String doctorId) {
        String[] lines = clinic.util.FileUtil.readLines(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 1 && parts[0].equalsIgnoreCase(doctorId)) {
                return new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
            }
        }
        return null;
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

    public int totalDoctors() {
        return count;
    }
}
