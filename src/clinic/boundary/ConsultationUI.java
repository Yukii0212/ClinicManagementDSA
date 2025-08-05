package clinic.boundary;

import clinic.control.ConsultationControl;
import clinic.entity.Consultation;
import clinic.entity.Patient;
import clinic.entity.Doctor;
import static clinic.util.ConsoleUtil.*;

import java.util.Scanner;

public class ConsultationUI {
    private final ConsultationControl control;
    private final Scanner sc = new Scanner(System.in);

    public ConsultationUI() {
        control = new ConsultationControl(20); 
    }

    public void run(Patient[] patients, Doctor[] doctors) {
        int choice;
        do {
            clearScreen();
            System.out.println("\n--- Consultation Management ---");
            System.out.println("1. Schedule Consultation");
            System.out.println("2. View All Consultations");
            System.out.println("0. Back");
            System.out.print("Enter option: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> {
                    schedule(patients, doctors);
                    pause();
                }
                case 2 -> {
                    viewAll();
                    pause();
                }
                case 0 -> System.out.println("Back to main menu...");
                default -> System.out.println("Invalid.");
            }
        } while (choice != 0);
    }

    private void schedule(Patient[] patients, Doctor[] doctors) {
        if (patients.length == 0 || doctors.length == 0) {
            System.out.println("Need at least 1 patient and 1 doctor.");
            return;
        }

        System.out.println("Select Patient:");
        for (int i = 0; i < patients.length; i++) {
            System.out.printf("%d. %s\n", i + 1, patients[i].getName());
        }
        int pIndex = sc.nextInt() - 1; sc.nextLine();

        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.length; i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, doctors[i].getName(), doctors[i].getSpecialization());
        }
        int dIndex = sc.nextInt() - 1; sc.nextLine();

        System.out.print("Consultation ID: ");
        String id = sc.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Time: ");
        String time = sc.nextLine();
        System.out.print("Notes: ");
        String notes = sc.nextLine();

        boolean ok = control.scheduleConsultation(id, patients[pIndex], doctors[dIndex], date, time, notes);
        System.out.println(ok ? "Consultation scheduled." : "Schedule full.");
    }

    private void viewAll() {
        Consultation[] all = control.getAllConsultations();
        if (all.length == 0) {
            System.out.println("No consultations scheduled.");
        } else {
            for (Consultation c : all) {
                System.out.println("\n" + c);
            }
        }
    }
}
