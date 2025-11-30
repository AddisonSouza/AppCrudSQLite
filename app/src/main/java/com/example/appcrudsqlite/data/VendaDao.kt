package com.example.appcrudsqlite.data

import android.content.ContentValues
import android.content.Context
import com.example.appcrudsqlite.model.Venda

class VendaDao(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insert(Venda: Venda): Long {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("idCliente", Venda.idCliente)
            put("idProduto", Venda.idProduto)
            put("precoUnitario", Venda.precoUnitario)
            put("quantidade", Venda.quantidade)
            put("dataVenda", Venda.dataVenda)
        }
        val id = db.insert(DBHelper.TBL_VENDA, null, cv)
        db.close()
        return id
    }

    fun update(Venda: Venda): Int {
        val db = dbHelper.writableDatabase
        val cv = ContentValues().apply {
            put("idCliente", Venda.idCliente)
            put("idProduto", Venda.idProduto)
            put("precoUnitario", Venda.precoUnitario)
            put("quantidade", Venda.quantidade)
            put("dataVenda", Venda.dataVenda)
        }
        val rows = db.update(DBHelper.TBL_VENDA, cv, "id = ?", arrayOf(Venda.id.toString()))
        db.close()
        return rows
    }

    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        val rows = db.delete(DBHelper.TBL_VENDA, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    fun getAll(): List<Venda> {
        val list = mutableListOf<Venda>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, idCliente, idProduto, precoUnitario, quantidade, dataVenda FROM ${DBHelper.TBL_VENDA} ORDER BY dataVenda DESC", null)
        cursor.use {
            while (it.moveToNext()) {
                val s = Venda(
                    id = it.getLong(0),
                    idCliente = it.getLong(1),
                    idProduto = it.getLong(2),
                    precoUnitario = it.getDouble(3),
                    quantidade = it.getInt(4),
                    dataVenda = it.getString(5)
                )
                list.add(s)
            }
        }
        db.close()
        return list
    }

    fun getById(id: Long): Venda? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id, idCliente, idProduto, precoUnitario, quantidade, dataVenda FROM ${DBHelper.TBL_VENDA} WHERE id = ?", arrayOf(id.toString()))
        cursor.use {
            if (it.moveToFirst()) {
                return Venda(
                    id = it.getLong(0),
                    idCliente = it.getLong(1),
                    idProduto = it.getLong(2),
                    precoUnitario = it.getDouble(3),
                    quantidade = it.getInt(4),
                    dataVenda = it.getString(5)
                )
            }
        }
        db.close()
        return null
    }
}
