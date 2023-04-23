package com.example.bookticketsfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketsfirebase.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.checkBoxLogin.setChecked(true);
        binding.tvDontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnGetLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.edtEmail.getText().toString().trim();
                password = binding.edtPassword.getText().toString().trim();

                if (binding.checkBoxLogin.isChecked()) {
                    if (!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        checkLogin(email, password);
                    } else {
                        if (email.isEmpty()) {
                            binding.edtEmail.setError("Email is required!");
                            binding.edtEmail.requestFocus();
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            binding.edtEmail.setError("Please provide valid email! @gmail.com?");
                            binding.edtEmail.requestFocus();
                        }
                        if (password.isEmpty()) {
                            binding.edtPassword.setError("Password is required!");
                            binding.edtPassword.requestFocus();
                        }
                        if (password.length() < 6) {
                            binding.edtPassword.setError("Passwords must have at least 6 characters!");
                            binding.edtPassword.requestFocus();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please agree to the terms of the app!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intentUser = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentUser);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "LOGIN FAILED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}