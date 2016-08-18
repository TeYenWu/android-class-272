package com.example.user.simpleui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.OnFragmentInteractionListener{

    ListView drinkMenuListView;
    TextView totalTextView;

    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶", "珍珠紅茶拿鐵", "紅茶拿鐵"};
    int[] lPrices = {35, 45, 55, 45};
    int[] mPrices = {25,35,45,35};
    int[] imageIds = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};

    int total = 0;
    List<Drink> drinkList = new ArrayList<>();
    ArrayList<DrinkOrder> drinkOrderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setData();

        Intent intent = getIntent();
        drinkOrderList = intent.getParcelableArrayListExtra("result");

        drinkMenuListView = (ListView)findViewById(R.id.drinkMenuListView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);

        updateTotalTextView();

        drinkMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drink drink = (Drink)parent.getAdapter().getItem(position);
//                total+=drink.mPrice;
//                totalTextView.setText(String.valueOf(total));
                showDrinkOrderDialog(drink);
            }
        });

//        setupDrinkMenu();

        Log.d("DEBUG", "DrinkMenuActivity OnCreate");
    }

    private void setData()
    {
//        for (int i = 0; i < names.length ; i++)
//        {
//            Drink drink = new Drink();
//            drink.name = names[i];
//            drink.lPrice = lPrices[i];
//            drink.mPrice = mPrices[i];
//            drink.imageId = imageIds[i];
//            drinkList.add(drink);
//        }
        Drink.getDrinksFromLocalThenRemote(new FindCallback<Drink>() {
            @Override
            public void done(List<Drink> objects, ParseException e) {
                if(e==null)
                {
                    drinkList = objects;
                    setupDrinkMenu();
                }
            }
        });
    }

    private void setupDrinkMenu()
    {
        DrinkAdapter adapter = new DrinkAdapter(this, drinkList);
        drinkMenuListView.setAdapter(adapter);
    }

    public void done(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("result", drinkOrderList);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void showDrinkOrderDialog(Drink drink)
    {
        DrinkOrder order = null;
        for (DrinkOrder drinkOrder : drinkOrderList)
        {
            if (drinkOrder.getDrink().getObjectId().equals(drink.getObjectId()))
            {
                order = drinkOrder;
                break;
            }
        }
        if (order == null)
            order = new DrinkOrder(drink);


        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        DrinkOrderDialog dialog = DrinkOrderDialog.newInstance(order);

//        ft.replace(R.id.root, dialog);
//
//        ft.commit();
        dialog.show(ft, "DrinkOrderDialog");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "DrinkMenuActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "DrinkMenuActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "DrinkMenuActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "DrinkMenuActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "DrinkMenuActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "DrinkMenuActivity OnRestart");
    }

    @Override
    public void onDrinkOrderResult(DrinkOrder drinkOrder) {
        boolean flag = false;

        for (int i = 0; i < drinkOrderList.size() ; i++)
        {
            if(drinkOrderList.get(i).getDrink().getObjectId().equals(drinkOrder.getDrink().getObjectId()))
            {
                drinkOrderList.set(i, drinkOrder);
                flag = true;
                break;
            }
        }
        if(!flag)
            drinkOrderList.add(drinkOrder);
        updateTotalTextView();
    }

    private void updateTotalTextView()
    {
        int total = 0;
        for (DrinkOrder drinkOrder : drinkOrderList)
        {
            total += drinkOrder.getlNumber() * drinkOrder.getDrink().getlPrice() + drinkOrder.getmNumber() * drinkOrder.getDrink().getmPrice();
        }

        totalTextView.setText(String.valueOf(total));
    }
}
