package com.arttt95.sqlite.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context,
    "loja.db",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " +
                    "produtos(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nome VARCHAR(100)," +
                        "descricao TEXT" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}