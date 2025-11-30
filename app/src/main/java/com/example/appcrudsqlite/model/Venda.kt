package com.example.appcrudsqlite.model

data class Venda(
    var id: Long? = null,
    var idCliente: Long,
    var idProduto: Long,
    var precoUnitario: Double,
    var quantidade: Int,
    var dataVenda: String
) {
    override fun toString(): String = "Venda #$id - Qtde: $quantidade"
}
