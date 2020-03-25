package com.example.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.R;
import com.example.listadetarefas.helper.DbHelper;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editeTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editeTarefa = findViewById(R.id.tiTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if ( tarefaAtual != null ){
            editeTarefa.setText( tarefaAtual.getNomeTarefa() );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            //Executa ação para editar ou salvar item

            case R.id.itemSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if(tarefaAtual != null){
                    //Então a opção é de editar a tarefa
                    String tarefaEditada = editeTarefa.getText().toString();

                    if(!tarefaEditada.isEmpty()){
                        Tarefa tarefa = new Tarefa();

                        tarefa.setNomeTarefa(tarefaEditada);
                        tarefa.setId(tarefaAtual.getId());

                        if(tarefaDAO.atualizar(tarefa)) {
                            finish();
                            Toast.makeText(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_LONG).show();

                        }else{
                            finish();
                            Toast.makeText(this, "Não foi possível atualizar tarefa!", Toast.LENGTH_LONG).show();}


                    }

                } else {
                    //Salvar Tarefa:
                    String nomeTarefa = editeTarefa.getText().toString();

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if(tarefaDAO.salvar(tarefa)){
                            finish();
                            Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(this, "Não foi possível salvar a tarefa. Tente nocamente!", Toast.LENGTH_LONG).show();
                        }
                    }


                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}