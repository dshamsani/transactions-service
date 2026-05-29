package dev.dshamsani.transactions.dto

import dev.dshamsani.transactions.model.Account
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class AccountDto(
    val id: Long,
    val owner: String,
    val balance: BigDecimal,
    val transactions: List<CompactTransactionDto>
)

data class CompactAccountDto(
    val id: Long,
    val owner: String,
    val balance: BigDecimal,
)


data class CreateAccountRequest(
    @field:NotNull
    val owner: String,

    @field:NotNull
    @field:PositiveOrZero
    val balance: BigDecimal
)

data class TransferAmountRequest(
    val fromId: Long,
    val toId: Long,
    val amount: BigDecimal
)


fun Account.toDto(): AccountDto = AccountDto(
    id = this.id,
    owner = this.owner,
    balance = this.balance,
    transactions = this.transactions.map { it.toCompactDto() }
)

fun Account.toCompactDto(): CompactAccountDto = CompactAccountDto(
    id = this.id,
    owner = this.owner,
    balance = this.balance,
)

fun CreateAccountRequest.toAccount(): Account = Account(
    owner = this.owner,
    balance = this.balance
)