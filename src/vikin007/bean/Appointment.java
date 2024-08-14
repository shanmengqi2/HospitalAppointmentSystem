package vikin007.bean;

public class Appointment {
    private String patientName;
    private String patientID;
    private Doctor doctor;
    private Schedule schedule;

    public Appointment(String patientName, String patientID, Doctor doctor, Schedule schedule) {
        this.patientName = patientName;
        this.patientID = patientID;
        this.doctor = doctor;
        this.schedule = schedule;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
