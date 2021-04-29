package com.example.carsdb;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Data.DatabaseHandler;
import Model.Car;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Log.d("CarsCount", String.valueOf(databaseHandler.getCarsCount()));
//        databaseHandler.addCar(new Car("Toyota", "26320$"));
//        databaseHandler.addCar(new Car("Ford", "34627$"));
//        databaseHandler.addCar(new Car("Opel", "14320$"));
//        databaseHandler.addCar(new Car("Mercedes", "63201$"));
//        databaseHandler.addCar(new Car("KIA", "33201$"));

        List<Car> carList = databaseHandler.getAllCars();

//        Car deletingCar = databaseHandler.getCar(1);
//        databaseHandler.deleteCar(deletingCar);
        for (Car car : carList) {
            Log.d("CarInfo: ", "ID " + car.getId() + ", name " + car.getName() +
                    ", price " + car.getPrice());
        }
//        car.setName("Tesla");
//        car.setPrice("60000$");
//        int updateCarId = databaseHandler.updateCar(car);
//        Log.d("CarInfo", "ID " + car.getId() + ", name " + car.getName() +
//                ", price " + car.getPrice() + ", updateCarId " + updateCarId);

    }
}