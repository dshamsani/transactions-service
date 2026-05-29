package dev.dshamsani.transactions.controller

import dev.dshamsani.transactions.dto.AccountDto
import dev.dshamsani.transactions.dto.CompactTransactionDto
import dev.dshamsani.transactions.dto.CreateAccountRequest
import dev.dshamsani.transactions.dto.TransferAmountRequest
import dev.dshamsani.transactions.dto.shared.PagedResponse
import dev.dshamsani.transactions.service.AccountService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@Valid @RequestBody account: CreateAccountRequest): AccountDto {
        return accountService.create(account)
    }

    @GetMapping
    fun getAccounts(): List<AccountDto> {
        return accountService.getAll()
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: Long): AccountDto {
        return accountService.getById(id)
    }

    @GetMapping("/{id}/transactions")
    fun getAccountTransactions(@PathVariable id: Long, @PageableDefault(size = 20)  pageable: Pageable): PagedResponse<CompactTransactionDto> {
        return accountService.getTransactionsByAccountId(id, pageable)
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun transferBetweenAccount(@Valid @RequestBody transfer: TransferAmountRequest) {
        accountService.transfer(transfer)
    }

}