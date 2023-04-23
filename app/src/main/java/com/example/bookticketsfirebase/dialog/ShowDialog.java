package com.example.bookticketsfirebase.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookticketsfirebase.CustomerActivity;
import com.example.bookticketsfirebase.InformationCustomerActivity;
import com.example.bookticketsfirebase.LoginActivity;
import com.example.bookticketsfirebase.MainActivity;
import com.example.bookticketsfirebase.R;
import com.example.bookticketsfirebase.adapter.CarSeatAdapter;
import com.example.bookticketsfirebase.callback.callback;
import com.example.bookticketsfirebase.constants.Constants;
import com.example.bookticketsfirebase.model.CarSeat;
import com.example.bookticketsfirebase.model.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ShowDialog {
    private Context context;

    public ShowDialog(Context context) {
        this.context = context;
    }

    private ArrayList<String> listDishessssss = new ArrayList();


    public callback.CallBackAddDishes callBackAddCarSeat;

    public void setOnClickListener(callback.CallBackAddDishes callBackAddDishes) {
        this.callBackAddCarSeat = callBackAddDishes;
    }

    public callback.CallBackDelete callBackDelete;

    public void setOnClickListener(callback.CallBackDelete callBackDelete) {
        this.callBackDelete = callBackDelete;
    }

    public void openDialogLogOut(int gravity) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        Button btnConfirm = dialog.findViewById(R.id.btnConFirmDialog);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                MainActivity activity = (MainActivity) context;
                activity.finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openDialogDelete(int gravity, String nameDish, int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        Button btnConfirm = dialog.findViewById(R.id.btnConFirmDialog);
        TextView txtLogOut = dialog.findViewById(R.id.txtLogOut);
        txtLogOut.setText(nameDish);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackDelete.OnItemDirect(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openDialogCarSeat(int gravity, String name, String phone, String address, ArrayList<CarSeat> listCarSeat, String nameBook, DatabaseReference ref) {
        final int[] myPayment = {0};
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_dish);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        ConstraintLayout btnCancel = dialog.findViewById(R.id.btnCancel);
        ConstraintLayout btnConfirm = dialog.findViewById(R.id.btnConfirm);
        TextView tvName = dialog.findViewById(R.id.tvName);
        TextView tvPhone = dialog.findViewById(R.id.tvPhone);
        TextView tvAddress = dialog.findViewById(R.id.tvAddress);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.divider_recyclerview)));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(itemDecoration);
        CarSeatAdapter carSeatAdapter = new CarSeatAdapter(context, false);
        carSeatAdapter.setData(listCarSeat);
        recyclerView.setAdapter(carSeatAdapter);

        carSeatAdapter.setOnClickListener(new callback.CallBackDishes() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void OnItemDirect(String nameDish, int position, String payment) {
                listDishessssss.add(nameDish);
                myPayment[0] = myPayment[0] + Integer.parseInt(payment);
                listCarSeat.remove(position);
                carSeatAdapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listDishessssss.isEmpty()) {
                    Toast.makeText(context, "You have yet to selection your car seat!!!", Toast.LENGTH_SHORT).show();
                } else {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Customer").child(nameBook).setValue(new Customer(name, phone, address, nameBook, listDishessssss.toString(), String.valueOf(myPayment[0])));
                    if (nameBook.equals(Constants.CAR_KEY + " 1")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("0").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 2")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("1").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 3")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("2").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 4")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("3").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 5")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("4").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 6")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("5").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 7")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("6").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 8")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("7").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 9")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("8").child("status").setValue("true");
                    } else if (nameBook.equals(Constants.CAR_KEY + " 10")) {
                        ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("9").child("status").setValue("true");
                    }


                    Date date = java.util.Calendar.getInstance().getTime();
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Invoice").child(date.toString()).setValue(new Customer(name, phone, address, nameBook, listDishessssss.toString(), String.valueOf(myPayment[0])));


                    Intent intent = new Intent(context, InformationCustomerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nameBook", nameBook);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    CustomerActivity customerActivity = (CustomerActivity) context;
                    customerActivity.finish();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void openDialogPayment(int gravity, String payment, String nameBook, DatabaseReference ref) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_payment);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        Button btnConfirm = dialog.findViewById(R.id.btnConFirmDialog);
        TextView txtBill = dialog.findViewById(R.id.txtBill);
        txtBill.setText("Your bill: " + payment + " VND");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NẾU THANH TOÁN SET LẠI GIÁ TRỊ Ở MÀN HÌNH XE SỐ N để bỏ tô vàng
                if (nameBook.equals(Constants.CAR_KEY + " 1")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("0").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 2")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("1").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 3")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("2").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 4")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("3").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 5")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("4").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 6")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("5").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 7")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("6").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 8")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("7").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 9")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("8").child("status").setValue("false");
                } else if (nameBook.equals(Constants.CAR_KEY + " 10")) {
                    ref.child(Constants.BOOK_TICKETS_KEY).child("Table").child("9").child("status").setValue("false");
                }
                dialog.dismiss();
                openDialogPaymented(gravity);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        InformationCustomerActivity informationCustomerActivity = (InformationCustomerActivity) context;
                        informationCustomerActivity.finish();
                    }
                }, 500);
            }
        });
        dialog.show();
    }

    public void openDialogPaymented(int gravity) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_paymented);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void openDialogTotalRevenue(int gravity, String totalRevenue) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_paymented);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        TextView txt = dialog.findViewById(R.id.txt);
        txt.setText("Your revenue: " + totalRevenue + " VND");
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    public void openDialogShowBill(int gravity, String address, String dishes, String name, String type, String payment, String phone, String bill) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_show_bill);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        Button btnConfirm = dialog.findViewById(R.id.btnConFirmDialog);
        TextView txtBill = dialog.findViewById(R.id.txtBill);
        TextView txtType = dialog.findViewById(R.id.txtType);
        TextView txtName = dialog.findViewById(R.id.txtName);
        TextView txtPhone = dialog.findViewById(R.id.txtPhone);
        TextView txtAddress = dialog.findViewById(R.id.txtAddress);
        TextView txtCarSeat = dialog.findViewById(R.id.txtCarSeat);
        TextView txtPayment = dialog.findViewById(R.id.txtPayment);
        txtBill.setText(bill);
        txtType.setText(type);
        txtName.setText(name);
        txtPhone.setText(phone);
        txtAddress.setText(address);
        txtCarSeat.setText(dishes);
        txtPayment.setText(payment);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}