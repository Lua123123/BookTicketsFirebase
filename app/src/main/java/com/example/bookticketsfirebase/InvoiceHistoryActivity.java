package com.example.bookticketsfirebase;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookticketsfirebase.adapter.InvoiceAdapter;
import com.example.bookticketsfirebase.callback.callback;
import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityInvoiceHistoryBinding;
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class InvoiceHistoryActivity extends AppCompatActivity {
    private ActivityInvoiceHistoryBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private ArrayList<String> listBill = new ArrayList();
    private ArrayList<String> listObjectBill = new ArrayList();
    private InvoiceAdapter invoiceAdapter = new InvoiceAdapter(this);
    private ShowDialog showDialog = new ShowDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);
        binding = ActivityInvoiceHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        resetData();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuePayment();
            }
        });
    }

    private void resetData() {
        DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("Invoice");
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String nameBill = childSnapshot.getKey();
                    listBill.add(nameBill);
                }
                onSetRecyclerView();

                invoiceAdapter.setOnClickListener(new callback.CallBackLoadBill() {
                    @Override
                    public void OnItemDirect(String nameBook) {
                        getValueOfKey(nameBook);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onSetRecyclerView() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_recyclerview)));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(itemDecoration);
        invoiceAdapter.setData(listBill);
        binding.recyclerView.setAdapter(invoiceAdapter);
    }

    private void getValueOfKey(String nameBook) {
        listObjectBill.clear();
        DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("Invoice").child(nameBook);

        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    listObjectBill.add(childSnapshot.getValue().toString());
                }
                showDialog.openDialogShowBill(Gravity.CENTER, listObjectBill.get(0), listObjectBill.get(1), listObjectBill.get(2), listObjectBill.get(3), listObjectBill.get(4), listObjectBill.get(5), nameBook);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getValuePayment() {
        listObjectBill.clear();
        DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("Invoice");
        final int[] payment = {0};
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    payment[0] = payment[0] + Integer.parseInt(childSnapshot.child("payment").getValue().toString());
                }
                showDialog.openDialogTotalRevenue(Gravity.CENTER, String.valueOf(payment[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}