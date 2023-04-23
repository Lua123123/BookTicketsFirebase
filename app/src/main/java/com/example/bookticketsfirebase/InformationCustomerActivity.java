package com.example.bookticketsfirebase;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityInformationCustomerBinding;
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InformationCustomerActivity extends AppCompatActivity {
    private ActivityInformationCustomerBinding binding;

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String nameBook;
    private String name;
    private String phone;
    private String address;
    private String carSeat;
    private String payment;
    private ShowDialog showDialog = new ShowDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        nameBook = bundle.getString("nameBook", "");
        binding.tvNameBook.setText("Type: " + nameBook);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        DatabaseReference friendsRef = FirebaseDatabase.getInstance().getReference().child(Constants.BOOK_TICKETS_KEY).child("Customer").child(nameBook);
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.child("name").getValue().toString();
                    phone = snapshot.child("phone").getValue().toString();
                    address = snapshot.child("address").getValue().toString();
                    carSeat = snapshot.child(Constants.SEAT_KEY).getValue().toString();
                    payment = snapshot.child("payment").getValue().toString();
                    binding.tvName.setText("Name: " + name);
                    binding.tvPhone.setText("Phone: " + phone);
                    binding.tvAddress.setText("Address: " + address);
                    binding.tvDish.setText("Car seat: " + carSeat);
                    binding.tvPayment.setText("Total: " + payment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog.openDialogPayment(Gravity.CENTER, payment, nameBook, ref);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}