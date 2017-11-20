package com.momocorp.charihelp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wg13w on 10/7/2017.
 */

public class Tutor extends User{

    public String isRecommended;
    public ArrayList<Appointments> appointments;
    public ArrayList<String> subjectTaught;
    public float ratings;
    public Tutor()
    {
        super();

    }


    public Tutor(String firstName, String lastName, String uid, ArrayList<String> subject) {
        super(firstName, lastName, uid);
        this.subjectTaught = subject;
    }
}
