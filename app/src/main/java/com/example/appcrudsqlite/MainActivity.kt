package com.example.appcrudsqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.databinding.ActivityMainBinding
import com.example.appcrudsqlite.ui.categoria.CategoriaListActivity
import com.example.appcrudsqlite.ui.cliente.ClienteListActivity
import com.example.appcrudsqlite.ui.produto.ProdutoListActivity
import com.example.appcrudsqlite.ui.venda.VendaListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCategorias.setOnClickListener {
            startActivity(Intent(this, CategoriaListActivity::class.java))
        }

        binding.botaoClientes.setOnClickListener {
            startActivity(Intent(this, ClienteListActivity::class.java))
        }

        binding.botaoProdutos.setOnClickListener {
            startActivity(Intent(this, ProdutoListActivity::class.java))
        }

        binding.botaoVendas.setOnClickListener {
            startActivity(Intent(this, VendaListActivity::class.java))
        }
    }
}
