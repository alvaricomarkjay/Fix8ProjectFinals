package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


public class Register extends AppCompatActivity {

    private EditText fullname, phone, email, password, conPassword;
    private Button register;
    private TextView login;
    private ProgressDialog progressBarDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        fullname = findViewById(R.id.etFullname);
        phone = findViewById(R.id.etPhone);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        conPassword = findViewById(R.id.etConPassword);

        register = findViewById(R.id.registerBtn);
        login = findViewById(R.id.rdLogIn);

        progressBarDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void RegisterUser(){
        String userFullname = fullname.getText().toString();
        String userPhone = phone.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userConPassword = conPassword.getText().toString();

        if(TextUtils.isEmpty(userFullname)){
            fullname.setError("Enter your Fullname");
            return;
        }else if(TextUtils.isEmpty(userPhone)){
            phone.setError("Enter your Phone Number");
            return;
        }else if(TextUtils.isEmpty(userEmail)){
            email.setError("Enter your Email");
            return;
        }else if(TextUtils.isEmpty(userPassword)){
            password.setError("Enter your Password");
            return;
        }else if(TextUtils.isEmpty(userConPassword)){
            conPassword.setError("Confirm your password");
            return;
        }else if(!userPassword.equals(userConPassword)){
            conPassword.setError("Your password does not match");
            return;
        }else if(userPassword.length() < 4){
            conPassword.setError("Password length should be more than 4");
            return;
        }else if(!isValidEmail(userEmail)){
            email.setError("Invalid Email");
            return;
        }
        progressBarDialog.setMessage("Please wait....");
        progressBarDialog.show();
        progressBarDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                }
                progressBarDialog.dismiss();
            }
        });
    }
    private boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}