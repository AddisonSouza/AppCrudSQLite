package com.example.appcrudsqlite.ui.venda

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.ClienteDao
import com.example.appcrudsqlite.data.ProdutoDao
import com.example.appcrudsqlite.data.VendaDao
import com.example.appcrudsqlite.model.Venda
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class VendaFormActivity : AppCompatActivity() {
    private lateinit var dao: VendaDao
    private lateinit var clientDao: ClienteDao
    private lateinit var productDao: ProdutoDao
    private var editingId: Long? = null
    private lateinit var clients: List<com.example.appcrudsqlite.model.Cliente>
    private lateinit var products: List<com.example.appcrudsqlite.model.Produto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_venda)
        dao = VendaDao(this)
        clientDao = ClienteDao(this)
        productDao = ProdutoDao(this)

        val spinnerCliente: Spinner = findViewById(R.id.spinnerCliente)
        val spinnerProduto: Spinner = findViewById(R.id.spinnerProduto)
        val edtPrecoUnitario: EditText = findViewById(R.id.edtPrecoUnitario)
        val edtQuantidade: EditText = findViewById(R.id.edtQuantidade)
        val edtDataVenda: EditText = findViewById(R.id.edtDataVenda)
        val btnSave: Button = findViewById(R.id.btnSave)

        editingId = intent?.getLongExtra("id", -1L)?.takeIf { it != -1L }

        clients = clientDao.getAll()
        products = productDao.getAll()

        spinnerCliente.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, clients).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinnerProduto.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, products).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        if (editingId != null) {
            val s = dao.getById(editingId!!)
            s?.let {
                edtPrecoUnitario.setText(it.precoUnitario.toString())
                edtQuantidade.setText(it.quantidade.toString())
                edtDataVenda.setText(it.dataVenda)
                val cPos = clients.indexOfFirst { c -> c.id == it.idCliente }
                if (cPos >= 0) spinnerCliente.setSelection(cPos)
                val pPos = products.indexOfFirst { p -> p.id == it.idProduto }
                if (pPos >= 0) spinnerProduto.setSelection(pPos)
            }
        } else {
            val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            edtDataVenda.setText(now)
        }

        btnSave.setOnClickListener {
            val selectedClient = spinnerCliente.selectedItem as? com.example.appcrudsqlite.model.Cliente
            val selectedProduct = spinnerProduto.selectedItem as? com.example.appcrudsqlite.model.Produto
            if (selectedClient == null || selectedProduct == null) {
                Toast.makeText(this, "Selecione cliente e produto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val preco = edtPrecoUnitario.text.toString().toDoubleOrNull() ?: selectedProduct.preco
            val quantidade = edtQuantidade.text.toString().toIntOrNull() ?: 1
            val data = edtDataVenda.text.toString().trim()
            val sale = Venda(
                id = editingId,
                idCliente = selectedClient.id!!,
                idProduto = selectedProduct.id!!,
                precoUnitario = preco,
                quantidade = quantidade,
                dataVenda = data
            )
            if (editingId == null) dao.insert(sale) else dao.update(sale)
            finish()
        }
    }
}
