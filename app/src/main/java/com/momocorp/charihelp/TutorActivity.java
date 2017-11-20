package com.momocorp.charihelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
        tutorRecyclerView =  findViewById(R.id.tutor_recycler);
        noAppointments =  findViewById(R.id.no_appointments_text);
        AppointmentsAdapter app = new AppointmentsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        tutorRecyclerView.setAdapter(app);
        tutorRecyclerView.setLayoutManager(linearLayoutManager);
        tutorRecyclerView.addItemDecoration(dividerItemDecoration);



        //create recyclerview for tutor
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
