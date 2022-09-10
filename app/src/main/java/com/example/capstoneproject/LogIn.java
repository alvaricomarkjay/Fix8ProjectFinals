package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn extends AppCompatActivity {

    private EditText  LogInEmail, LogInPassword;
    private Button loginU;
    private TextView registerU;
    private ProgressDialog progressBarDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();
        LogInEmail = findViewById(R.id.loginEmail);
        LogInPassword = findViewById(R.id.loginPassword);


        loginU = findViewById(R.id.loginBtn);
        registerU = findViewById(R.id.toRegister);

        progressBarDialog = new ProgressDialog(this);

        loginU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        registerU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void LoginUser(){
        String userEmail = LogInEmail.getText().toString();
        String userPassword = LogInPassword.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            LogInEmail.setError("Enter your Email");
            return;
        }else if(TextUtils.isEmpty(userPassword)){
            LogInPassword.setError("Enter your Password");
            return;
        }

        progressBarDialog.setMessage("Please wait....");
        progressBarDialog.show();
        progressBarDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LogIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
                progressBarDialog.dismiss();
            }
        });
    }
}
