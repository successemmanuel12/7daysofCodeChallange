package com.dairy.successemmanuel.personaldairyy.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by SuccessEmmanuel on 6/29/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "dairy.db";

    // Table Names
    private static final String TABLE_NOTES = "notes";
    private static final String TABLE_USER = "user";

    //column name
    public static final String KEY_NOTE = "note";
    public static final String KEY_TITLE = "title";

    SQLiteDatabase db;


    //user table create statement
    private static final String CREATE_TABLE_USER = "Create table user(email text primary key not null, name text, password text not null)";



    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL("Create table notes(ID INTEGER PRIMARY KEY AUTOINCREMENT, title text,note text) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("drop table if exists " + TABLE_USER);
        onCreate(db);
    }

    // saving user details
    public boolean insert(String email, String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password",password);

        long ins = db.insert("user",null,contentValues);
        if (ins == -1)return false;
        else return true;
    }
    //saving user notes
    public boolean addNote(String note, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("note", note);

        long insert = db.insert("notes",null, contentValues);
        if (insert== -1)return false;
        else return true;
    }

    //checking if the email exist
    public boolean checkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=?", new String[]{email});

        if (cursor.getCount()>0)return false;
        else return true;
    }

    //checking the email and password
    public boolean emailPassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email = ? and password = ?", new String[]{email,password});
        if (cursor.getCount()>0)return true;
        else return false;

    }
    //Get All Values
    public Cursor getAllRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_TITLE,KEY_NOTE};
        return db.query(TABLE_NOTES,columns,null,null,null,null,null);
    }
    public Cursor getCursor() {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NOTES);

        String[] asColumnsToReturn = new String[] { KEY_TITLE, KEY_NOTE};

        //make sure you get your search by string pass correctly!
        Cursor mCursor = queryBuilder.query(db, asColumnsToReturn,null,null, null,null,""+KEY_TITLE+" ASC");

        return mCursor;
    }

    public String getNote(Cursor c) {
        return(c.getString(1));
    }
    public Cursor getList(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NOTES+"", null);
        return data;
    }

}
