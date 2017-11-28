package com.momocorp.charihelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextView signUp;
    public static User user;
    TextView tutorSignIn;
    EditText email;
    EditText password;
    FirebaseAuth auth;
    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.login_button);
        email = (EditText) findViewById(R.id.email_address_edit);
        password = (EditText) findViewById(R.id.password_edit);
        email.setText("test_3@email.com");
        password.setText("Dragon");
        tutorSignIn = findViewById(R.id.sign_in_as_tutor);
        signUp = findViewById(R.id.sign_up);
        auth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString()).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                 @Override
                                                 public void onSuccess(AuthResult authResult) {
                                                     //get the user associated with the uid and run a query
                                                     FirebaseUser user = auth.getCurrentUser();

                                                     if (user != null) {
                                                         String uID = user.getUid();
                                                         DatabaseReference reference = FirebaseDatabase.
                                                                 getInstance().getReference();
                                                         reference.child("users").orderByChild("uid").equalTo(uID).
                                                                 addChildEventListener(new ChildEventListener() {
                                                                     @Override
                                                                     public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                         Log.i("Firebase Test", dataSnapshot.getValue().toString());
                                                                         LoginActivity.user =
                                                                                 dataSnapshot.getValue(User.class);
                                                                         startActivity(new Intent(LoginActivity.this, UserActivity.class));
                                                                         finish();

                                                                     }

                                                                     @Override
                                                                     public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                                                         Log.i("Firebase Test", dataSnapshot.getValue().toString());
                                                                         LoginActivity.user = dataSnapshot.getValue(User.class);
                                                                     }

                                                                     @Override
                                                                     public void onChildRemoved(DataSnapshot dataSnapshot) {

                                                                     }

                                                                     @Override
                                                                     public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                                                         Log.i("Firebase Test", dataSnapshot.getValue().toString());
                                                                         LoginActivity.user = dataSnapshot.getValue(User.class);
                                                                     }

                                                                     @Override
                                                                     public void onCancelled(DatabaseError databaseError) {

                                                                     }
                                                                 });

                                                     }

                                                 }
                                             }

                        ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Sign in failed. Please Sign up.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        tutorSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    //get user data
                    auth.signInWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString()).
                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        LoginActivity.user = new Tutor();
                                        LoginActivity.user.first_name = user.getDisplayName().split(" ")[0];
                                        LoginActivity.user.last_name = user.getDisplayName().split(" ")[1];
                                        LoginActivity.user.uid = user.getUid();
                                        startActivity(new Intent(LoginActivity.this, TutorActivity.class));
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Sign in failed. Please sign up", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });


    }

}
