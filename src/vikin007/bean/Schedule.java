package vikin007.bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    private String doctorID;
    private LocalDate ldt;
    private String AmPm;
    private LocalTime slt;
    private LocalTime elt;
    private int appointmentCapacity;
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Schedule(){

    }
    public Schedule(String doctorID,LocalDate ldt, String AmPm, LocalTime slt,LocalTime elt, int appointmentCapacity) {
        this.doctorID = doctorID;
        this.ldt = ldt;
        this.AmPm = AmPm;
        this.slt = slt;
        this.elt = elt;
        this.appointmentCapacity = appointmentCapacity;
    }

    public String getDoctorName() {
        return doctorID;
    }

    public void setDoctorName(String doctorName) {
        this.doctorID = doctorName;
    }

    public LocalDate getLdt() {
        return ldt;
    }

    public void setLdt(LocalDate ldt) {
        this.ldt = ldt;
    }

    public String getAmPm() {
        return AmPm;
    }

    public void setAmPm(String amPm) {
        AmPm = amPm;
    }

    public LocalTime getSlt() {
        return slt;
    }

    public void setSlt(LocalTime slt) {
        this.slt = slt;
    }

    public LocalTime getElt() {
        return elt;
    }

    public void setElt(LocalTime elt) {
        this.elt = elt;
    }

    public int getAppointmentCapacity() {
        return appointmentCapacity;
    }

    public void setAppointmentCapacity(int appointmentCapacity) {
        this.appointmentCapacity = appointmentCapacity;
    }
}
