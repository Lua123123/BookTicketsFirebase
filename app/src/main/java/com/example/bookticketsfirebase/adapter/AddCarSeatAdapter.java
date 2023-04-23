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
import com.example.bookticketsfirebase.dialog.ShowDialog;
import com.example.bookticketsfirebase.model.CarSeat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddCarSeatAdapter extends RecyclerView.Adapter<AddCarSeatAdapter.ViewHolder> {

    private ArrayList<CarSeat> listCarSeat = new ArrayList();
    private Context context;
    private Boolean status = false;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public AddCarSeatAdapter(Context context) {
        this.context = context;
    }

    public callback.CallBackDelete callBackDelete;

    public void setOnClickListener(callback.CallBackDelete callBackDelete) {
        this.callBackDelete = callBackDelete;
    }

    private ShowDialog showDialog = new ShowDialog(context);

    public void setData(ArrayList<CarSeat> listCarSeat) {
        this.listCarSeat = listCarSeat;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_carseat_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameCarSeat;
        private final TextView payment;
        private final LinearLayout itemBookTable;

        public ViewHolder(View view) {
            super(view);
            nameCarSeat = (TextView) view.findViewById(R.id.nameCarSeat);
            payment = (TextView) view.findViewById(R.id.payment);
            itemBookTable = (LinearLayout) view.findViewById(R.id.itemBookTable);
        }
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

        holder.nameCarSeat.setText(listCarSeat.get(position).getCarSeat().toString());
        holder.payment.setText(listCarSeat.get(position).getPayment().toString());

    }

    @Override
    public int getItemCount() {
        return listCarSeat.size();
    }
}
