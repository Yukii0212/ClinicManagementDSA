package clinic.entity;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String illnessDescription;

    public Patient(String patientId, String name, int age, String gender, String phone, String illnessDescription) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.illnessDescription = illnessDescription;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getIllnessDescription() {
        return illnessDescription;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIllnessDescription(String illnessDescription) {
        this.illnessDescription = illnessDescription;
    }

    @Override
    public String toString() {
        return String.format("Patient ID: %s | Name: %s | Age: %d | Gender: %s | Phone: %s | Illness: %s",
                patientId, name, age, gender, phone, illnessDescription);
    }
}