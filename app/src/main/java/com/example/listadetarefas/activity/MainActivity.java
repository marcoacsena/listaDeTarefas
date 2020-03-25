package com.example.listadetarefas.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.R;
import com.example.listadetarefas.Tools;
import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.helper.DbHelper;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.example.listadetarefas.helper.TarefaDAO;
import com.example.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListaDeTarefas;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaDeTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;
    //private SelectionTracker<Long> selectionTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tarefas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void initComponent() {

        //Configurar recyclerview
        rvListaDeTarefas = findViewById(R.id.rvListaDeTarefas);


        //Configurar evento de clique
        rvListaDeTarefas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        rvListaDeTarefas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Obtém tarefa selecionada para edição:
                                tarefaSelecionada = listaDeTarefas.get(position);


                                //Envia tarefa selecionada para a ctivity AdicionarTarefa:
                                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity(intent);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Obtém tarefa selecionada para exclusão:
                                tarefaSelecionada = listaDeTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("Exclusão de Tarefa");
                                dialog.setMessage("Deseja excluir a tarefa " +"'" +tarefaSelecionada.getNomeTarefa() +"'" +" ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                                        if(tarefaDAO.deletar(tarefaSelecionada)){
                                            carregarListaDeTarefas();

                                            Toast.makeText(MainActivity.this, "Tarefa excluída com suceso!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //Exibe o diálago
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaDeTarefas(){

        //Listar tarefas
//        Tarefa tarefa1 = new Tarefa();
//        tarefa1.setNomeTarefa("Estudar Flutter");
//        listaDeTarefas.add(tarefa1);
//
//        Tarefa tarefa2 = new Tarefa();
//        tarefa2.setNomeTarefa("Estudar React Native");
//        listaDeTarefas.add(tarefa2);

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaDeTarefas = tarefaDAO.listarTarefas();

       /*
        Exibe lista de tarefas no recyclerview
        */

        //Configurar adapter
        tarefaAdapter = new TarefaAdapter(listaDeTarefas);

        //Configurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvListaDeTarefas.setLayoutManager(layoutManager);
        rvListaDeTarefas.setHasFixedSize(true);
        rvListaDeTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        rvListaDeTarefas.setAdapter(tarefaAdapter);

    }

    @Override
    protected void onStart() {
        carregarListaDeTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
