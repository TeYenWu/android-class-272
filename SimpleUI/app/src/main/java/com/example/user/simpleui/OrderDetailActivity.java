package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)findViewById(R.id.storeInfoTextView);
        TextView drinkOrderResultsTextView = (TextView)findViewById(R.id.drinkOrderResultsTextView);
        TextView latlngTextView = (TextView)findViewById(R.id.latLngTextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());
        String resultText = "";
        for (DrinkOrder drinkOrder: order.getDrinkOrderList())
        {
            String lNumber = String.valueOf(drinkOrder.getlNumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText+=drinkName + "  M:" + mNumber + "  L:" + lNumber + "\n";
        }
        drinkOrderResultsTextView.setText(resultText);


    }

}
