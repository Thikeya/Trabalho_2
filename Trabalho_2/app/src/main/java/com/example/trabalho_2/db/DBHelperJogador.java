package com.example.trabalho_2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelperJogador extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "jogador.db";
    private static final String TABLE_NAME = "jogador";
    private static final String COLUMN_ID = "jogadorId";
    private static final String COLUMN_ID_TIME = "timeId";
    private static final String COLUMN_NAME = "jogadorNome";
    private static final String COLUMN_CPF = "jogadorCpf";
    private static final String COLUMN_ANONASCIMENTO = "jogadorAnoNascimento";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE emprestimo" +
            "(jogadorId integer primary key autoincrement, timeId integer not null, jogadorNome text not null," +
            "jogadorCpf text not null, jogadorAnoNascimento integer not null);";

    public DBHelperJogador(@Nullable Context context) {
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

    public void inserirJogador(Jogador e){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_TIME, e.getTimeId());
        values.put(COLUMN_NAME, e.getJogadorNome());
        values.put(COLUMN_CPF, e.getJogadorCpf());
        values.put(COLUMN_ANONASCIMENTO, e.getJogadorAnoNascimento());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Jogador> selectAllJogadores() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM jogador", null);
        ArrayList<Jogador> listaJogador = new ArrayList<Jogador>();
        while(cursor.moveToNext()){
            Jogador e = new Jogador();
            e.setJogadorId(cursor.getInt(0));
            e.setTimeId(cursor.getInt(1));
            e.setJogadorNome(cursor.getString(2));
            e.setJogadorAnoNascimento(cursor.getInt(3));
            e.setJogadorCpf(cursor.getString(4));
            listaJogador.add(e);
        }
        return listaJogador;
    }

    public void updateJogador(Jogador e) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, e.getJogadorNome());
        values.put(COLUMN_ANONASCIMENTO, e.getJogadorAnoNascimento());
        values.put(COLUMN_CPF, e.getJogadorCpf());
        values.put(COLUMN_ID_TIME, e.getTimeId());
        String[] args = {String.valueOf(e.getJogadorId())};
        db.update(TABLE_NAME,values,"jogadorId=?", args);
        db.close();
    }

    public long deleteJogador(Jogador e) {
        long retornoBD;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(e.getJogadorId())};
        retornoBD = db.delete(TABLE_NAME, COLUMN_ID + "=?", args);
        return retornoBD;
    }

    public boolean jogadorExistsOnTime(int timeId) {
        db = this.getReadableDatabase();
        boolean jogadorExist = false;
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_CPF, COLUMN_ANONASCIMENTO};
        String[] args = {String.valueOf(timeId)};
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, "timeId=?", args, null, null, null, null);

        Jogador e = new Jogador();
        while(cursor.moveToNext()) {
            e.setJogadorId(cursor.getInt(0));
            e.setJogadorNome(cursor.getString(1));
            e.setJogadorCpf(cursor.getString(2));
            e.setJogadorAnoNascimento(cursor.getInt(3));
        }
        return jogadorExist;
    }
}
