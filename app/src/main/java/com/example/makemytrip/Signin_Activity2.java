package com.example.makemytrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin_Activity2 extends AppCompatActivity {

    TextInputEditText email, password, confirm_password;
    MaterialButton registerbtn;
    MaterialTextView logintext;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);

        email = findViewById(R.id.Email_id);
        password = findViewById(R.id.Password_id);
        confirm_password = findViewById(R.id.Confirm_password_id);
        registerbtn = findViewById(R.id.register_btn_id);
        logintext = findViewById(R.id.logintext_id);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin_Activity2.this, Login_Activity1.class));
            }
        });
    }

    private void adddata() {

        String Email = email.getText().toString();
        String Pass = password.getText().toString();

        String c_pass = confirm_password.getText().toString();

        if (Email.isEmpty() || Pass.isEmpty() || c_pass.isEmpty()) {

            email.setError("");
            password.setError("");
            confirm_password.setError("");
            Toast.makeText(this, "Fill the fild", Toast.LENGTH_SHORT).show();

        } else if (!Pass.equals(c_pass)) {

            confirm_password.setError("Confirm Password not match");
        }
        else {

            auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        // Modal class obj
                        Modal modal = new Modal(Email,Pass);
                        
                        String id = task.getResult().getUser().getUid();
                        reference.child("Users").child(id).setValue(modal);

                        //  SharedPreferences

                        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("open",true);
                        editor.apply();

                        Toast.makeText(Signin_Activity2.this, "Succesfully Registration", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Signin_Activity2.this,MainActivity.class);
                        startActivity(intent);

                        email.setText("");
                        password.setText("");
                        confirm_password.setText("");
                        finish();
                        
                    }else {

                        Toast.makeText(Signin_Activity2.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}