package com.example.student_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Tela_Consulta extends AppCompatActivity {

    Button btnConsultar;
    Button btnExcluir;
    TextView titulo;
    EditText edtRA;
    ListView listaView;
    List<Aluno> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__consulta);

        btnConsultar = findViewById(R.id.btnConsultarConsul);
        btnExcluir = findViewById(R.id.btnExcluir);
        titulo = findViewById(R.id.txtTitulo);
        edtRA = findViewById(R.id.edtRAConsul);
        listaView = findViewById(R.id.listaAlunos);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ra = edtRA.getText().toString();

                if (ra.equals(""))
                    consultarAlunos();
                else
                {
                    if (validar(ra))
                        consultarAluno(ra);
                    else
                        Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ra = edtRA.getText().toString();

                if (ra.equals(""))
                    Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
                else
                    if (Integer.parseInt(ra) < 0)
                        Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
                    else if (ra.length() != 5)
                             Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
                    else
                        excluirAluno(edtRA.getText().toString());
            }
        });
    }

    public void consultarAlunos ()
    {
        Call<List<Aluno>> call = new RetrofitConfig().getService().getAll();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Response<List<Aluno>> response, Retrofit retrofit) {
                    List<Aluno> listaAlunos = new ArrayList<Aluno>();
                    listaAlunos = response.body();
                    mostrarTudoListView(listaAlunos);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void consultarAluno (String ra)
    {
        Call<Aluno> call = new RetrofitConfig().getService().getAluno(ra);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Response<Aluno> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Aluno aluno = response.body();
                    listaAlunos = new ArrayList<>();
                    listaAlunos.add(aluno);
                    mostrarTudoListView(listaAlunos);
                }
                else
                {
                    listaView.clearChoices();
                    Toast.makeText(Tela_Consulta.this, "Aluno não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void excluirAluno (String ra)
    {
        Call<Object> call = new RetrofitConfig().getService().excluirAluno(ra);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(Tela_Consulta.this, "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    listaView.clearChoices();
                    Toast.makeText(Tela_Consulta.this, "Aluno não encontrado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public boolean validar (String ra)
    {
        if (Integer.parseInt(ra) < 0)
        {
            Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (ra.length() != 5)
        {
            Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void mostrarTudoListView (List<Aluno> lista)
    {
        listaView.clearChoices();

        if (lista.size() > 1)
            titulo.setText("Alunos encontrados:");
        else
            titulo.setText("Aluno encontrado:");

        titulo.setVisibility(View.VISIBLE);
        AlunoAdapter adapter = new AlunoAdapter(this, R.layout.aluno_item, lista);
        listaView.setAdapter(adapter);
    }
}