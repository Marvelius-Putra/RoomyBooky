package com.example.roomybooky.data;

import java.util.Map;

public class Room {
    private int capacity;
    private String category;
    private int floor;
    private String room;
    private Map<String, String> availability;
    private String roomID;


    public Room() { }

    public Room(int capacity, String category, int floor, String room, Map<String, String> availability, String roomID) {
        this.capacity = capacity;
        this.category = category;
        this.floor = floor;
        this.room = room;
        this.availability = availability;
        this.roomID = roomID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Map<String, String> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, String> availability) {
        this.availability = availability;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }






}

