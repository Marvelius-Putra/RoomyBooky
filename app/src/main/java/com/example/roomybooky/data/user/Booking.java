package com.example.roomybooky.data.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Booking implements Parcelable {
  private String username;
  private String email;
  private String nim;
  private String room;
  private int floor;
  private String category;
  private String date;
  private int member;
  private String startTime;
  private String endTime;
  private String description;
  private String status;
  private String bookingID;

  public Booking() {}

  public Booking(
    String username,
    String email,
    String nim,
    String room,
    int floor,
    String category,
    String date,
    int member,
    String startTime,
    String endTime,
    String description,
    String status,
    String bookingID
  ) {
    this.username = username;
    this.email = email;
    this.nim = nim;
    this.room = room;
    this.floor = floor;
    this.category = category;
    this.date = date;
    this.member = member;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
    this.status = status;
    this.bookingID = bookingID;
  }

  protected Booking(Parcel in) {
    username = in.readString();
    email = in.readString();
    nim = in.readString();
    room = in.readString();
    floor = in.readInt();
    category = in.readString();
    date = in.readString();
    member = in.readInt();
    startTime = in.readString();
    endTime = in.readString();
    description = in.readString();
    status = in.readString();
    bookingID = in.readString();
  }

  public static final Creator<Booking> CREATOR = new Creator<Booking>() {
    @Override
    public Booking createFromParcel(Parcel in) {
      return new Booking(in);
    }

    @Override
    public Booking[] newArray(int size) {
      return new Booking[size];
    }
  };

  public String getTimeFormat() {
    return getStartTime() + " - " + getEndTime();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getMember() {
    return member;
  }

  public void setMember(int member) {
    this.member = member;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBookingID() {
    return bookingID;
  }

  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel dest, int flags) {
    dest.writeString(username);
    dest.writeString(email);
    dest.writeString(nim);
    dest.writeString(room);
    dest.writeInt(floor);
    dest.writeString(category);
    dest.writeString(date);
    dest.writeInt(member);
    dest.writeString(startTime);
    dest.writeString(endTime);
    dest.writeString(description);
    dest.writeString(status);
    dest.writeString(bookingID);
  }
}
