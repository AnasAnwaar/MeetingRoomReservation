package com.example.meetingroomreservation;

import java.util.Date;

public class Reservation {
    private long id;
    private String reservedFor;
    private String roomNo;
    private String meetingType;
    private String agenda;
    private Date date;
    private String timing;
    private String status;

    public Reservation(long id, String reservedFor, String roomNo, String meetingType, String agenda, Date date, String timing, String status) {
        this.id = id;
        this.reservedFor = reservedFor;
        this.roomNo = roomNo;
        this.meetingType = meetingType;
        this.agenda = agenda;
        this.date = date;
        this.timing = timing;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getReservedFor() {
        return reservedFor;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public String getAgenda() {
        return agenda;
    }

    public int getDate() {
        return date;
    }

    public String getTiming() {
        return timing;
    }

    public String getStatus() {
        return status;
    }
}
