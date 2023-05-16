package com.example.roomybooky.models;

import java.util.Map;

public class room {
    private int capacity;
    private String category;
    private int floor;
    private String name;
    private Map<String, String> availability;
    private Map<String, Boolean> status;

    public  room(){

    }

    public room(int capacity, String category, int floor, String name, Map<String, String> availability, Map<String, Boolean> status) {
        this.capacity = capacity;
        this.category = category;
        this.floor = floor;
        this.name = name;
        this.availability = availability;
        this.status = status;
    }

    public Map<String, Boolean> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Boolean> status) {
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, String> availability) {
        this.availability = availability;
    }
}