package com.example.student_agenda;

import java.util.List;

import javax.net.ssl.SSLEngineResult;

import retrofit.Call;
import retrofit.http.*;

public interface Service {

    @GET("alunos")
    Call<List<Aluno>> getAll();

    @GET("aluno/{ra}")
    Call<Aluno> getAluno (@Path("ra") String nome);

    @POST("insertAluno")
    Call<Status> inserirAluno (@Body Aluno aluno);

    @PUT("updateAluno")
    Call<Aluno> alterarAluno (@Body Aluno aluno);

    @DELETE("deleteAluno/{ra}")
    Call<Object> excluirAluno (@Path("ra") String ra);
}
