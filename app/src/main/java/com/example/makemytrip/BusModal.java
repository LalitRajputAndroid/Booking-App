package com.example.makemytrip;

public class BusModal {
    private String Booking_Date;
    private String passengerName;
    private String From;
    private String To;
    private String Gender;
    private String BusType;

    public BusModal() {
    }

    public BusModal(String booking_Date, String passengerName, String from, String to, String gender, String busType) {
        this.Booking_Date = booking_Date;
        this.passengerName = passengerName;
        this.From = from;
        this.To = to;
        this.Gender = gender;
        this.BusType = busType;
    }

    public String getBooking_Date() {
        return Booking_Date;
    }

    public void setBooking_Date(String booking_Date) {
        Booking_Date = booking_Date;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }
}
