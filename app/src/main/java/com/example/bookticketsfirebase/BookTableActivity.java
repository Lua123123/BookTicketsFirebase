package com.example.bookticketsfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookticketsfirebase.adapter.BookTableAdapter;
import com.example.bookticketsfirebase.callback.callback;
import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.databinding.ActivityBookTableBinding;
import com.example.bookticketsfirebase.model.BookTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class BookTableActivity extends AppCompatActivity {
    private ActivityBookTableBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private BookTableAdapter bookTableAdapter = new BookTableAdapter(this);
    private ArrayList<BookTable> listNameTable = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        getDataTable();

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getDataTable() {
        if (!listNameTable.isEmpty()) {
            listNameTable.clear();
            bookTableAdapter.notifyDataSetChanged();
        }

        bookTableAdapter.loadEmptyTableListener(new callback.CallBackLoadEmptyTable() {
            @Override
            public void OnItemDirect(LinearLayout itemBookTable, int position, String nameBook) {
                //quét data theo đường dẫn trong firebase "book_tickets/Table/position"
                DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child(String.valueOf(position));
                friendsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (Integer.parseInt(snapshot.getKey()) <= 9) {
                            //if quét thấy data status bằng true thì set nó màu vàng
                            if (snapshot.child("status").getValue().toString().equals("true")) {
                                itemBookTable.setBackground(getResources().getDrawable(R.drawable.bg_item_rcv_availabel, null));
                                itemBookTable.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(BookTableActivity.this, InformationCustomerActivity.class);
                                        Bundle bundle = new Bundle();
                                        //chuyển qua màn hình InformationCustomerActivity và bắn nó data listNameTable.get(position).getNameBookTable()
                                        bundle.putString("nameBook", listNameTable.get(position).getNameBookTable().toString());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                //ngược quét thấy data status bằng false thì set màu trắng
                                itemBookTable.setBackground(getResources().getDrawable(R.drawable.bg_item_rcv, null));
                                itemBookTable.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //chuyển qua màn hình InformationCustomerActivity và bắn nó data listNameTable.get(position).getNameBookTable()
                                        Intent intent = new Intent(BookTableActivity.this, CustomerActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("nameBook", nameBook);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        DatabaseReference friendsRef = ref.child(Constants.BOOK_TICKETS_KEY).child("Table");
        friendsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!listNameTable.isEmpty()) {
                    listNameTable.clear();
                    bookTableAdapter.notifyDataSetChanged();
                }
                for (DataSnapshot dataSnap : snapshot.getChildren()) {
                    listNameTable.add(new BookTable(dataSnap.child("nameBookTable").getValue().toString(), dataSnap.child("status").getValue().toString()));
                }
                onSetRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onSetRecyclerView() {
        //khởi tạo vạch kẻ ngang
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //vạch kẽ giữa 2 item
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_recyclerview)));
        //setup recyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add vạch kẽ vào recyclerview
        binding.recyclerView.addItemDecoration(itemDecoration);
        //đổ data vào adapter
        bookTableAdapter.setData(listNameTable);
        bookTableAdapter.notifyDataSetChanged();
        //set adapter vào recyclerview để show lên màn hình người dùng
        binding.recyclerView.setAdapter(bookTableAdapter);
    }
}