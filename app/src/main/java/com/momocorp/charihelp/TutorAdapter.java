package com.momocorp.charihelp;

import android.animation.TimeAnimator;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
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

    public TutorAdapter() {
        //instantiate list information here
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<Tutor>> t = new GenericTypeIndicator<>();
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
        noTutorListener = (NoTutorListener) context;
        if(tutorsList.size()==0)
            noTutorListener.showNoTutor();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TutorAdapter.ViewHolder holder, int position) {
        holder.scheduleButton.setOnClickListener(view -> {
            //send data to server to schedule appointment

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, timeSetListener, 8, 0, false);
            this.iSelected = holder.getAdapterPosition();
            timePickerDialog.setOnDismissListener(dialogInterface -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, dateChangedListener, 2017, 1, 1);
                datePickerDialog.setOnDismissListener(dialogInterface1 -> {
                    Toast.makeText(context, "Appointment Set.", Toast.LENGTH_SHORT).show();

                });
                datePickerDialog.show();
            });
            timePickerDialog.show();



        });
        holder.tutorNameText.setText(tutorsList.get(holder.getAdapterPosition()).first_name + " " + tutorsList.get(holder.getAdapterPosition()).last_name);
        holder.subjectText.setText(tutorsList.get(holder.getAdapterPosition()).subjectTaught);
        //// FIXME: 10/7/2017 fix image

    }

    @Override
    public int getItemCount() {


        return tutorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button scheduleButton;
        public ImageView tutorImage;
        public TextView tutorNameText;
        public TextView subjectText;

        public ViewHolder(View itemView) {
            super(itemView);
            scheduleButton = itemView.findViewById(R.id.schedule_button);
            tutorImage = itemView.findViewById(R.id.tutor_image);
            tutorNameText = itemView.findViewById(R.id.tutor_name_text);
            subjectText = itemView.findViewById(R.id.subject_text);
        }
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hour, minute) -> {
        //set the time and pass it to the tutor in question
        appointment.time = new Time(timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);

    };

    private DatePickerDialog.OnDateSetListener dateChangedListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            appointment.date = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            //push data
            LoginActivity.user.appointments.add(appointment);
            Map<String, Object> userAppointment = new HashMap<>();
            userAppointment.put("appointments", appointment);



            reference.child(tutorsList.get(iSelected).uid).updateChildren(userAppointment);


        }


    };
}
