package com.example.user.simpleui;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderDetailActivity extends AppCompatActivity implements GeoCodingTask.GeocodingCallback {

    TextView latlngTextView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)findViewById(R.id.storeInfoTextView);
        TextView drinkOrderResultsTextView = (TextView)findViewById(R.id.drinkOrderResultsTextView);
        latlngTextView = (TextView)findViewById(R.id.latLngTextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());
//        String address = order.getStoreInfo().split(",")[1];
        String resultText = "";
        for (DrinkOrder drinkOrder: order.getDrinkOrderList())
        {
            String lNumber = String.valueOf(drinkOrder.getlNumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText+=drinkName + "  M:" + mNumber + "  L:" + lNumber + "\n";
        }
        drinkOrderResultsTextView.setText(resultText);

        MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                (new GeoCodingTask(OrderDetailActivity.this)).execute("台北市大安區羅斯福路四段一號");
            }
        });

    }

    @Override
    public void done(double[] latlng) {
        if(latlng!=null)
        {
            String latlngString = String.valueOf(latlng[0]+","+latlng[1]);
            latlngTextView.setText(latlngString);

            LatLng latLng = new LatLng(latlng[0], latlng[1]);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("NTU");

            map.moveCamera(cameraUpdate);
            map.addMarker(markerOptions);
        }
    }
}
