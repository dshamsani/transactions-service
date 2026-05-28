package dev.dshamsani.transactions.dto

import dev.dshamsani.transactions.model.Transaction
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime


data class TransactionDto(
    val id: Long,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val amount: BigDecimal
)

data class CreateTransactionRequest(
    @field:NotNull
    @field:Positive
    val amount: BigDecimal,

    @field:NotBlank
    @field:Size(max = 500)
    val description: String,

    @field:NotNull
    val accountId: Long
)

data class UpdateTransactionRequest(
    @field:NotNull
    @field:Positive
    val amount: BigDecimal,

    @field:NotBlank
    @field:Size(max = 500)
    val description: String
)

data class PatchTransactionRequest(
    @field:Positive
    val amount: BigDecimal? = null,

    @field:NotBlank
    @field:Size(max = 500)
    val description: String? = null
)


fun Transaction.toDto(): TransactionDto = TransactionDto(
    id = this.id,
    amount = this.amount,
    description = this.description,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

