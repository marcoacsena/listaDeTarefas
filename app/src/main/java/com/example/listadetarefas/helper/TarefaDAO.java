package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.listadetarefas.activity.AdicionarTarefaActivity;
import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

import static com.example.listadetarefas.helper.DbHelper.NOMETAREFA;
import static com.example.listadetarefas.helper.DbHelper.TABLE_NAME;


public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {

        DbHelper dbHelper = new DbHelper(context);
        escreve = dbHelper.getWritableDatabase();
        le = dbHelper.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nomeTarefa", tarefa.getNomeTarefa());
        
        try {

            escreve.insert(TABLE_NAME, null, cv);
            escreve.close();
            Log.d("Informações_sobre_BD", "Tarefa salva com sucesso!");
            
        }catch (Exception e){

            Log.d("Informações_sobre_BD", "A tarefa não pode ser salva!" +e.getMessage());
            return false;
        }
        
        return true;
    }

    @Override
    public List<Tarefa> listarTarefas() {

        List<Tarefa> listaDeTarefas = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ;";

        Cursor cursor = le.rawQuery(sql,null);

        while(cursor.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeTarefa = cursor.getString(cursor.getColumnIndex("nomeTarefa"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            listaDeTarefas.add(tarefa);

        }

        return listaDeTarefas;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nomeTarefa", tarefa.getNomeTarefa() );

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.update(DbHelper.TABLE_NAME, cv, "id=?", args );
            Log.d("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DbHelper.TABLE_NAME, "id=?", args);
            Log.d("INFO", "Tarefa atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }

}
