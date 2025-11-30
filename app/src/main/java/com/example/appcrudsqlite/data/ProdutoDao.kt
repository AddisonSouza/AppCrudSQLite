package com.example.appcrudsqlite.data

import android.content.ContentValues
import android.content.Context
import com.example.appcrudsqlite.model.Produto

class ProdutoDao(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insert(Produto: Produto): Long {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("descricao", Produto.descricao)
            put("quantidade", Produto.quantidade)
            put("preco", Produto.preco)
            put("idCategoria", Produto.idCategoria)
        }
        val id = db.insert(DBHelper.TBL_PRODUTO, null, cv)
        db.close()
        return id
    }

    fun update(Produto: Produto): Int {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("descricao", Produto.descricao)
            put("quantidade", Produto.quantidade)
            put("preco", Produto.preco)
            put("idCategoria", Produto.idCategoria)
        }
        val rows = db.update(DBHelper.TBL_PRODUTO, cv, "id = ?", arrayOf(Produto.id.toString()))
        db.close()
        return rows
    }

    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DBHelper.TBL_PRODUTO, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    fun getAll(): List<Produto> {
        val list = mutableListOf<Produto>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, descricao, quantidade, preco, idCategoria FROM ${DBHelper.TBL_PRODUTO} ORDER BY descricao", null)
        cursor.use {
            while (it.moveToNext()) {
                val p = Produto(
                    id = it.getLong(0),
                    descricao = it.getString(1),
                    quantidade = it.getInt(2),
                    preco = it.getDouble(3),
                    idCategoria = if (!it.isNull(4)) it.getLong(4) else null
                )
                list.add(p)
            }
        }
        db.close()
        return list
    }

    fun getById(id: Long): Produto? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, descricao, quantidade, preco, idCategoria FROM ${DBHelper.TBL_PRODUTO} WHERE id = ?", arrayOf(id.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                return Produto(
                    id = it.getLong(0),
                    descricao = it.getString(1),
                    quantidade = it.getInt(2),
                    preco = it.getDouble(3),
                    idCategoria = if (!it.isNull(4)) it.getLong(4) else null
                )
            }
        }
        db.close()
        return null
    }
}
