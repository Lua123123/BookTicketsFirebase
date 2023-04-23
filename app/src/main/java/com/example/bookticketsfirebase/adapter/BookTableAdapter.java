package com.example.bookticketsfirebase.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookticketsfirebase.R;
import com.example.bookticketsfirebase.callback.callback;
import com.example.bookticketsfirebase.model.BookTable;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookTableAdapter extends RecyclerView.Adapter<BookTableAdapter.ViewHolder> {

    private ArrayList<BookTable> listNameTable = new ArrayList();
    private ArrayList<String> listStatus = new ArrayList();
    private Context context;
    private Boolean status = false;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public callback.CallBackLoadEmptyTable callBackLoadEmptyTable;

    public void loadEmptyTableListener(callback.CallBackLoadEmptyTable callBackLoadEmptyTable) {
        this.callBackLoadEmptyTable = callBackLoadEmptyTable;
    }

    public BookTableAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<BookTable> listNameTable) {
        this.listNameTable = listNameTable;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_book_table_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView book;
        private final TextView status;
        private final LinearLayout itemBookTable;

        public ViewHolder(View view) {
            super(view);

            book = (TextView) view.findViewById(R.id.book);
            status = (TextView) view.findViewById(R.id.status);
            itemBookTable = (LinearLayout) view.findViewById(R.id.itemBookTable);
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        holder.book.setText(listNameTable.get(position).getNameBookTable().toString());

        if (listNameTable.get(position).getStatus().toString().equals("false")) {
            holder.status.setText("EMPTY");
        } else {
            holder.status.setText("FULL");
        }

        callBackLoadEmptyTable.OnItemDirect(holder.itemBookTable, position, listNameTable.get(position).getNameBookTable().toString());

    }

    @Override
    public int getItemCount() {
        return listNameTable.size();
    }
}
