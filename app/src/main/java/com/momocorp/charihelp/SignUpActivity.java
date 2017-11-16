package com.momocorp.charihelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Rfc822Tokenizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.doodle.android.chips.ChipsView;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tylersuehr.chips.ChipsInputLayout;
import com.tylersuehr.chips.data.Chip;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    EditText first_name;
    EditText last_name;
    EditText email;
    ProgressBar progressBar;
    EditText password;
    ChipsInputLayout subjectTaught;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    CheckBox is_tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        first_name =findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        subjectTaught = findViewById(R.id.chips_input);

        email = findViewById(R.id.email_edit);
        password = findViewById(R.id.password_sign_up);
        is_tutor = findViewById(R.id.is_tutor);
        progressBar = findViewById(R.id.progress_signup);
        progressBar.setVisibility(View.INVISIBLE);
        // TODO: 11/14/2017 change the kind of subjects that can be added to static
        is_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_tutor.isChecked())
                    subjectTaught.setVisibility(View.VISIBLE);
                else
                    subjectTaught.setVisibility(View.INVISIBLE);

            }
        });
        Button submit = findViewById(R.id.sign_up_button);
        auth.signOut();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.INVISIBLE);
                {
                    final String firstName = first_name.getText().toString();
                    final String lastName = last_name.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);


                    auth.createUserWithEmailAndPassword
                            (email.getText().toString(), password.getText().toString()).
                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    {
                                        ArrayList<String> list = new ArrayList<>();
                                        if (is_tutor.isChecked()) {
                                            for (int i = 0; i < subjectTaught.getSelectedChips().size(); i++)
                                            {
                                               list.add(subjectTaught.getSelectedChips().get(i).getTitle());

                                            }
                                            LoginActivity.user = new Tutor(firstName, lastName,
                                                    FirebaseAuth.getInstance().getCurrentUser().getUid(), list);

                                            reference.child("tutors").push().setValue(LoginActivity.user);
                                            reference.child("users").push().setValue(LoginActivity.user);
                                            user = FirebaseAuth.getInstance().getCurrentUser();
                                            UserProfileChangeRequest profileChange = new
                                                    UserProfileChangeRequest.Builder().
                                                    setDisplayName(LoginActivity.user.first_name + " " + LoginActivity.user.last_name).build();
                                            user.updateProfile(profileChange);
                                            startActivity(new Intent(SignUpActivity.this, TutorActivity.class));


                                        } else {
                                            User user1 = new User(firstName, lastName, FirebaseAuth.getInstance().getCurrentUser().getUid());
                                            reference.child("users").push().setValue(user1);
                                            user = FirebaseAuth.getInstance().getCurrentUser();
                                            UserProfileChangeRequest profileChange = new
                                                    UserProfileChangeRequest.Builder().setDisplayName(firstName + " " + lastName).build();
                                            user.updateProfile(profileChange);
                                            progressBar.setVisibility(View.INVISIBLE);
                                            startActivity(new Intent(SignUpActivity.this, UserActivity.class));
                                        }


                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                Log.i("ERROR", e.getMessage());
                            }
                        }
                    });


                }
            }
        });
    }
}
