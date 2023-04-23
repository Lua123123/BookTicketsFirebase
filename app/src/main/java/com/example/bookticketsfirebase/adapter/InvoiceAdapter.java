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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private ArrayList<String> listBill = new ArrayList();
    private Context context;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public callback.CallBackLoadBill callBackLoadBill;

    public void setOnClickListener(callback.CallBackLoadBill callBackLoadBill) {
        this.callBackLoadBill = callBackLoadBill;
    }

    public InvoiceAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<String> listBill) {
        this.listBill = listBill;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_bill_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtInvoice;
        private final LinearLayout itemInvoice;

        public ViewHolder(View view) {
            super(view);

            txtInvoice = (TextView) view.findViewById(R.id.txtInvoice);
            itemInvoice = (LinearLayout) view.findViewById(R.id.itemInvoice);
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        holder.txtInvoice.setText(listBill.get(position).toString(), TextView.BufferType.SPANNABLE);

        holder.itemInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackLoadBill.OnItemDirect(listBill.get(position).toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listBill.size();
    }
}
