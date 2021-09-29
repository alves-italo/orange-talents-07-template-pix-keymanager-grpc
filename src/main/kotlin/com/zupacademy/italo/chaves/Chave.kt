package com.zupacademy.italo.chaves

import com.zupacademy.italo.contas.Conta
import javax.persistence.*
import javax.persistence.CascadeType.PERSIST

@Entity
class Chave(
    val codigoCliente: String?,
    val tipoChave: TipoChave?,
    @ManyToOne(cascade = [PERSIST]) val conta: Conta
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var chavePix: String? = null
}