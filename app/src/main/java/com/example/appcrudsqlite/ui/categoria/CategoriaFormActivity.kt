package com.example.appcrudsqlite.ui.categoria

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.CategoriaDao
import com.example.appcrudsqlite.model.Categoria

class CategoriaFormActivity : AppCompatActivity() {

    private lateinit var dao: CategoriaDao
    private var idEdicao: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_categoria)

        dao = CategoriaDao(this)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val btnSalvar: Button = findViewById(R.id.btnSalvar)

        idEdicao = intent?.getLongExtra("id", -1L)?.takeIf { it != -1L }

        idEdicao?.let { id ->
            val categoria = dao.getById(id)
            if (categoria != null) {
                edtNome.setText(categoria.nome)
            }
        }

        btnSalvar.setOnClickListener {

            val nome = edtNome.text.toString().trim()

            if (nome.isEmpty()) {
                edtNome.error = "Digite o nome"
                return@setOnClickListener
            }

            if (idEdicao == null) {
                dao.insert(Categoria(nome = nome))
            } else {
                dao.update(Categoria(id = idEdicao, nome = nome))
            }

            finish()
        }
    }
}
