package com.arttt95.sqlite

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.sqlite.database.DatabaseHelper
import com.arttt95.sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bancoDados by lazy {
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {

            btnSalvar.setOnClickListener {
                salvar()
            }

            btnListar.setOnClickListener {
                listar()
            }

            btnAtualizar.setOnClickListener {
                atualizar()
            }

            btnRemover.setOnClickListener {
                remover()
            }

        }

    }

    private fun remover() {

        val sql =
            "DELETE FROM " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = 3;"


        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("info_db", "Sucesso ao remover registro(s) main")
        } catch(e: Exception) {
            Log.i("info_db", "Erro ao remover registro(s) main")
        }

    }

    private fun atualizar() {

        val titulo = binding.editProduto.text.toString()
        val descricao = binding.editDescricao.text.toString()

        val sql =
            "UPDATE " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "SET " +
                "${DatabaseHelper.TITULO} = '$titulo', " +
                "${DatabaseHelper.DESCRICAO} = '$descricao' " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = 1;"


        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("info_db", "Sucesso ao atualizar registro(s) main")
        } catch(e: Exception) {
            Log.i("info_db", "Erro ao atualizar registro(s) main")
        }

    }

    private fun listar() {

        val sql =
            "SELECT " +
                "* " +
            "FROM " +
                "${DatabaseHelper.TABELA_PRODUTOS};"
        val cursor = bancoDados.readableDatabase.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex("${DatabaseHelper.ID_PRODUTO}")
        val indiceTitulo = cursor.getColumnIndex("${DatabaseHelper.TITULO}")
        val indiceDescricao = cursor.getColumnIndex("${DatabaseHelper.DESCRICAO}")

        try {
            Log.i("info_db", "Sucesso ao listar registros main")

            while (cursor.moveToNext()) {

                val idProduto = cursor.getInt(indiceId)
                val titulo = cursor.getString(indiceTitulo)
                val descricao = cursor.getString(indiceDescricao)

                Log.i(
                    "info_db",
                    "Posição: $idProduto | Título: $titulo | Descrição: $descricao "
                )

            }

        } catch(e: Exception) {
            Log.i("info_db", "Erro ao listar registros main")
        }

    }

    private fun salvar() {

        val titulo = binding.editProduto.text.toString()
        val descricao = binding.editDescricao.text.toString()

        val sql = "INSERT INTO " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
                "VALUES(" +
                "NULL, " +
                "'$titulo', " +
                "'$descricao'" +
                ");"

        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("info_db", "Sucesso ao inserir registro main")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao inserir registro main")
        }
    }

}