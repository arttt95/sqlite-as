package com.arttt95.sqlite

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.sqlite.database.DatabaseHelper
import com.arttt95.sqlite.database.ProdutoDAO
import com.arttt95.sqlite.databinding.ActivityMainBinding
import com.arttt95.sqlite.model.Produto

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

    private fun salvar() {

        val titulo = binding.editProduto.text.toString()
        val descricao = binding.editDescricao.text.toString()

        val produtoDAO = ProdutoDAO(this)

        val produto = Produto(
            -1,
            titulo,
            descricao
        )

        produtoDAO.salvar(produto)

    }

    private fun atualizar() {

        val titulo = binding.editProduto.text.toString()
        val descricao = binding.editDescricao.text.toString()

        val produtoDAO = ProdutoDAO(this)

        val produto = Produto(
            7,
            titulo,
            descricao
        )

        produtoDAO.atualizar(produto)

    }

    private fun remover() {

        val produtoDAO = ProdutoDAO(this)

        produtoDAO.remover(7)
    }

    private fun listar() {

        val produtoDAO = ProdutoDAO(this)

        val listaProdutos = produtoDAO.listar()

        if(listaProdutos.isNotEmpty()) {
            listaProdutos.forEach { produto ->
                Log.i(
                    "info_db",
                    "ID: ${produto.idProduto} | Título: ${produto.titulo}")
            }
        }

    }

}