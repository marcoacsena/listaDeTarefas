package com.example.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "DB_TAREFAS";
    public static String TABLE_NAME = "tarefas";

    public static final String KEY = "id";
    public static final String NOMETAREFA = "nomeTarefa";

    public DbHelper(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nomeTarefa TEXT NOT NULL ); ";

//        String sql1 = "CREATE TABLE IF NOT EXISTS "
//                + TABLE_NAME + "("
//                + KEY + " INTEGER (10) PRIMARY KEY, "
//                + NOMETAREFA + " TEXT " + ")";

        try{
            db.execSQL(sql);
            Log.d("Informações_sobre_BD", "Tabela criada com sucesso!");
        }catch(Exception e) {
            Log.d("Informações_sobre_BD", "Erro ao criar a tabela " +e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " +TABLE_NAME +" ;" ;

        try{
            db.execSQL(sql);
            onCreate(db);
            Log.d("Informações_Sobre_BD", "Tabela atualizada com sucesso!");
        }catch(Exception e) {
            Log.d("Informações_Sobre_BD", "Erro ao atualizar a tabela " +e.getMessage());
        }

    }

}
