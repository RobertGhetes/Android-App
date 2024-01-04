package com.example.sidereader;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class MyDatabaseHelper extends SQLiteOpenHelper {

    public Context context;
    public static final String DATABASE_NAME = "CoursesLibrary.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_courses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "course_name";
    public static final String COLUMN_URL = "course_url";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_URL + " TEXT); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long getProfilesCount() {
        long counter;
        SQLiteDatabase db = this.getReadableDatabase();
        counter = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return counter;
    }

    public boolean idInUse(int id)
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int courseId = cursor.getInt(0);
                if(courseId == id)
                    return true;
            }while ( cursor.moveToNext());
        }
        return false;
    }

    public void updateId()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv;
        int newId;
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                newId = cursor.getPosition()+1;
                cv = new ContentValues();
                cv.put(COLUMN_ID, newId);
                cv.put(COLUMN_NAME, cursor.getString(1));
                cv.put(COLUMN_URL, cursor.getString(2));
                long result = db.update(TABLE_NAME,cv,null,null);
                if(result == -1){
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
                }
            }while ( cursor.moveToNext());
        }

    }

    public boolean urlInUse(String url)
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String courseUrl = cursor.getString(2);
                if(courseUrl.equals(url))
                    return true;
            }while ( cursor.moveToNext());
        }
        return false;
    }

    void addCourse(int id, String name, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_URL, url);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getLastId(){
        int lastID = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToLast()) {
            lastID = cursor.getInt(0);
        }
        return lastID;
    }

    public int getCurrentId(int position){
        int currentId ;
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToPosition(position);
        currentId = cursor.getInt(0);
        return currentId;
    }

    public List<Courses> readAllData(){
        List<Courses> returnList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
       SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
           do{
                int courseId = cursor.getInt(0);
                String courseName = cursor.getString(1);
                String courseUrl = cursor.getString(2);

                Courses courses = new Courses(courseId, courseName, courseUrl);
                returnList.add(courses);

            }while ( cursor.moveToNext());
        }
        else {
            //failure. add nothing to list
        }
        cursor.close();
        db.close();
        return returnList;
    }

    void updateData(String row_id, String name, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_URL, url);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}