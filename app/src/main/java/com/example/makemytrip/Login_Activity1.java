package com.example.makemytrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login_Activity1 extends AppCompatActivity {

    TextInputEditText email, password;
    MaterialButton loginbtn;
    MaterialTextView signintext;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        email = findViewById(R.id.login_Email_id);
        password = findViewById(R.id.login_password_id);
        loginbtn = findViewById(R.id.login_btn_id);
        signintext = findViewById(R.id.signuptext_id);

        auth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkemailpass(); 
            }
        });

        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_Activity1.this, Signin_Activity2.class));
            }
        });
    }

    private void checkemailpass() {

        String Email = Objects.requireNonNull(email.getText()).toString();
        String Pass = Objects.requireNonNull(password.getText()).toString();

        if (Email.isEmpty() || Pass.isEmpty()) {

            email.setError("");
            password.setError("");

            Toast.makeText(this, "Fill the fild", Toast.LENGTH_SHORT).show();
        } else {

            auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("open", true);
                        editor.apply();

                        Toast.makeText(Login_Activity1.this, "Login Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login_Activity1.this, MainActivity.class);
                        startActivity(intent);

                        email.setText("");
                        password.setText("");
                        finish();
                    }
                }
            });

        }
    }
}