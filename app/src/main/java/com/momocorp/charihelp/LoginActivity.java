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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    TextView signUp;
    public static User user;
    TextView tutorSignIn;
    EditText email;
    EditText password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        email = findViewById(R.id.email_address_edit);
        password = findViewById(R.id.password_edit);
        tutorSignIn = findViewById(R.id.sign_in_as_tutor);
        signUp = findViewById(R.id.sign_up);
        auth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button);
        signUp.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        loginButton.setOnClickListener(view -> auth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnSuccessListener(task ->
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String first_name = user.getDisplayName().split(" ")[0];
                        String last_name = user.getDisplayName().split(" ")[1];
                        LoginActivity.user = new User(first_name, last_name, user.getUid());


                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
        ));


        tutorSignIn.setOnClickListener(view -> {
            //get user data
            auth.signInWithEmailAndPassword(email.getText().toString(),
                    password.getText().toString()).
                    addOnCompleteListener(task -> {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                         LoginActivity.user = new Tutor();
                        LoginActivity.user.first_name = user.getDisplayName().split(" ")[0];
                        LoginActivity.user.last_name = user.getDisplayName().split(" ")[1];
                        LoginActivity.user.uid = user.getUid();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    });
            startActivity(new Intent(LoginActivity.this, TutorActivity.class));
        });


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

}
