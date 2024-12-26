package com.arttt95.sqlite.database

import android.content.Context
import android.util.Log
import com.arttt95.sqlite.model.Produto

class ProdutoDAO(
    context: Context
) : IProdutoDAO {

    private val writer = DatabaseHelper(context).writableDatabase
    private val reader = DatabaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {

        val titulo = produto.titulo
        val descricao = produto.descricao

        val sql =
            "INSERT INTO " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "VALUES(" +
                "NULL, " +
                "'${titulo}', " +
                "'${descricao}'" +
            ");"

        try {
            writer.execSQL(sql)
            Log.i("info_db", "Sucesso ao inserir registro main")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao inserir registro main")
            return false
        }

        return true

    }

    override fun atualizar(produto: Produto): Boolean {

        val titulo = produto.titulo
        val descricao = produto.descricao
        val idProduto = produto.idProduto

        val sql =
            "UPDATE " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "SET " +
                "${DatabaseHelper.TITULO} = '${titulo}', " +
                "${DatabaseHelper.DESCRICAO} = '${descricao}' " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = $idProduto;"


        try {
            writer.execSQL(sql)
            Log.i("info_db", "Sucesso ao atualizar registro(s) main")
        } catch(e: Exception) {
            Log.i("info_db", "Erro ao atualizar registro(s) main")
            return false
        }

        return true
    }

    override fun remover(idProduto: Int): Boolean {

        val sql =
            "DELETE FROM " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = ${idProduto};"


        try {
            writer.execSQL(sql)
            Log.i("info_db", "Sucesso ao remover registro(s) main")
        } catch(e: Exception) {
            Log.i("info_db", "Erro ao remover registro(s) main")
            return false
        }

        return true

    }

    override fun listar(): List<Produto> {

        val listaProdutos = mutableListOf<Produto>()

        val sql =
            "SELECT " +
                "* " +
            "FROM " +
                "${DatabaseHelper.TABELA_PRODUTOS};"

        val cursor = reader.rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex("${DatabaseHelper.ID_PRODUTO}")
        val indiceTitulo = cursor.getColumnIndex("${DatabaseHelper.TITULO}")
        val indiceDescricao = cursor.getColumnIndex("${DatabaseHelper.DESCRICAO}")

        try {
            Log.i("info_db", "Sucesso ao listar registros main")

            while (cursor.moveToNext()) {

                val idProduto = cursor.getInt(indiceId)
                val titulo = cursor.getString(indiceTitulo)
                val descricao = cursor.getString(indiceDescricao)

                val produto = Produto(idProduto, titulo, descricao)
                listaProdutos.add(produto)

//                Log.i(
//                    "info_db",
//                    "Posição: $idProduto | Título: $titulo | Descrição: $descricao "
//                )

            }

        } catch(e: Exception) {
            Log.i("info_db", "Erro ao listar registros main")
        }

        return listaProdutos

    }


}