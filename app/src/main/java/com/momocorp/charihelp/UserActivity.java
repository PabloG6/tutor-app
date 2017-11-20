package com.momocorp.charihelp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.momocorp.charihelp.CustomViews.CustomItemDecoration;

public class UserActivity extends AppCompatActivity implements NoTutorListener, DetailsFragment.OnFragmentInteractionListener{
    RecyclerView recyclerView;
    TextView noTutorsText;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =  findViewById(R.id.recycler_view);
        noTutorsText = findViewById(R.id.no_tutors_text);
        final TutorAdapter tutorAdapter = new TutorAdapter(this);
        recyclerView.setAdapter(tutorAdapter);
        noTutorsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorAdapter.checkTutors();
            }
        });
        noTutorsText.setVisibility(View.INVISIBLE);
        LinearLayoutManager lM = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new
                DividerItemDecoration(this, lM.getOrientation());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(dividerItemDecoration);




    }

    @Override
    public void showNoTutor() {
        noTutorsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTutor() {
        noTutorsText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO: 11/20/2017 idk lol
    }
}
