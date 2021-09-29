package com.zupacademy.italo.chaves

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ChaveRepository : JpaRepository<Chave, Long> {
    fun existsByChavePix(chavePix: String?) : Boolean
}