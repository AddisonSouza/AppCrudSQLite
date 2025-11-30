package com.example.appcrudsqlite.model

data class Categoria(
    var id: Long? = null,
    var nome: String
) {
    override fun toString(): String = nome
}
