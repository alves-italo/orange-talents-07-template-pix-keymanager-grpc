package com.zupacademy.italo.contas

import javax.persistence.*
import javax.persistence.CascadeType.PERSIST

@Entity
class Conta (
    val tipoConta: TipoConta,
    val agencia: String,
    val numero: String,
    @ManyToOne(cascade = [PERSIST]) val titular: Cliente
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}