package com.zupacademy.italo.chaves

import com.zupacademy.italo.chaves.cadastro.NovaChave
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ChavePixValidaValidator::class])
annotation class ChavePixValida (
    val message: String = "Chave pix inválida.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

class ChavePixValidaValidator() : ConstraintValidator<ChavePixValida, NovaChave> {
    override fun isValid(
        value: NovaChave?,
        annotationMetadata: AnnotationValue<ChavePixValida>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value?.tipoChave == null) return false

        return value.tipoChave.valida(value.chavePix)
    }
}