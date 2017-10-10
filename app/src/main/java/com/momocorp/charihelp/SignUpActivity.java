package com.momocorp.charihelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText password;
    EditText subjectTaught;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    CheckBox is_tutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        subjectTaught = findViewById(R.id.subject_taught);
        subjectTaught.setVisibility(View.GONE);
        email = findViewById(R.id.email_edit);
        password = findViewById(R.id.password_sign_up);
        auth = FirebaseAuth.getInstance();
        is_tutor = findViewById(R.id.is_tutor);
        is_tutor.setOnClickListener(view -> subjectTaught.setVisibility(View.VISIBLE));
        Button submit = findViewById(R.id.sign_up_button);
        if(user!=null)
            FirebaseAuth.getInstance().signOut();
        submit.setOnClickListener(view -> {
           final String firstName = first_name.getText().toString();
            final String lastName = last_name.getText().toString();
            if(user == null)
            {
                auth.createUserWithEmailAndPassword
                        (email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
                            if(is_tutor.isChecked())
                            {
                                String subject = subjectTaught.getText().toString();
                                LoginActivity.user = new Tutor(firstName, lastName, FirebaseAuth.getInstance().getCurrentUser().getUid(), subject);
                                reference.child("tutors").push().setValue(LoginActivity.user);

                                user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileChange = new
                                        UserProfileChangeRequest.Builder().
                                        setDisplayName(LoginActivity.user.first_name+" "+LoginActivity.user.last_name).build();
                                user.updateProfile(profileChange);
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));


                            } else {
                                User user1 = new User(firstName, lastName, FirebaseAuth.getInstance().getCurrentUser().getUid());
                                reference.child("users").push().setValue(user1);
                                user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileChange = new
                                        UserProfileChangeRequest.Builder().setDisplayName(firstName+" "+lastName).build();
                                user.updateProfile(profileChange);

                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            }



                        }).addOnFailureListener(e -> {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });



            } else {
                user = null;
                FirebaseAuth.getInstance().signOut();;
            }


        });
    }
}
