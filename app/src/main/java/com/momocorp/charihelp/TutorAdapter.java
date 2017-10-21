package com.momocorp.charihelp;

import android.animation.TimeAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wg13w on 10/7/2017.
 */

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.ViewHolder> {
    private static final String TAG = "TUTOR_ADAPTER";
    ArrayList<Tutor> tutorsList = new ArrayList<>();
    NoTutorListener noTutorListener;
    Context context;
    private int iSelected;
    User.Appointments appointment = new User.Appointments();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tutors");

    public TutorAdapter(Context context) {
        //instantiate list information here
        this.context = context;
        noTutorListener = (NoTutorListener) context;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    TutorAdapter.this.tutorsList.add(snapshot.getValue(Tutor.class));
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TutorAdapter.TAG, "Database error");

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
        holder.scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    //send data to server to schedule appointment

                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 8, 0, false);
                    TutorAdapter.this.iSelected = holder.getAdapterPosition();
                    timePickerDialog.show();



                }
            }
        });
        holder.tutorNameText.setText(tutorsList.get(holder.getAdapterPosition()).first_name + " " + tutorsList.get(holder.getAdapterPosition()).last_name);
        holder.subjectText.setText(tutorsList.get(holder.getAdapterPosition()).subjectTaught);
        //// FIXME: 10/7/2017 fix image

    }

    @Override
    public int getItemCount() {
        if(tutorsList.size()==0)
        {
            noTutorListener.showNoTutor();
        }

        return tutorsList.size();
    }

    public void checkTutors() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    TutorAdapter.this.tutorsList.add(snapshot.getValue(Tutor.class));
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TutorAdapter.TAG, "Database error");

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button scheduleButton;
        public ImageView tutorImage;
        public TextView tutorNameText;
        public TextView subjectText;

        public ViewHolder(View itemView) {
            super(itemView);
            scheduleButton =(Button) itemView.findViewById(R.id.schedule_button);
            tutorImage = (ImageView) itemView.findViewById(R.id.tutor_image);
            tutorNameText =(TextView) itemView.findViewById(R.id.tutor_name_text);
            subjectText = (TextView) itemView.findViewById(R.id.subject_text);
        }
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                appointment.time = new Time(view.getCurrentHour(), view.getCurrentMinute(), 0);
            Date current = Calendar.getInstance().getTime();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateChangedListener,
                    current.getYear(), current.getMonth(), current.getDay());
            datePickerDialog.show();


        }
    };

    private DatePickerDialog.OnDateSetListener dateChangedListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            appointment.date = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            //push data
            LoginActivity.user.appointments.add(appointment);
            Map<String, Object> userAppointment = new HashMap<>();
            userAppointment.put("appointments", appointment);



            reference.child(tutorsList.get(iSelected).uid).updateChildren(userAppointment).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Appointment successfully created", Toast.LENGTH_LONG).show();
                }
            });


        }


    };
}
