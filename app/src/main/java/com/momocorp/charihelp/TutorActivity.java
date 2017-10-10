package com.momocorp.charihelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class TutorActivity extends AppCompatActivity {
    RecyclerView tutorRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);
        tutorRecyclerView = findViewById(R.id.tutor_recycler);
        UserAdapter userAdapter = new UserAdapter();
        tutorRecyclerView.setAdapter(userAdapter);

        //create recyclerview for tutor
    }
}
