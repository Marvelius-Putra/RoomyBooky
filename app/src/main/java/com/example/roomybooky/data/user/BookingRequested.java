package com.example.roomybooky.data.user;

import java.time.LocalDate;

public class BookingRequested {
  private String category;
  private LocalDate date;
  private String description;
  private String email;
  private String endTime;
  private String startTime;
  private String nim;
  private String room;
  private String status;
  private int floor;
  private int member;

  public BookingRequested(String category, LocalDate date, String startTime, String endTime) {
    setRoom(category);
    setDate(date);
    setStartTime(startTime);
    setEndTime(endTime);
  }

  public String getTimeFormat() {
    return getStartTime() + " - " + getEndTime();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getNim() {
    return nim;
  }

  public void setNim(String nim) {
    this.nim = nim;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public int getMember() {
    return member;
  }

  public void setMember(int member) {
    this.member = member;
  }
}
