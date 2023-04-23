package com.example.bookticketsfirebase.callback;

import android.widget.LinearLayout;

public class callback {

    public interface CallBackDishes {
        void OnItemDirect(String nameDish, int position, String payment);
    }

    public interface CallBackAddDishes {
        void OnItemDirect(String carSeat, String price);
    }

    public interface CallBackLoadEmptyTable {
        void OnItemDirect(LinearLayout itemBookTable, int position, String nameBook);
    }

    public interface CallBackLoadBill {
        void OnItemDirect(String nameBook);
    }

    public interface CallBackDelete {
        void OnItemDirect(int position);
    }
}
