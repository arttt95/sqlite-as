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
    override fun onCreate(db: SQLiteDatabase?) {

        Log.i("info_db", "Executou onCreate -> Helper")

        val sql = "CREATE TABLE IF NOT EXISTS " +
                    "produtos(" +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "titulo VARCHAR(100), " +
                        "descricao TEXT" +
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