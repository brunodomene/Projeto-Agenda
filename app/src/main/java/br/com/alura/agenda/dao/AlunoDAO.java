package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by bruno on 20/03/2018.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {

        super(context, "Agenda", null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT," +
                "site TEXT," +
                "caminhoFoto TEXT," +
                "nota REAL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch (oldVersion) {
            case 1:
                sql = "Alter table Alunos add colunm fotoCaminho text;";
                db.execSQL(sql);
        }
    }


    public void insere(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        database.insert("Alunos", null, dados);

    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();

        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }

        return alunos;
    }

    public void deleta(Aluno aluno){
        SQLiteDatabase database = getWritableDatabase();
        String[] argumentos = {String.valueOf(aluno.getId())};
        database.delete("Alunos", "id = ?", argumentos);

    }

    public void atualiza(Aluno aluno) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);
        String[] parametros = {aluno.getId().toString()};

        database.update("Alunos", dados, "id = ?", parametros);
    }
}
