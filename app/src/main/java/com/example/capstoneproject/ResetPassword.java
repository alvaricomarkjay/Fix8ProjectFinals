package com.example.capstoneproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText resetEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetEmail = findViewById(R.id.fEmail);
        resetPassword = findViewById(R.id.rPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetUserPassword();
            }
        });
    }
    private void ResetUserPassword(){
        String email = resetEmail.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(ResetPassword.this, "Please Enter your Email",Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPassword.this, "Please visit your email to reset your password.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPassword.this, LogIn.class));
                    }else{
                        String message = task.getException().getMessage();
                        Toast.makeText(ResetPassword.this, "Error: "+ message,Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}