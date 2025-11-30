package com.example.appcrudsqlite.ui.venda

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.data.VendaDao
import com.example.appcrudsqlite.data.ClienteDao
import com.example.appcrudsqlite.data.ProdutoDao
import kotlin.text.get

class VendaListActivity : AppCompatActivity() {
    private lateinit var dao: VendaDao
    private lateinit var clientDao: ClienteDao
    private lateinit var productDao: ProdutoDao
    private lateinit var listView: ListView
    private var list = mutableListOf<com.example.appcrudsqlite.model.Venda>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.appcrudsqlite.R.layout.activity_list)
        dao = VendaDao(this)
        clientDao = ClienteDao(this)
        productDao = ProdutoDao(this)
        listView = findViewById(com.example.appcrudsqlite.R.id.listView)
        val btnAdd: Button = findViewById(com.example.appcrudsqlite.R.id.btnAdd)
        btnAdd.setOnClickListener { startActivity(Intent(this, VendaFormActivity::class.java)) }

        listView.setOnItemClickListener { _, _, position, _ ->
            val s = list[position]
            val it = Intent(this, VendaFormActivity::class.java)
            it.putExtra("id", s.id)
            startActivity(it)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val s = list[position]
            AlertDialog.Builder(this)
                .setTitle("Excluir?")
                .setMessage("Excluir venda #${s.id}?")
                .setPositiveButton("Sim") { _, _ -> dao.delete(s.id!!); loadList() }
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
        val clients = clientDao.getAll().associateBy { it.id }
        val products = productDao.getAll().associateBy { it.id }
        val strings = list.map {
            val cli = clients[it.idCliente]?.nome ?: "Cliente ${it.idCliente}"
            val prod = products[it.idProduto]?.descricao ?: "Produto ${it.idProduto}"
            "$cli - $prod - Qtd:${it.quantidade} - R$${it.precoUnitario} - ${it.dataVenda}"
        }
        listView.adapter = ArrayAdapter(this, com.example.appcrudsqlite.R.layout.item_simple_text, com.example.appcrudsqlite.R.id.text1, strings)
    }
}
