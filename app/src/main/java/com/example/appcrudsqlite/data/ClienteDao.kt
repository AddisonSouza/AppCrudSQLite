package com.example.appcrudsqlite.data

import android.content.ContentValues
import android.content.Context
import com.example.appcrudsqlite.model.Cliente

class ClienteDao(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insert(Cliente: Cliente): Long {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("nome", Cliente.nome)
            put("cpf", Cliente.cpf)
            put("telefone", Cliente.telefone)
            put("email", Cliente.email)
            put("endereco", Cliente.endereco)
        }
        val id = db.insert(DBHelper.TBL_CLIENTE, null, cv)
        db.close()
        return id
    }

    fun update(Cliente: Cliente): Int {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("nome", Cliente.nome)
            put("cpf", Cliente.cpf)
            put("telefone", Cliente.telefone)
            put("email", Cliente.email)
            put("endereco", Cliente.endereco)
        }
        val rows = db.update(DBHelper.TBL_CLIENTE, cv, "id = ?", arrayOf(Cliente.id.toString()))
        db.close()
        return rows
    }

    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DBHelper.TBL_CLIENTE, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    fun getAll(): List<Cliente> {
        val list = mutableListOf<Cliente>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nome, cpf, telefone, email, endereco FROM ${DBHelper.TBL_CLIENTE} ORDER BY nome", null)
        cursor.use {
            while (it.moveToNext()) {
                val c = Cliente(
                    id = it.getLong(0),
                    nome = it.getString(1),
                    cpf = it.getString(2),
                    telefone = it.getString(3),
                    email = it.getString(4),
                    endereco = it.getString(5)
                )
                list.add(c)
            }
        }
        db.close()
        return list
    }

    fun getById(id: Long): Cliente? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, nome, cpf, telefone, email, endereco FROM ${DBHelper.TBL_CLIENTE} WHERE id = ?", arrayOf(id.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                return Cliente(
                    id = it.getLong(0),
                    nome = it.getString(1),
                    cpf = it.getString(2),
                    telefone = it.getString(3),
                    email = it.getString(4),
                    endereco = it.getString(5)
                )
            }
        }
        db.close()
        return null
    }
}
