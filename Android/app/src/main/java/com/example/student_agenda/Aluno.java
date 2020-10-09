package com.example.student_agenda;

public class Aluno
{
    private String ra, nome, email;

    public Aluno(String ra, String nome, String email) throws Exception
    {
        setRA(ra);
        setNome(nome);
        setEmail(email);
    }

    public String getRA()
    {
        return ra;
    }

    public String getNome()
    {
        return nome;
    }

    public String getEmail()
    {
        return email;
    }

    public void setRA(String ra) throws Exception
    {
        if (ra == null || ra.equals(""))
            throw new Exception("RA invalido!");

        this.ra = ra;
    }

    public void setNome (String nome) throws Exception
    {
        if (nome == null || nome.equals(""))
            throw new Exception("Nome invalido!");

        this.nome = nome;
    }

    public void setEmail(String email) throws Exception
    {
        if (email == null || email.equals(""))
            throw new Exception ("Email inválido!");

        this.email = email;
    }
}
