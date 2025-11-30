package com.example.appcrudsqlite.model

data class Cliente(
    var id: Long? = null,
    var nome: String,
    var cpf: String?,
    var telefone: String?,
    var email: String?,
    var endereco: String?
) {
    override fun toString(): String = nome
}
