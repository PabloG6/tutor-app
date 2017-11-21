package com.momocorp.charihelp;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Pablo Grant on 10/7/2017.
 */

// TODO: 11/20/2017 create custom rating bar
public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.ViewHolder> {
    private static final String TAG = "TUTOR_ADAPTER";
    ArrayList<Tutor> tutorsList = new ArrayList<>();
    private NoTutorListener noTutorListener;
    private DetailsFragment.OnFragmentInteractionListener showFragment;
    private Context context;
    private int iSelected;
    AppCompatActivity activity;

    private Appointments appointment = new Appointments();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors");

    public TutorAdapter(Context context, AppCompatActivity activity) {
        //instantiate list information here
        this.context = context;
        this.activity = activity;
        showFragment = (DetailsFragment.OnFragmentInteractionListener) context;
        appointment.name = LoginActivity.user.first_name + " " + LoginActivity.user.last_name;
        appointment.uid = LoginActivity.user.uid;
        noTutorListener = (NoTutorListener) context;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    TutorAdapter.this.tutorsList.add(snapshot.getValue(Tutor.class));
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TutorAdapter.TAG, "Database error: " + databaseError.getMessage());


            }
        });

    }

    @Override
    public TutorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        noTutorListener.showTutor();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TutorAdapter.ViewHolder holder, int position) {

        final Tutor tutor = tutorsList.get(holder.getAdapterPosition());
        final String name = tutor.first_name + " " + tutor.last_name;
        holder.tutorNameText.setText(name);
        holder.ratingBar.setRating(tutor.ratings);
        holder.tutorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show details fragment
                TutorAdapter.this.activity.getSupportFragmentManager().
                        beginTransaction().add(R.id.container, DetailsFragment.
                        newInstance(name, null, tutor.ratings), TAGS.DETAILS).addToBackStack(TAGS.DETAILS).commit();
            }
        });

        if (tutor.subjectTaught != null && tutor.subjectTaught.size() > 0) {
            holder.subjectsTaughtRecycler.setVisibility(View.VISIBLE);
            holder.subjectsTaughtRecycler.setAdapter(new SubjectTaughtAdapter(tutor.subjectTaught));
            holder.subjectText.setVisibility(View.INVISIBLE);


        } else {
            holder.subjectsTaughtRecycler.setVisibility(View.INVISIBLE);
            holder.subjectText.setVisibility(View.VISIBLE);

        }
        //// FIXME: 10/7/2017 fix image

    }

    @Override
    public int getItemCount() {
        if (tutorsList.size() == 0) {
            noTutorListener.showNoTutor();
        }

        return tutorsList.size();
    }

     void checkTutors() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    TutorAdapter.this.tutorsList.add(snapshot.getValue(Tutor.class));
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TutorAdapter.TAG, "Database error");

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView tutorCard;
        ImageView tutorImage;
        TextView tutorNameText;
        TextView subjectText;
        RatingBar ratingBar;
        RecyclerView subjectsTaughtRecycler;

        ViewHolder(View itemView) {
            super(itemView);
            tutorImage = itemView.findViewById(R.id.profile_image);
            tutorCard = itemView.findViewById(R.id.tutor_card);
            tutorNameText = itemView.findViewById(R.id.tutor_name_text);
            subjectText = itemView.findViewById(R.id.subject_text);
            subjectsTaughtRecycler = itemView.findViewById(R.id.subject_recycler);
            ratingBar = itemView.findViewById(R.id.rating_bar);

        }
    }


    private static class SubjectTaughtAdapter extends RecyclerView.Adapter<SubjectTaughtAdapter.ViewHolder> {
        ArrayList<String> subjects;

        SubjectTaughtAdapter(ArrayList<String> subjects) {
            this.subjects = subjects;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chips_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (subjects != null && subjects.size() != 0) {
                holder.subjectText.setText(subjects.get(holder.getAdapterPosition()));
            }

        }

        @Override
        public int getItemCount() {
            if (subjects != null) return subjects.size();
            return 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView subjectText;

            ViewHolder(View itemView) {
                super(itemView);
                subjectText = itemView.findViewById(R.id.subject_text);

            }
        }
    }
}
