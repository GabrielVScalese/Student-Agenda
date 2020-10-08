package com.example.student_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Tela_Inserir extends AppCompatActivity {

    Button btnInserir;
    EditText edtRA;
    EditText edtNome;
    EditText edtEmail;
    Aluno umAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__inserir);

        btnInserir = findViewById(R.id.btnInserir);
        edtRA = findViewById(R.id.edtRAInser);
        edtNome = findViewById(R.id.edtNomeInser);
        edtEmail = findViewById(R.id.edtEmailInser);

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                umAluno = null;
                String ra = edtRA.getText().toString();
                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();

                consultarAluno(ra);

                if (validar(ra, nome, email))
                {
                    Aluno aluno = new Aluno (ra, nome, email);
                    inserirAluno(aluno);
                }
            }
        });
    }

    public void inserirAluno (Aluno aluno)
    {
        Call<Object> call = new RetrofitConfig().getService().inserirAluno(aluno);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Response<Object> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Toast.makeText(Tela_Inserir.this, "Aluno incluído com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
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
                    setAluno(aluno);
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public boolean validar (String ra, String nome, String email)
    {
        if (ra.equals(""))
        {
            Toast.makeText(Tela_Inserir.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(ra) < 0)
        {
            Toast.makeText(Tela_Inserir.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false ;
        }

        if (ra.length() != 5)
        {
            Toast.makeText(Tela_Inserir.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nome.equals(""))
        {
            Toast.makeText(Tela_Inserir.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nome.contains("[0-9]"))
        {
            Toast.makeText(Tela_Inserir.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.equals(""))
        {
            Toast.makeText(Tela_Inserir.this, "Email inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.contains("[0-9]"))
        {
            Toast.makeText(Tela_Inserir.this, "Email inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (umAluno != null)
        {
            Toast.makeText(Tela_Inserir.this, "RA existente!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void setAluno (Aluno aluno)
    {
        umAluno = new Aluno (aluno.getRA(), aluno.getNome(), aluno.getEmail());
    }
}