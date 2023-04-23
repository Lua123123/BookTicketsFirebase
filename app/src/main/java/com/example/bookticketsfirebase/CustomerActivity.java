package com.example.bookticketsfirebase;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityCustomerBinding;
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.example.bookticketsfirebase.model.CarSeat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    private ActivityCustomerBinding binding;
    private String nameBook;
    private ShowDialog showDialog = new ShowDialog(this);
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ArrayList<CarSeat> listDishes = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        nameBook = bundle.getString("nameBook", "");
        binding.nameBook.setText(nameBook);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("CARSEAT");
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    listDishes.add(new CarSeat(dataSnap.child(Constants.SEAT_KEY).getValue().toString(), dataSnap.child("payment").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtName.getText().toString().isEmpty()) {
                    binding.edtName.setError("Name is required!");
                    binding.edtName.requestFocus();
                    return;
                }
                if (binding.edtPhone.getText().toString().isEmpty()) {
                    binding.edtPhone.setError("Phone is required!");
                    binding.edtPhone.requestFocus();
                    return;
                }
                if (binding.edtAddress.getText().toString().isEmpty()) {
                    binding.edtAddress.setError("Address is required!");
                    binding.edtAddress.requestFocus();
                    return;
                }
                showDialog.openDialogCarSeat(Gravity.CENTER, binding.edtName.getText().toString(), binding.edtPhone.getText().toString(), binding.edtAddress.getText().toString(), listDishes, nameBook, ref);
            }
        });
    }
}