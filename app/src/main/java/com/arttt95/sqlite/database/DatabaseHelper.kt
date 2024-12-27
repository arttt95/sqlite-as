package com.arttt95.sqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context,
    "loja.db",
    null,
    2
){

    companion object {
        const val TABELA_PRODUTOS = "produtos"
        const val ID_PRODUTO = "id"
        const val TITULO = "titulo"
        const val DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        Log.i("info_db", "Executou onCreate -> Helper")

        val sql =
            "CREATE TABLE IF NOT EXISTS " +
                "$TABELA_PRODUTOS" +
                    "(" +
                        "$ID_PRODUTO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "$TITULO VARCHAR(100), " +
                        "$DESCRICAO TEXT" +
                    ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db", "Sucesso ao criar a table -> onCreate -> Helper")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar a table -> onCreate ->Helper")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("info_db", "Executou onUpgrade -> Helper")
    }

}