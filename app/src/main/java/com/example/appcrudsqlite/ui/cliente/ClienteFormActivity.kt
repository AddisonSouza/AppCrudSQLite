package com.example.appcrudsqlite.ui.cliente

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.appcrudsqlite.R
import com.example.appcrudsqlite.data.ClienteDao
import com.example.appcrudsqlite.model.Cliente

class ClienteFormActivity : AppCompatActivity() {
    private lateinit var dao: ClienteDao
    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cliente)
        dao = ClienteDao(this)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtCpf: EditText = findViewById(R.id.edtCpf)
        val edtTelefone: EditText = findViewById(R.id.edtTelefone)
        val edtEmail: EditText = findViewById(R.id.edtEmail)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)
        val btnSave: Button = findViewById(R.id.btnSave)

        editingId = intent?.getLongExtra("id", -1L)?.takeIf { it != -1L }
        editingId?.let {
            val c = dao.getById(it)
            c?.let {
                edtNome.setText(it.nome)
                edtCpf.setText(it.cpf)
                edtTelefone.setText(it.telefone)
                edtEmail.setText(it.email)
                edtEndereco.setText(it.endereco)
            }
        }

        btnSave.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            if (nome.isEmpty()) { edtNome.error = "Informe o nome"; return@setOnClickListener }

            val client = Cliente(
                id = editingId,
                nome = nome,
                cpf = edtCpf.text.toString().trim(),
                telefone = edtTelefone.text.toString().trim(),
                email = edtEmail.text.toString().trim(),
                endereco = edtEndereco.text.toString().trim()
            )
            if (editingId == null) dao.insert(client) else dao.update(client)
            finish()
        }
    }
}
