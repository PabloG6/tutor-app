package com.momocorp.charihelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class TutorActivity extends AppCompatActivity {
    RecyclerView tutorRecyclerView;
    TextView noAppointments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        tutorRecyclerView = (RecyclerView) findViewById(R.id.tutor_recycler);
        noAppointments = (TextView) findViewById(R.id.no_appointments_text);
        UserAdapter userAdapter = new UserAdapter();
        tutorRecyclerView.setAdapter(userAdapter);
        tutorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //create recyclerview for tutor
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
