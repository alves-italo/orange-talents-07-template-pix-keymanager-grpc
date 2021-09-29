package com.zupacademy.italo.chaves.cadastro

import com.zupacademy.italo.chaves.*
import com.zupacademy.italo.contas.Conta
import com.zupacademy.italo.contas.TipoConta
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@ChavePixValida
@Introspected
data class NovaChave(
    @field:NotBlank val codigoCliente: String,
    @field:NotNull val tipoChave: TipoChave,
    @field:NotNull val tipoConta: TipoConta,
    val chavePix: String?
) {
    fun toModel(conta: Conta): Chave {
        val chave =  Chave(codigoCliente, tipoChave, conta)

        chave.chavePix = when(tipoChave) {
            TipoChave.ALEATORIA -> UUID.randomUUID().toString()
            else -> chavePix
        }

        return chave
    }
}
