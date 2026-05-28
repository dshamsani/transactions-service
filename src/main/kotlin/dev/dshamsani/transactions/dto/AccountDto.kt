package dev.dshamsani.transactions.dto

import dev.dshamsani.transactions.model.Account
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class AccountDto(
    val id: Long,
    val owner: String,
    val balance: BigDecimal
)


data class CreateAccountRequest(
    @field:NotNull
    val owner: String,

    @field:NotNull
    @field:PositiveOrZero
    val balance: BigDecimal
)


fun Account.toDto(): AccountDto = AccountDto(
    id = this.id,
    owner = this.owner,
    balance = this.balance
)

fun CreateAccountRequest.toAccount(): Account = Account(
    owner = this.owner,
    balance = this.balance
)