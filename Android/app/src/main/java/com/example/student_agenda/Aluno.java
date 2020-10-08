package com.example.student_agenda;

public class Aluno {

    private String ra, nome, email;


    public Aluno(String ra, String nome, String email)
    {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
    }

    public String getRA()
    {
        return ra;
    }

    public void setRA(String ra) throws Exception
    {
        if(!(ra == null || ra.equals("")))
            this.ra = ra;

        throw new Exception("RA invalido!");
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome) throws Exception
    {
        if(!(nome == null || nome.equals("")))
            this.nome = nome;

        throw new Exception("Nome invalido!");
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) throws Exception
    {
        if(!(email == null || email.equals("")))
            this.email = email;

        throw new Exception("Email invalido!");
    }
}
