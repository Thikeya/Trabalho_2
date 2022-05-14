package com.example.trabalho_2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelperTime  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "time.db";
    private static final String TABLE_NAME = "time";
    private static final String COLUMN_ID = "timeId";
    private static final String COLUMN_DESCRICAO = "timeDescricao";


    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE time" +
            "(timeId integer primary key autoincrement, timeDescricao text not null);";

    public DBHelperTime(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void inserirTime(Time e){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRICAO, e.getTimeDescricao());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Time> selectAllTimes() {
        db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_DESCRICAO};

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, "upper(timeDescricao)", null);

        ArrayList<Time> listaTime = new ArrayList<Time>();
        while(cursor.moveToNext()){
            Time e = new Time();
            e.setTimeId(cursor.getInt(0));
            e.setTimeDescricao(cursor.getString(1));
            listaTime.add(e);
        }
        return listaTime;
    }

    public void updateTime(Time e) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRICAO, e.getTimeDescricao());
        String[] args = {String.valueOf(e.getTimeId())};
        db.update(TABLE_NAME,values,"timeId=?", args);
        db.close();
    }

    public long deleteTime(Time e) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(e.getTimeId())};
        retornoBD = db.delete(TABLE_NAME, COLUMN_ID + "=?", args);
        return retornoBD;
    }

    public Time selectOneTime(int timeId) {
        db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_DESCRICAO};

        String[] args = {String.valueOf(timeId)};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, "timeId=?", args, null, null, "upper(timeDescricao)", null);

        Time e = new Time();
        e.setTimeId(cursor.getInt(0));
        e.setTimeDescricao(cursor.getString(1));
        return e;
    }
}
