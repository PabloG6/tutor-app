package com.momocorp.charihelp;

import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by wg13w on 10/9/2017.
 */

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().
            getReference("tutors").equalTo("uid", user.getUid()).getRef().child("appointments");
    ArrayList<User.Appointments> users;
    UserAdapter()
    {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<User.Appointments>> t = new GenericTypeIndicator<ArrayList<User.Appointments>>();
                if(dataSnapshot!=null)
                    users = dataSnapshot.getValue(t);
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        User.Appointments appointments = users.get(holder.getAdapterPosition());
        holder.nameText.setText(appointments.name);
        holder.subjectText.setText(appointments.subject);
        holder.dateText.setText(appointments.time.getMonth()+" "+appointments.time.getDay());



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView subjectText;
        public TextView timeText;
        public TextView dateText;
        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.user_title_text);
            subjectText = itemView.findViewById(R.id.subject_to_teach_text);
            timeText = itemView.findViewById(R.id.time_text);
            dateText = itemView.findViewById(R.id.appointment_date_text);

        }


    }
}
