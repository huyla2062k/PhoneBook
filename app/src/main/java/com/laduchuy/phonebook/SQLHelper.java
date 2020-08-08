package com.laduchuy.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static  final String DB_NAME="PhoneBook.db";
    static final String DB_TABLE="PhoneNumber";
    static final int DB_VERSION = 1;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE PhoneNumber("+
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "name Text,"+
                "phones Text)";

        db.execSQL(queryCreateTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!=newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
            onCreate(db);
        }
    }

    public void insertPhoneNumber(Contact contact){
        sqLiteDatabase=getWritableDatabase();
        contentValues=new ContentValues();

        contentValues.put("name" , contact.getName());
        contentValues.put("phones" , contact.getPhoneNumber());
        sqLiteDatabase.insert(DB_TABLE,null,contentValues);
    }

    public int deletePhoneNumber(String id){
        sqLiteDatabase=getWritableDatabase();
        int delete = sqLiteDatabase.delete(DB_TABLE,"id=?",new String[]{String.valueOf(id)});

        return delete;
    }
    public List<Contact> getAllPhoneNumber(){
        List<Contact> phones= new ArrayList<>();


        Cursor cursor= sqLiteDatabase.query(false,DB_TABLE,
                null,null,null,null,
                null,null,null);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phoneNumber = cursor.getString(cursor.getColumnIndex("phones"));
            phones.add(new Contact(name,phoneNumber));
        }

        return phones;
    }
}
