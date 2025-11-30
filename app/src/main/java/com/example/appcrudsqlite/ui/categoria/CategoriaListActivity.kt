package com.example.appcrudsqlite.ui.categoria

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.CategoriaDao
import com.example.appcrudsqlite.model.Categoria

class CategoriaListActivity : AppCompatActivity() {
    private lateinit var dao: CategoriaDao
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var list = mutableListOf<Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        dao = CategoriaDao(this)
        listView = findViewById(R.id.listView)
        val btnAdd: Button = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            startActivity(Intent(this, CategoriaFormActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val cat = list[position]
            val it = Intent(this, CategoriaFormActivity::class.java)
            it.putExtra("id", cat.id)
            startActivity(it)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val cat = list[position]
            AlertDialog.Builder(this)
                .setTitle("Excluir?")
                .setMessage("Excluir ${cat.nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    dao.delete(cat.id!!)
                    loadList()
                }
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
        val strings = list.map { it.nome }
        adapter = ArrayAdapter(this, R.layout.item_simple_text, R.id.text1, strings)
        listView.adapter = adapter
    }
}
