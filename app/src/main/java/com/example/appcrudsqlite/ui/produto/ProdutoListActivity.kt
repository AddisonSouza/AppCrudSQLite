package com.example.appcrudsqlite.ui.produto

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.ProdutoDao

class ProdutoListActivity : AppCompatActivity() {
    private lateinit var dao: ProdutoDao
    private lateinit var listView: ListView
    private var list = mutableListOf<com.example.appcrudsqlite.model.Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        dao = ProdutoDao(this)
        listView = findViewById(R.id.listView)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener { startActivity(Intent(this, ProdutoFormActivity::class.java)) }

        listView.setOnItemClickListener { _, _, position, _ ->
            val p = list[position]
            val it = Intent(this, ProdutoFormActivity::class.java)
            it.putExtra("id", p.id)
            startActivity(it)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val p = list[position]
            AlertDialog.Builder(this)
                .setTitle("Excluir?")
                .setMessage("Excluir ${p.descricao}?")
                .setPositiveButton("Sim") { _, _ -> dao.delete(p.id!!); loadList() }
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
        val strings = list.map { "${it.descricao} - Qtd: ${it.quantidade} - R$ ${it.preco}" }
        listView.adapter = ArrayAdapter(this, com.example.appcrudsqlite.R.layout.item_simple_text, com.example.appcrudsqlite.R.id.text1, strings)
    }
}
