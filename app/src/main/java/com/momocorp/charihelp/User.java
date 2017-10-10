package com.momocorp.charihelp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wg13w on 10/7/2017.
 */

class User {
    public String uid;
    public String first_name, last_name;
    public ArrayList<String> subjects = new ArrayList<>();
    public ArrayList<Appointments> appointments = new ArrayList<>();

    public User()
    {

    }
    public User(String firstName, String lastName, String uid) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.uid = uid;
    }

    public static class Appointments extends User {
        public Appointments()
        {
            this.name = first_name + " "+ last_name;

        }
        String name;
        Date date;
        String subject;
        Time time;

    }

}
