package com.zupacademy.italo.externo.contas

import com.zupacademy.italo.contas.Conta
import com.zupacademy.italo.contas.TipoConta

data class ContaItauResponse(
    val tipo: String,
    val agencia: String,
    val numero: String,
    val titular: TitularItauResponse
) {
    fun toModel(): Conta {
        return Conta(
            TipoConta.valueOf(tipo),
            agencia,
            numero,
            titular.toModel()
        )
    }
}
