package com.example.student_agenda;

import java.util.List;

import javax.net.ssl.SSLEngineResult;

import retrofit.Call;
import retrofit.http.*;

public interface Service
{
    @GET("alunos")
    Call<List<Aluno>> getAlunos ();

    @GET("aluno/{ra}")
    Call<Aluno> getAluno (@Path("ra") String nome);

    @POST("insertAluno")
    Call<Status> inserirAluno (@Body Aluno aluno);

    @PUT("updateAluno")
    Call<Status> alterarAluno (@Body Aluno aluno);

    @DELETE("deleteAluno/{ra}")
    Call<Status> excluirAluno (@Path("ra") String ra);
}
