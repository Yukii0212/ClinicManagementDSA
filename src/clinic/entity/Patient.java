package clinic.entity;

public class Patient {
    private String patientId; 
    private String identificationCard; 
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String illnessDescription;

    public Patient(String patientId, String identificationCard, String name, int age, String gender, String phone, String illnessDescription) {
        this.patientId = patientId;
        this.identificationCard = identificationCard;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.illnessDescription = illnessDescription;
    }

    public String getPatientId() { return patientId; }
    public String getIdentificationCard() { return identificationCard; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getIllnessDescription() { return illnessDescription; }

    @Override
    public String toString() {
        return String.format("Patient ID: %s | Name: %s | Age: %d | Gender: %s | Phone: %s | Illness: %s",
                patientId, name, age, gender, phone, illnessDescription);
    }
}