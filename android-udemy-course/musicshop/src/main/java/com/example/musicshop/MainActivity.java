package com.example.musicshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView quantityTextView;
    private Spinner spinner;
    private int quantity = 0;
    private ArrayList spinnerArrayList;
    private ArrayAdapter spinnerAdapter;
    private HashMap goodsMap;
    private TextView priceTextView;
    private String goodsName;
    private double price;
    private ImageView goodsImageView;
    private EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = findViewById(R.id.quantityTextView);

        userNameEditText = findViewById(R.id.nameEditText);

        createSpinner();

        createMap();

        priceTextView = findViewById(R.id.priceTextView);

        goodsImageView = findViewById(R.id.goodsImageView);
    }

    private void createSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();
        spinnerArrayList.add("guitar");
        spinnerArrayList.add("drums");
        spinnerArrayList.add("keyboard");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void createMap() {
        goodsMap = new HashMap();
        goodsMap.put("guitar", 500.0);
        goodsMap.put("drums", 1500.0);
        goodsMap.put("keyboard", 1000.0);
    }

    public void increaseQuantity(View view) {
        quantityTextView.setText(String.valueOf(++quantity));
        price = (double) goodsMap.get(goodsName);
        priceTextView.setText("" + quantity * price);
    }

    public void decreaseQuantity(View view) {
        if (quantity > 0) {
            quantityTextView.setText(String.valueOf(--quantity));
            price = (double) goodsMap.get(goodsName);
            priceTextView.setText("" + quantity * price);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double) goodsMap.get(goodsName);
        priceTextView.setText("" + quantity * price);

        switch (goodsName) {
            case "keyboard":
                goodsImageView.setImageResource(R.drawable.keyboard);
                break;
            case "drums":
                goodsImageView.setImageResource(R.drawable.drums);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCard(View view) {
        Order order = new Order();
        order.userName = userNameEditText.getText().toString();
        order.goodsName = goodsName;
        order.quantity = quantity;
        order.price = price;
        order.orderPrice = quantity * price;

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameForIntent", order.userName);
        orderIntent.putExtra("goodsName", order.goodsName);
        orderIntent.putExtra("quantity", order.quantity);
        orderIntent.putExtra("orderPrice", order.orderPrice);
        orderIntent.putExtra("price", order.price);
        startActivity(orderIntent);
    }
}