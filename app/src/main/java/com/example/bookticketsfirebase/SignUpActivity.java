package com.example.bookticketsfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketsfirebase.databinding.ActivitySignUpBinding;
import com.example.bookticketsfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private String name;
    private String conformPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.checkBoxSignUp.setChecked(true);

        binding.tvHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnPostSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.edtName.getText().toString().trim();
                email = binding.edtEmail.getText().toString().trim();
                password = binding.edtPassword.getText().toString().trim();
                conformPassword = binding.edtConformPassword.getText().toString().trim();
                if (binding.checkBoxSignUp.isChecked()) {
                    if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !conformPassword.isEmpty() && password.length() >= 6 && conformPassword.equals(password) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        registerUser(name, email, password, conformPassword);
                    } else {
                        if (name.isEmpty()) {
                            binding.edtConformPassword.setError("Name is required!");
                            binding.edtName.requestFocus();
                        }
                        if (email.isEmpty()) {
                            binding.edtConformPassword.setError("Email is required!");
                            binding.edtEmail.requestFocus();
                        }
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            binding.edtConformPassword.setError("Please provide valid email! @gmail.com?");
                            binding.edtEmail.requestFocus();
                        }
                        if (password.isEmpty()) {
                            binding.edtConformPassword.setError("Password is required!");
                            binding.edtPassword.requestFocus();
                        }
                        if (password.length() < 6) {
                            binding.edtConformPassword.setError("Passwords must have at least 6 characters!");
                            binding.edtPassword.requestFocus();
                        }
                        if (conformPassword.isEmpty()) {
                            binding.edtConformPassword.setError("ConformPassword is required!");
                            binding.edtConformPassword.requestFocus();
                        }
                        if (!conformPassword.equals(password)) {
                            binding.edtConformPassword.setError("Passwords, conform password invalid!");
                            binding.edtConformPassword.requestFocus();
                        }
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please agree to the terms of the app!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String name, String email, String password, String conformPassword) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(name, email, password, conformPassword);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(SignUpActivity.this, "REGISTER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "REGISTER FAIL!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "REGISTER FAILED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}