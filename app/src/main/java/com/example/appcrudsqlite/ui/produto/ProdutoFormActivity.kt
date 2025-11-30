package com.example.appcrudsqlite.ui.produto

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.CategoriaDao
import com.example.appcrudsqlite.data.ProdutoDao
import com.example.appcrudsqlite.model.Produto

class ProdutoFormActivity : AppCompatActivity() {
    private lateinit var dao: ProdutoDao
    private lateinit var categoryDao: CategoriaDao
    private var editingId: Long? = null
    private var categories = listOf<com.example.appcrudsqlite.model.Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)
        dao = ProdutoDao(this)
        categoryDao = CategoriaDao(this)

        val edtDescricao: EditText = findViewById(R.id.edtDescricao)
        val edtQuantidade: EditText = findViewById(R.id.edtQuantidade)
        val edtPreco: EditText = findViewById(R.id.edtPreco)
        val spinnerCategoria: Spinner = findViewById(R.id.spinnerCategoria)
        val btnSave: Button = findViewById(R.id.btnSave)

        editingId = intent?.getLongExtra("id", -1L)?.takeIf { it != -1L }

        categories = categoryDao.getAll()
        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = catAdapter

        editingId?.let {
            val p = dao.getById(it)
            p?.let {
                edtDescricao.setText(it.descricao)
                edtQuantidade.setText(it.quantidade.toString())
                edtPreco.setText(it.preco.toString())
                val pos = categories.indexOfFirst { c -> c.id == it.idCategoria }
                if (pos >= 0) spinnerCategoria.setSelection(pos)
            }
        }

        btnSave.setOnClickListener {
            val descricao = edtDescricao.text.toString().trim()
            if (descricao.isEmpty()) { edtDescricao.error = "Informe descrição"; return@setOnClickListener }
            val quantidade = edtQuantidade.text.toString().toIntOrNull() ?: 0
            val preco = edtPreco.text.toString().toDoubleOrNull() ?: 0.0
            val selectedCat = spinnerCategoria.selectedItem as? com.example.appcrudsqlite.model.Categoria

            val product = Produto(
                id = editingId,
                descricao = descricao,
                quantidade = quantidade,
                preco = preco,
                idCategoria = selectedCat?.id
            )

            if (editingId == null) dao.insert(product) else dao.update(product)
            finish()
        }
    }
}
