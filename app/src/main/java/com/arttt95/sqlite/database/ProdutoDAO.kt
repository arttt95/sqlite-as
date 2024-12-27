package com.arttt95.sqlite.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.arttt95.sqlite.model.Produto

class ProdutoDAO(context: Context) : IProdutoDAO {

    private val context = context
    private val writer = DatabaseHelper(context).writableDatabase
    private val reader = DatabaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {

        val titulo = produto.titulo
        val descricao = produto.descricao

//        val sql =
//            "INSERT INTO ${DatabaseHelper.TABELA_PRODUTOS} VALUES(NULL, '${titulo}', '${descricao}');"

        val valores = ContentValues()
        valores.put(DatabaseHelper.TITULO, titulo)
        valores.put(DatabaseHelper.DESCRICAO, descricao)

        try {
//            writer.execSQL(sql)

            writer.insert(
                DatabaseHelper.TABELA_PRODUTOS,
                null,
                valores
            )
            Log.i("info_db", "Sucesso ao SALVAR registro DAO")
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao SALVAR registro DAO")
            return false
        }

        return true

    }

    override fun atualizar(produto: Produto): Boolean {

        val titulo = produto.titulo
        val descricao = produto.descricao
        val idProduto = produto.idProduto

        /*val sql =
            "UPDATE " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "SET " +
                "${DatabaseHelper.TITULO} = '${titulo}', " +
                "${DatabaseHelper.DESCRICAO} = '${descricao}' " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = $idProduto;"*/

        val valores = ContentValues()
        valores.put(DatabaseHelper.TITULO, titulo) // Chave & Valor -> Coluna & Conteúdo
        valores.put(DatabaseHelper.DESCRICAO, descricao)

        val condicao = " ${DatabaseHelper.ID_PRODUTO} = ?" // Where é suprimido -> nome da coluna

        val args = arrayOf(idProduto.toString()) // Convertendo para string -> valor da coluna


        try {

            writer.update(
                DatabaseHelper.TABELA_PRODUTOS,
                valores,
                condicao,
                args // Precisa ser String
            )
//            writer.execSQL(sql)
            Log.i("info_db", "Sucesso ao atualizar registro(s) DAO")
            Toast.makeText(context, "Sucesso ao atualizar o produto", Toast.LENGTH_LONG).show()
        } catch(e: Exception) {
            Log.i("info_db", "Erro ao atualizar registro(s) DAO")
            Toast.makeText(context, "Erro ao atualizar o produto", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    override fun remover(idProduto: Int): Boolean {

        /*val sql =
            "DELETE FROM " +
                "${DatabaseHelper.TABELA_PRODUTOS} " +
            "WHERE " +
                "${DatabaseHelper.ID_PRODUTO} = ${idProduto};"*/

        val condicao = "${DatabaseHelper.ID_PRODUTO} = ?"

        val args = arrayOf(idProduto.toString())

        try {
            writer.delete(
                DatabaseHelper.TABELA_PRODUTOS,
                condicao,
                args
            )
//            writer.execSQL(sql)
            Toast.makeText(context, "Sucesso ao remover o produtro.", Toast.LENGTH_LONG).show()
            Log.i("info_db", "Sucesso ao remover registro(s) main")
        } catch(e: Exception) {
            Toast.makeText(context, "Erro ao remover o produtro.", Toast.LENGTH_LONG).show()
            Log.i("info_db", "Erro ao remover registro(s) main")
            return false
        }

        return true

    }

    override fun listar(): List<Produto> {

        val listaProdutos = mutableListOf<Produto>()

        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS};"

        val cursor = reader.rawQuery(sql, null)

        try {

            if(cursor.moveToFirst()) {

                do {
                    val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_PRODUTO)
                    val indiceTitulo = cursor.getColumnIndex(DatabaseHelper.TITULO)
                    val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)

                    if (indiceId != -1 && indiceTitulo != -1 && indiceDescricao != -1) {

                        val idProduto = cursor.getInt(indiceId)
                        val titulo = cursor.getString(indiceTitulo)
                        val descricao = cursor.getString(indiceDescricao)

                        val produto = Produto(idProduto, titulo, descricao)
                        listaProdutos.add(produto)

                    } else {
                        Log.i("info_db", "Erro! Índice de coluna inválido")
                    }

                } while (cursor.moveToNext())
            }

            Log.i("info_db", "Sucesso listagem -> DAO")
            
        } catch (e: Exception) {
            Log.i("info_db", "Erro ao listar registros ProdutoDAO: ${e.message}")
        } finally {
            cursor.close()
        }

        /*val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_PRODUTO)
        val indiceTitulo = cursor.getColumnIndex(DatabaseHelper.TITULO)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)

        while ( cursor.moveToNext() ){// false ou true

            val idProduto = cursor.getInt(indiceId)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)
            //Log.i("info_db", "id: $idProduto - $titulo")

            listaProdutos.add(
                Produto(idProduto, titulo, descricao)
            )

        }

        cursor.close()*/

        return listaProdutos

    }


}