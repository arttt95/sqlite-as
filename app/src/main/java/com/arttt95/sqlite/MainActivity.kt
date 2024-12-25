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

        }

    }

    private fun salvar() {

        val titulo = binding.editProduto.text.toString()

        val sql = "INSERT INTO " +
                "produtos " +
                "VALUES(" +
                "NULL, " +
                "'${titulo}', " +
                "'Descrição...'" +
                ");"

        try {
            bancoDados.writableDatabase.execSQL(sql)
            Log.i("info_db", "Sucesso ao inserir registro main")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao inserir registro main")
        }
    }

    private fun listar() {
        TODO("Not yet implemented")
    }
}