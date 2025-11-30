package com.example.appcrudsqlite.data

import android.content.ContentValues
import android.content.Context
import com.example.appcrudsqlite.model.Categoria

class CategoriaDao(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insert(Categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("nome", Categoria.nome)
        }
        val id = db.insert(DBHelper.TBL_CATEGORIA, null, cv)
        db.close()
        return id
    }

    fun update(Categoria: Categoria): Int {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply { put("nome", Categoria.nome) }
        val rows = db.update(DBHelper.TBL_CATEGORIA, cv, "id = ?", arrayOf(Categoria.id.toString()))
        db.close()
        return rows
    }

    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DBHelper.TBL_CATEGORIA, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    fun getAll(): List<Categoria> {
        val list = mutableListOf<Categoria>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nome FROM ${DBHelper.TBL_CATEGORIA} ORDER BY nome", null)
        cursor.use {
            while (it.moveToNext()) {
                val c = Categoria(it.getLong(0), it.getString(1))
                list.add(c)
            }
        }
        db.close()
        return list
    }

    fun getById(id: Long): Categoria? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nome FROM ${DBHelper.TBL_CATEGORIA} WHERE id = ?", arrayOf(id.toString()))
        cursor.use {
            if (it.moveToFirst()) return Categoria(it.getLong(0), it.getString(1))
        }
        db.close()
        return null
    }
}
