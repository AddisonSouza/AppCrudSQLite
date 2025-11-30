package com.example.appcrudsqlite.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "posto.db"
        const val DATABASE_VERSION = 1

        const val TBL_CATEGORIA = "categoria"
        const val TBL_CLIENTE = "cliente"
        const val TBL_PRODUTO = "produto"
        const val TBL_VENDA = "venda"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoria = """
            CREATE TABLE $TBL_CATEGORIA (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              nome TEXT NOT NULL
            );
        """.trimIndent()

        val createCliente = """
            CREATE TABLE $TBL_CLIENTE (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              nome TEXT NOT NULL,
              cpf TEXT,
              telefone TEXT,
              email TEXT,
              endereco TEXT
            );
        """.trimIndent()

        val createProduto = """
            CREATE TABLE $TBL_PRODUTO (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              descricao TEXT NOT NULL,
              quantidade INTEGER NOT NULL,
              preco REAL NOT NULL,
              idCategoria INTEGER,
              FOREIGN KEY(idCategoria) REFERENCES $TBL_CATEGORIA(id)
            );
        """.trimIndent()

        val createVenda = """
            CREATE TABLE $TBL_VENDA (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              idCliente INTEGER,
              idProduto INTEGER,
              precoUnitario REAL,
              quantidade INTEGER,
              dataVenda TEXT,
              FOREIGN KEY(idCliente) REFERENCES $TBL_CLIENTE(id),
              FOREIGN KEY(idProduto) REFERENCES $TBL_PRODUTO(id)
            );
        """.trimIndent()

        db.execSQL(createCategoria)
        db.execSQL(createCliente)
        db.execSQL(createProduto)
        db.execSQL(createVenda)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TBL_VENDA")
        db.execSQL("DROP TABLE IF EXISTS $TBL_PRODUTO")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CLIENTE")
        db.execSQL("DROP TABLE IF EXISTS $TBL_CATEGORIA")
        onCreate(db)
    }
}
