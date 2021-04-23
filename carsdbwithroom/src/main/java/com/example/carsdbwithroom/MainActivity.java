package com.example.carsdbwithroom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Data.CarsAppDatabase;
import Model.Car;

public class MainActivity extends AppCompatActivity {

    private CarsAdapter carsAdapter;
    private final ArrayList<Car> carArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CarsAppDatabase carsAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        carsAppDatabase = Room.databaseBuilder(getApplicationContext(),
                CarsAppDatabase.class, "CarsDB")
                .build();

        new GetAllCarsAsyncTask().execute();

        carsAdapter = new CarsAdapter(this, carArrayList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(carsAdapter);

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> addAndEditCars(false, null, -1));
    }

    public void addAndEditCars(final boolean isUpdate, final Car car, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_car, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView newCarTitle = view.findViewById(R.id.newCarTitle);
        final EditText nameEditText = view.findViewById(R.id.nameEditText);
        final EditText priceEditText = view.findViewById(R.id.priceEditText);

        newCarTitle.setText(!isUpdate ? "Add Car" : "Edit Car");

        if (isUpdate && car != null) {
            nameEditText.setText(car.getName());
            priceEditText.setText(car.getPrice());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", (dialogBox, id) -> {

                })
                .setNegativeButton(isUpdate ? "Delete" : "Cancel",
                        (dialogBox, id) -> {
                            if (isUpdate) {
                                deleteCar(car, position);
                            } else {
                                dialogBox.cancel();
                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

            if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                Toast.makeText(MainActivity.this, "Enter car name!", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(priceEditText.getText().toString())) {
                Toast.makeText(MainActivity.this, "Enter car price!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                alertDialog.dismiss();
            }
            if (isUpdate && car != null) {

                updateCar(nameEditText.getText().toString(), priceEditText.getText().toString(), position);
            } else {

                createCar(nameEditText.getText().toString(), priceEditText.getText().toString());
            }
        });
    }

    private void deleteCar(Car car, int position) {
        carArrayList.remove(position);
        new DeleteCarAsyncTask().execute(car);
    }

    private void updateCar(String name, String price, int position) {
        Car car = carArrayList.get(position);
        car.setName(name);
        car.setPrice(price);
        new UpdateCarAsyncTask().execute(car);
        carArrayList.set(position, car);
    }

    private void createCar(String name, String price) {
        new CreateCarAsyncTask().execute(new Car(0, name, price));
    }

    private class GetAllCarsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            carArrayList.addAll(carsAppDatabase.getCarDAO().getAllCars());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            carsAdapter.notifyDataSetChanged();
        }
    }


    private class CreateCarAsyncTask extends AsyncTask<Car, Void, Void> {

        @Override
        protected Void doInBackground(Car... cars) {
            long id = carsAppDatabase.getCarDAO().addCar(
                    cars[0]
            );
            Car car = carsAppDatabase.getCarDAO().getCar(id);
            if (car != null) {
                carArrayList.add(0, car);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            carsAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateCarAsyncTask extends AsyncTask<Car, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            carsAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Car... cars) {
            carsAppDatabase.getCarDAO().updateCar(cars[0]);

            return null;
        }
    }

    private class DeleteCarAsyncTask extends AsyncTask<Car, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            carsAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Car... cars) {
            carsAppDatabase.getCarDAO().deleteCar(cars[0]);
            return null;
        }
    }
}