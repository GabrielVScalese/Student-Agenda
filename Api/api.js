"use strict";

const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const port = 3000;
const connStr =
  "Server=regulus.cotuca.unicamp.br;Database=BD19171;User Id=BD19171;Password=;";
const sql = require("mssql");

sql
  .connect(connStr)
  .then((conn) => (global.conn = conn))
  .catch((err) => console.log("erro: " + err));

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.listen(port);
console.log("API funcionando!");

const router = express.Router();

async function execQuery (query)
{
    try
    {
        return global.conn.request().query(query);
    }
    catch (error)
    {
        console.log(error);
    }
}

router.get("/", (req, res) => {
    return res.json({Message: "API rodando!"});
});

router.get("/api/alunos", async (req, res) => {
    try
    {
        const alunos = await execQuery("SELECT * FROM KITCHNY.DBO.ALUNOS");
    
        return res.json(alunos.recordset);
    }
    catch (error)
    {
        return res.status(500).send({error: "Erro na busca de alunos!" });
    }
});

router.get("/api/aluno/:id?", async (req, res) => {
    try
    {
        const ra = req.params.id;

        const aluno = await getAluno(ra);

        if (aluno == undefined)
            return res.status(406).send({error: "Aluno não encontrado!"});

        return res.json(aluno);
    }
    catch (error)
    {
        return res.status(500).send({error: "Erro na busca de aluno!" });
    }
});

router.post("/api/insertAluno", async (req, res) => {
    try
    {
        const aluno = req.body;

        await execQuery ("INSERT INTO KITCHNY.DBO.ALUNOS VALUES ('" + aluno.ra + "', '" + 
        aluno.nome + "', '" + aluno.email + "')");

        return res.status(200).send({succesfull: "Aluno incluído com sucesso!" });
    }
    catch (error)
    {
        return res.status(500).send({error: "Erro na inclusão de aluno!"});
    }
});

router.put("/api/updateAluno", async (req, res) => {
    try
    {
        const aluno = req.body;

        if (await getAluno(aluno.ra) == undefined)
            return res.status(406).send({error: "Aluno não encontrado!"});

        await execQuery ("UPDATE KITCHNY.DBO.ALUNOS \n SET NOME = '" +
        aluno.nome +
        "', EMAIL = '" +
        aluno.email +
        "' WHERE RA = '" +
        aluno.ra +
        "';");

        return res.status(200).send({succesfull: "Aluno alterado com sucesso!" });
    }
    catch (error)
    {
        return res.status(500).send({error: "Erro na alteração de aluno!"});
    }
});

router.delete("/api/deleteAluno/:id?", async (req, res) => {
    try
    {
        const ra = req.params.id;

        if (await getAluno(ra) == undefined)
            return res.status(406).send({error: "Aluno não encontrado!"});

        await execQuery ("DELETE FROM KITCHNY.DBO.ALUNOS WHERE RA = '" + ra + "'");

        return res.status(200).send({succesfull: "Aluno excluído com sucesso!" });
    }
    catch (error)
    {
        return res.status(500).send({error: "Erro na exclusão de aluno!"});
    }
});

async function getAluno (ra)
{
    const ret = await execQuery ("SELECT * FROM KITCHNY.DBO.ALUNOS WHERE RA = '" + ra + "'");

    const aluno = ret.recordset[0];

    return aluno;
} 

app.use(router);

