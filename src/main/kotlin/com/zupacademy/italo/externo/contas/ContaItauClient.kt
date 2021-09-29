package com.zupacademy.italo.externo.contas

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client


@Client("\${itau.contas.url}")
interface ContaItauClient {

    @Get("/clientes/{codigoCliente}/contas")
    fun buscaContaPorTipo(@PathVariable("codigoCliente") codigoCliente: String, @QueryValue tipoConta: String): ContaItauResponse
}