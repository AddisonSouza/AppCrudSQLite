package com.example.appcrudsqlite.model

data class Produto(
    var id: Long? = null,
    var descricao: String,
    var quantidade: Int,
    var preco: Double,
    var idCategoria: Long? = null
) {
    override fun toString(): String = descricao
}
