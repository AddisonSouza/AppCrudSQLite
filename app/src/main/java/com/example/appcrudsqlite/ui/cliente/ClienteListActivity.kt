package com.example.appcrudsqlite.ui.cliente

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.ClienteDao
import com.example.appcrudsqlite.model.Cliente

class ClienteListActivity : AppCompatActivity() {
    private lateinit var dao: ClienteDao
    private lateinit var listView: ListView
    private var list = mutableListOf<Cliente>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        dao = ClienteDao(this)
        listView = findViewById(R.id.listView)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener { startActivity(Intent(this, ClienteFormActivity::class.java)) }

        listView.setOnItemClickListener { _, _, position, _ ->
            val c = list[position]
            val it = Intent(this, ClienteFormActivity::class.java)
            it.putExtra("id", c.id)
            startActivity(it)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val c = list[position]
            AlertDialog.Builder(this)
                .setTitle("Excluir?")
                .setMessage("Excluir ${c.nome}?")
                .setPositiveButton("Sim") { _, _ -> dao.delete(c.id!!); loadList() }
                .setNegativeButton("Não", null)
                .show()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    private fun loadList() {
        list = dao.getAll().toMutableList()
        val strings = list.map { "${it.nome} - ${it.cpf ?: ""}" }
        listView.adapter = ArrayAdapter(this, com.example.appcrudsqlite.R.layout.item_simple_text, com.example.appcrudsqlite.R.id.text1, strings)
    }
}
