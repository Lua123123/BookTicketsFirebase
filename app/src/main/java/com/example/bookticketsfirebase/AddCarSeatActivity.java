package com.example.bookticketsfirebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookticketsfirebase.adapter.AddCarSeatAdapter;
import com.example.bookticketsfirebase.callback.callback;
import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityAddCarSeatBinding;
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.example.bookticketsfirebase.model.CarSeat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AddCarSeatActivity extends AppCompatActivity {
    private ActivityAddCarSeatBinding binding;
    private ArrayList<CarSeat> listCarSeat = new ArrayList();
    private AddCarSeatAdapter carSeatAdapter = new AddCarSeatAdapter(this);
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ShowDialog showDialog = new ShowDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCarSeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        getDataDishes();

    }

    private void getDataDishes() {
        //if data rỗng mới bắt đầu quét data và add vào list
        if (listCarSeat.isEmpty()) {
            //quét data từ đường dẫn trong firebase book_tickets/tickets_information
            DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("tickets_information");
            friendsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnap : snapshot.getChildren()) {
                        //với mỗi data sẽ add vào list
                        listCarSeat.add(new CarSeat(dataSnap.child(Constants.SEAT_KEY).getValue().toString(), dataSnap.child("payment").getValue().toString()));
                    }
                    //và set vào recyclerview
                    onSetRecyclerView();

                    carSeatAdapter.setOnClickListener(new callback.CallBackDelete() {
                        @Override
                        public void OnItemDirect(int position) {
                            showDialog.openDialogDelete(Gravity.CENTER, listCarSeat.get(position).getCarSeat().toString(), position);
                        }
                    });

                    showDialog.setOnClickListener(new callback.CallBackDelete() {
                        @Override
                        public void OnItemDirect(int position) {
                            ref.child(Constants.BOOK_TICKETS_KEY).child("tickets_information").child(listCarSeat.get(position).getCarSeat().toString()).removeValue();
                            listCarSeat.clear();
                            carSeatAdapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void onSetRecyclerView() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_recyclerview)));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(itemDecoration);
        carSeatAdapter.setData(listCarSeat);
        binding.recyclerView.setAdapter(carSeatAdapter);
    }
}