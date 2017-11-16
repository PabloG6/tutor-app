package com.momocorp.charihelp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference = FirebaseDatabase.getInstance().
            getReference("tutors").orderByChild("uid").equalTo(user.getUid()).getRef().child("appointments");
    ArrayList<Appointments> userAppointments = new ArrayList<>();

    AppointmentsAdapter() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    userAppointments.add(snap.getValue(Appointments.class));
                }
                Log.i("Appointment Construtor", "appointment adapter called");
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public AppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(AppointmentsAdapter.ViewHolder holder, int position) {
        Appointments appointments = userAppointments.get(holder.getAdapterPosition());
        holder.nameText.setText(appointments.name);
        holder.subjectText.setText(appointments.subject);
        // holder.dateText.setText(appointments.time.getMonth()+" "+appointments.time.getDay());


    }

    @Override
    public int getItemCount() {
        Log.i("Appointment Construtor", "appointment adapter called");

        if (userAppointments != null && userAppointments.size() != 0)
            return userAppointments.size();
        return 0;
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView subjectText;
        TextView timeText;
        TextView dateText;

         ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.user_title_text);
            subjectText = itemView.findViewById(R.id.subject_to_teach_text);
            timeText = itemView.findViewById(R.id.time_text);
            dateText = itemView.findViewById(R.id.appointment_date_text);

        }


    }
}
