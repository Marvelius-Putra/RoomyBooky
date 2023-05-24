package com.example.roomybooky.Data.User;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ArrayBookingRequested extends ViewModel {
  public static ArrayList<BookingRequested> bookingRequesteds;

  public static ArrayList<BookingRequested> getInstance() {
    if (bookingRequesteds == null) {
      bookingRequesteds = new ArrayList<BookingRequested>();
    }
    return bookingRequesteds;
  }

  public static ArrayList<BookingRequested> getHistory() {
    return bookingRequesteds;
  }

  public static void addBookingRequested(BookingRequested bookingRequested) {
    bookingRequesteds.add(bookingRequested);
  }

  public void setMenuList(ArrayList<BookingRequested> bookingRequested) {
    bookingRequesteds = bookingRequested;
  }
}
