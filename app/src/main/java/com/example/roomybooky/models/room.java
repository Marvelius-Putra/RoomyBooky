package com.example.roomybooky.models;

import java.util.List;

public class room {
    String name;
    Integer floor;
    Integer capacity;
    String category;
    List<Availability> availability;

    public  room(){

    }

    public room(String name, Integer floor, Integer capacity, String category) {
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }
}

