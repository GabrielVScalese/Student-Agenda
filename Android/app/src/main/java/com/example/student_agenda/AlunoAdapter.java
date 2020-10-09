package com.example.student_agenda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AlunoAdapter extends ArrayAdapter<Aluno> {

    private Context context;
    private int layoutResourceId;
    private List<Aluno> dados;

    public AlunoAdapter(@NonNull Context context, int resource, @NonNull List<Aluno> dados) {
        super(context, resource, dados);

        this.context = context;
        this.layoutResourceId = resource;
        this.dados = dados;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater layoutinflater = LayoutInflater.from(context);
            view = layoutinflater.inflate(layoutResourceId, parent, false);
        }

        TextView ra = view.findViewById(R.id.txtRA);
        TextView nome = view.findViewById(R.id.txtNome);
        TextView email = view.findViewById(R.id.txtEmail);

        Aluno aluno = dados.get(position);
        ra.setText("RA: " + aluno.getRA());
        nome.setText("Nome: " + aluno.getNome());
        email.setText("Email: " + aluno.getEmail());

        return view;
    }
}
