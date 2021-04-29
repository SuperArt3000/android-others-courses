package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Student;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL - Structured Query Language
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_DEPARTMENT + " TEXT,"
                + Util.KEY_FIRST_NAME + " TEXT,"
                + Util.KEY_LAST_NAME + " TEXT,"
                + Util.KEY_AVERAGE_MARK + " TEXT" + ")";

        db.execSQL(CREATE_STUDENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    //CRUD
    //Create, Read, Update, Delete

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_DEPARTMENT, student.getDepartment());
        contentValues.put(Util.KEY_FIRST_NAME, student.getFirstName());
        contentValues.put(Util.KEY_LAST_NAME, student.getLastName());
        contentValues.put(Util.KEY_AVERAGE_MARK, student.getAverageMark());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_DEPARTMENT,
                        Util.KEY_FIRST_NAME, Util.KEY_LAST_NAME, Util.KEY_AVERAGE_MARK},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Student student = new Student(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));

        return student;
    }

    public List<Student> getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Student> studentList = new ArrayList<>();

        String selectAllStudents = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllStudents, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setDepartment(cursor.getString(1));
                student.setFirstName(cursor.getString(2));
                student.setLastName(cursor.getString(3));
                student.setAverageMark(cursor.getString(4));

                studentList.add(student);
            } while (cursor.moveToNext());
        }

        return studentList;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_DEPARTMENT, student.getDepartment());
        contentValues.put(Util.KEY_FIRST_NAME, student.getFirstName());
        contentValues.put(Util.KEY_LAST_NAME, student.getLastName());
        contentValues.put(Util.KEY_AVERAGE_MARK, student.getAverageMark());

        return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?",
                new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{String.valueOf(student.getId())});

        db.close();
    }

    public int getStudentsCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
