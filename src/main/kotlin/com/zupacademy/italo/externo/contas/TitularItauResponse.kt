package com.zupacademy.italo.externo.contas

import com.zupacademy.italo.contas.Cliente

data class TitularItauResponse(
    val id: String,
    val nome: String,
    val cpf: String,
) {
    fun toModel(): Cliente {
        return Cliente(id, nome, cpf)
    }
}
