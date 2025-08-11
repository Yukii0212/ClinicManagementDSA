package clinic.entity;

public class Doctor {
    private String doctorId;
    private String identificationCard;
    private String name;
    private String specialization;
    private String dutyDay;
    private String shiftTime;

    public Doctor(String doctorId, String identificationCard, String name, String specialization, String dutyDay, String shiftTime) {
        this.doctorId = doctorId;
        this.identificationCard = identificationCard;
        this.name = name;
        this.specialization = specialization;
        this.dutyDay = dutyDay;
        this.shiftTime = shiftTime;
    }

    public String getDoctorId() { return doctorId; }
    public String getIdentificationCard() { return identificationCard; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public String getDutyDay() { return dutyDay; }
    public String getShiftTime() { return shiftTime; }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Specialization: %s | Duty: %s | Shift: %s",
                doctorId, name, specialization, dutyDay, shiftTime);
    }
}
