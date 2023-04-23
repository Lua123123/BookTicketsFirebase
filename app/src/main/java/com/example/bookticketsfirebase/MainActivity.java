package com.example.bookticketsfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityMainBinding;
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.example.bookticketsfirebase.model.BookTable;
import com.example.bookticketsfirebase.model.CarSeat;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseUser user;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private DatabaseReference refDishes;
    private DatabaseReference refUser;
    private String name;
    private String email;
    private ShowDialog showDialog = new ShowDialog(MainActivity.this);
    private ArrayList<BookTable> listNameTable = new ArrayList();
    private ArrayList<CarSeat> listDishes = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindComponent();
        bindData();
        bindEvent();
    }

    private void bindComponent() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getFirebaseInformation();
            }
        });
    }

    private void bindData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        refUser = FirebaseDatabase.getInstance().getReference("User");
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        refDishes = db.getReference();

        listNameTable.add(new BookTable(Constants.CAR_KEY + " 1", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 2", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 3", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 4", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 5", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 6", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 7", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 8", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 9", "false"));
        listNameTable.add(new BookTable(Constants.CAR_KEY + " 10", "false"));

        if (!onDataFinished()) {
            ref.child(Constants.BOOK_TICKETS_KEY).child("tickets").setValue(listNameTable);
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 1").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 1", "SÀI GÒN - HÀ NỘI"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 2").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 2", "LÀO CAI - BẮC CẠN"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 3").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 3", "SƠN LA - ĐỒNG THÁP"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 4").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 4", "ĐỒNG NAI - PHÚ YÊN"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 5").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 5", "ĐÀ NẴNG - THỪA THIÊN HUẾ"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 6").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 6", "HẬU GIANG - NINH BÌNH"));
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                    child("tickets_information").child(Constants.CAR_TICKET_KEY + " 7").setValue(new CarSeat(Constants.CAR_TICKET_KEY + " 7", "CẦN THƠ - BẾN TRE"));

            ref.child(Constants.BOOK_TICKETS_KEY).child("Table").setValue(listNameTable);
            refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 1").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 1", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 2").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 2", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 3").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 3", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 4").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 4", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 5").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 5", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 6").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 6", "10000"));
                refDishes.child(Constants.BOOK_TICKETS_KEY).
                        child("CARSEAT").child(Constants.CAR_SEAT_KEY + " 7").setValue(new CarSeat(Constants.CAR_SEAT_KEY + " 7", "10000"));
        }
        onBoardingFinishedTrue();
    }

    private void bindEvent() {
        binding.imgInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOpenInformation();
            }
        });

        binding.booktakeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookTableActivity.class);
                startActivity(intent);
            }
        });

        binding.adddishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCarSeatActivity.class);
                startActivity(intent);
            }
        });

        binding.invoicehistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InvoiceHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getFirebaseInformation() {
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = Objects.requireNonNull(snapshot.getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = Objects.requireNonNull(snapshot.getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickOpenInformation() {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.layout_bottom_information);
        TextView tvName = bottomSheetDialog.findViewById(R.id.tvName);
        TextView tvEmail = bottomSheetDialog.findViewById(R.id.tvEmail);
        tvName.setText("Full name: " + name);
        tvEmail.setText("Email: " + email);
        ConstraintLayout layoutHide = bottomSheetDialog.findViewById(R.id.layoutHide);
        layoutHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        ImageView logOut = bottomSheetDialog.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                showDialog.openDialogLogOut(Gravity.CENTER);
            }
        });
        bottomSheetDialog.show();
    }

    private Boolean onDataFinished() {
        SharedPreferences sharedPref = getSharedPreferences("runFirst", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("Finished", false);
    }

    private void onBoardingFinishedTrue() {
        SharedPreferences sharedPref = getSharedPreferences("runFirst", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Finished", true);
        editor.apply();
    }
}