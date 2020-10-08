package com.example.student_agenda;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class RetrofitConfig {
    private Retrofit retrofit;

    public RetrofitConfig ()
    {
        this.retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.28:3000/api/alunos/").
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Service getService ()
    {
        return this.retrofit.create(Service.class);
    }
}
