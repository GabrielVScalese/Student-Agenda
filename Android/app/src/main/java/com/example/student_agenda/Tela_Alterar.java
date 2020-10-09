package com.example.student_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Tela_Alterar extends AppCompatActivity
{

    private Button btnAlterar;
    private EditText edtRA, edtNome, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__alterar);

        btnAlterar = findViewById(R.id.btnAlterarAlter);
        edtRA = findViewById(R.id.edtRAAlter);
        edtNome = findViewById(R.id.edtNomeAlter);
        edtEmail = findViewById(R.id.edtEmailAlter);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ra = edtRA.getText().toString();
                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();

                if (validar(ra, nome, email))
                {
                    try
                    {
                        Aluno aluno = new Aluno (ra, nome, email);
                        alterarAluno(aluno);
                    }
                    catch (Exception e)
                    {}
                }

            }
        });
    }

    private void alterarAluno (Aluno aluno)
    {
        Call<Status> call = new RetrofitConfig().getService().alterarAluno(aluno);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Response<Status> response, Retrofit retrofit) {
                if (response.isSuccess())
                    Toast.makeText(Tela_Alterar.this, "Aluno alterado com sucesso!", Toast.LENGTH_SHORT).show();
                else
                {
                    try
                    {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Tela_Alterar.this, jObjError.getString("status"), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    { }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(Tela_Alterar.this, "Falha na exclusão de aluno!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validar (String ra, String nome, String email)
    {
        if (ra.equals(""))
        {
            Toast.makeText(Tela_Alterar.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(ra) < 0)
        {
            Toast.makeText(Tela_Alterar.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false ;
        }

        if (ra.length() != 5)
        {
            Toast.makeText(Tela_Alterar.this, "RA inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nome.equals(""))
        {
            Toast.makeText(Tela_Alterar.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nome.contains("[0-9]"))
        {
            Toast.makeText(Tela_Alterar.this, "Nome inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.equals(""))
        {
            Toast.makeText(Tela_Alterar.this, "Email inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.contains("[0-9]"))
        {
            Toast.makeText(Tela_Alterar.this, "Email inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}