package com.example.studentsdb;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Data.DatabaseHandler;
import Model.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        databaseHandler.addStudent(new Student("КТ-19", "Иван",
                "Иванов", "4.5"));
        databaseHandler.addStudent(new Student("МШ-19", "Петр",
                "Петров", "4.6"));
        databaseHandler.addStudent(new Student("ПМ-19", "Сергей",
                "Сидоров", "4.3"));
        databaseHandler.addStudent(new Student("КИ-19", "John",
                "Smith", "4.4"));
        databaseHandler.addStudent(new Student("ХМ-19", "Jan Jack",
                "Russo", "4.8"));


        List<Student> studentList = databaseHandler.getAllStudents();

        for (Student student : studentList) {
            Log.d("StudentInfo:", "ID " + student.getId() + ", department " + student.getDepartment()
                    + ", firstName " + student.getFirstName() + ", lastName " + student.getLastName()
                    + ", averageMark " + student.getAverageMark());

        }
    }
}
