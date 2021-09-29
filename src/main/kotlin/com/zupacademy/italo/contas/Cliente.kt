package com.zupacademy.italo.contas

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Cliente (
    @Id val id: String,
    val nome: String,
    val cpf: String
)
