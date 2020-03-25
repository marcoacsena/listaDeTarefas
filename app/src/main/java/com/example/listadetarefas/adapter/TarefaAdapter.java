package com.example.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.Tarefa;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private List<Tarefa> listaDeTarefas;

    public TarefaAdapter(List<Tarefa> listaDeTarefas) {
        this.listaDeTarefas = listaDeTarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemDaListaDeTarefas = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter,
                parent, false);

        return new TarefaViewHolder(itemDaListaDeTarefas);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, int position) {

        Tarefa tarefa = listaDeTarefas.get(position);
        holder.tvListaTarefaAdapter.setText(tarefa.getNomeTarefa());

    }

    @Override
    public int getItemCount() {
        return this.listaDeTarefas.size();
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder{

        private TextView tvListaTarefaAdapter;

        public TarefaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListaTarefaAdapter = itemView.findViewById(R.id.tvListaTarefaAdapter);
        }
    }
}
