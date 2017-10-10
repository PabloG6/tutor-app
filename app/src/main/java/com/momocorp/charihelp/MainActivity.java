package com.momocorp.charihelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NoTutorListener{
    RecyclerView recyclerView;
    TextView noTutorsText;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recycler_view);
        noTutorsText = findViewById(R.id.no_tutors_text);

        recyclerView.setAdapter(new TutorAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    public void showNoTutor() {
        noTutorsText.setVisibility(View.INVISIBLE);
    }
}
