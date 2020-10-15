package com.example.student_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Tela_Consulta extends AppCompatActivity {

    private Button btnConsultar;
    private Button btnExcluir;
    private TextView titulo;
    private EditText edtRA;
    private ListView listaView;
    private List<Aluno> listaAlunos;

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
                    if (validar(ra))
                        consultarAluno(ra);
                    else
                        Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ra = edtRA.getText().toString();

                if (ra.equals(""))
                    Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
                else
                    if (validar(ra))
                        excluirAluno(edtRA.getText().toString());
                    else
                        Toast.makeText(Tela_Consulta.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarAlunos ()
    {
        Call<List<Aluno>> call = new RetrofitConfig().getService().getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Response<List<Aluno>> response, Retrofit retrofit) {
                    List<Aluno> listaAlunos = new ArrayList<Aluno>();
                    listaAlunos = response.body();
                    mostrarTudoListView(listaAlunos);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(Tela_Consulta.this, "Falha na consulta de alunos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarAluno (String ra)
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
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Tela_Consulta.this, jObjError.getString("status"), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    { }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(Tela_Consulta.this, "Falha na consulta de aluno!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void excluirAluno (String ra)
    {
        Call<Status> call = new RetrofitConfig().getService().excluirAluno(ra);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Response<Status> response, Retrofit retrofit) {
                if (response.isSuccess())
                    Toast.makeText(Tela_Consulta.this, "Aluno excluído com sucesso!", Toast.LENGTH_SHORT).show();
                else
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Tela_Consulta.this, jObjError.getString("status"), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    { }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(Tela_Consulta.this, "Falha na exclusão de aluno!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validar (String ra)
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

    private void mostrarTudoListView (List<Aluno> lista)
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}