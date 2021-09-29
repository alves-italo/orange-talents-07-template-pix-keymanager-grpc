package com.zupacademy.italo.chaves.cadastro

import com.zupacademy.italo.CadastraChaveReply
import com.zupacademy.italo.CadastraChaveRequest
import com.zupacademy.italo.ChavesServiceGrpc
import com.zupacademy.italo.chaves.*
import com.zupacademy.italo.contas.Conta
import com.zupacademy.italo.contas.TipoConta
import com.zupacademy.italo.externo.contas.ContaItauClient
import com.zupacademy.italo.externo.contas.ContaItauResponse
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.validator.Validator
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.lang.Exception
import javax.validation.ConstraintViolationException
import kotlin.IllegalStateException


@Singleton
class ChavesGrpcServer(
    @Inject val chaveRepository: ChaveRepository,
    @Inject val contasItauClient: ContaItauClient,
    @Inject val validator: Validator
) : ChavesServiceGrpc.ChavesServiceImplBase() {
    private val logger = LoggerFactory.getLogger(this::class.java)


    override fun cadastra(request: CadastraChaveRequest, responseObserver: StreamObserver<CadastraChaveReply>?) {
        val novaChave: NovaChave = request.toModel()

        try {
            if (chaveRepository.existsByChavePix(request.chave)) throw IllegalStateException("A chave já é usada por uma conta.")
            validator.validate(novaChave).let {
                if(it.isNotEmpty()) throw ConstraintViolationException(it)
            }
        } catch (exception: ConstraintViolationException) {
            responseObserver?.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(exception.message)
                    .asRuntimeException()
            )
            return
        } catch (exception: IllegalStateException) {
            responseObserver?.onError(
                Status.ALREADY_EXISTS
                    .withDescription(exception.message)
                    .asRuntimeException()
            )
            return
        }

        val contaItauResponse: ContaItauResponse = contasItauClient.buscaContaPorTipo(novaChave.codigoCliente, novaChave.tipoConta.toString())
        val conta: Conta = contaItauResponse.toModel()

        val chave: Chave = novaChave.toModel(conta)
        chaveRepository.save(chave)

        val reply = CadastraChaveReply.newBuilder()
            .setPixId(chave.id.toString())
            .build()
        responseObserver!!.onNext(reply)
        responseObserver.onCompleted()



    }

    fun CadastraChaveRequest.toModel(): NovaChave {
        return NovaChave(
            codigoCliente,
            TipoChave.valueOf(tipoChave.toString()),
            TipoConta.valueOf(tipoConta.toString()),
            chave
        )
    }
}