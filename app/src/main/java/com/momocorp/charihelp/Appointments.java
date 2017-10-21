package com.momocorp.charihelp;

/**
 * Created by Mitco Technology on 10/21/2017.
 */

public class Appointments extends User {
    public Appointments()
    {

    }
    public String name;
    public Date date;
    public String subject;
    public Time time;
    public String appointmentUID;
    public static class Date {
        public Date(){

        }

        public Date(int day, int month, int year)
        {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        public int day, month, year;
    }

    public static class Time {
        int hour;
        int minute;
        public Time()
        {

        }

        public Time(int hour, int minute)
        {
            this.hour = hour;
            this.minute = minute;
        }
    }
}
