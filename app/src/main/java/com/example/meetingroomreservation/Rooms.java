package com.example.meetingroomreservation;
public class Rooms {
    private String roomName;
    private boolean isAvailable;

    public Rooms(String roomNumber) {
        this.roomName = "Room " + roomNumber;
        this.isAvailable = true; // By default, room is available when created
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
