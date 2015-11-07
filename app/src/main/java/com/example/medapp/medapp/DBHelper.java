package com.example.medapp.medapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedApp.db";
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table medication " +
                        "(id integer primary key, name text ,nid integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS medication");
        onCreate(db);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from medication", null );
        return res;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from medication where id="+id+"", null );
        return res;
    }


    public Cursor getSchedule(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String medSchedule = "medsche_" + name;
        Cursor res =  db.rawQuery( "SELECT * FROM sqlite_master WHERE name ='"+medSchedule+"' and type='table'", null );

        if( res != null ){
            res = db.rawQuery( "select * from "+medSchedule, null );
            return res;
        }

        return null;
    }

    public boolean insertMedication(String name,Integer NID,ArrayList<Integer> schedule){

        try{
            Cursor c = this.getReadableDatabase().rawQuery( "SELECT * FROM medication where name = '"+name+"'",null);
            if( c != null){
                if(c.getCount() > 0){
                    c.close();
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("nid", NID);
        try{
            db.insertWithOnConflict("medication", null, contentValues,SQLiteDatabase.CONFLICT_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        String medSchedule = "medsche_" + name;
        db.execSQL(
                "create table " + medSchedule +
                        "(id integer primary key,schetime integer, taketime integer)"
        );


        for(Integer i : schedule){
            System.out.println(i);
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("schetime", i);
            contentValues2.put("taketime", -1);
            db.insert(medSchedule, null, contentValues2);
        }

        return true;

    }




}
