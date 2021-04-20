package com.example.listview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView colorsListView = findViewById(R.id.listView);

        final ArrayList<String> colorsArrayList = new ArrayList<>();
        colorsArrayList.add("Красный");
        colorsArrayList.add("Оранжевый");
        colorsArrayList.add("Желтый");
        colorsArrayList.add("Зеленый");
        colorsArrayList.add("Голубой");
        colorsArrayList.add("Синий");
        colorsArrayList.add("Фиолетовый");
        colorsArrayList.add("Красный");
        colorsArrayList.add("Оранжевый");
        colorsArrayList.add("Желтый");
        colorsArrayList.add("Зеленый");
        colorsArrayList.add("Голубой");
        colorsArrayList.add("Синий");
        colorsArrayList.add("Фиолетовый");

        ArrayAdapter<String> colorsArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, colorsArrayList);

        colorsListView.setAdapter(colorsArrayAdapter);

        colorsListView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(MainActivity.this, "Номер " + position +
                " - " + colorsArrayList.get(position), Toast.LENGTH_SHORT).show());
    }
}