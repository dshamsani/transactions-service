package dev.dshamsani.transactions.controller

import dev.dshamsani.transactions.dto.CreateTransactionRequest
import dev.dshamsani.transactions.dto.PatchTransactionRequest
import dev.dshamsani.transactions.dto.TransactionDto
import dev.dshamsani.transactions.dto.UpdateTransactionRequest
import dev.dshamsani.transactions.dto.shared.PagedResponse
import dev.dshamsani.transactions.service.TransactionService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/transactions")
class TransactionController (private val transactionService: TransactionService) {

    @GetMapping
    fun getAllTransactions(@PageableDefault(size = 20, sort = ["createdAt"])  pageable: Pageable): PagedResponse<TransactionDto> {
        return transactionService.getAll(pageable)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@Valid @RequestBody transaction: CreateTransactionRequest): TransactionDto {
        return transactionService.create(transaction)
    }

    @GetMapping("/{id}")
    fun getTransaction(@PathVariable id: Long): TransactionDto {
        return transactionService.getById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTransaction(@PathVariable id: Long) {
         transactionService.delete(id)
    }

    @PutMapping("/{id}")
    fun updateTransaction(@PathVariable id: Long, @Valid @RequestBody transaction: UpdateTransactionRequest): TransactionDto {
        return transactionService.update(id, transaction)
    }

    @PatchMapping("/{id}")
    fun patchTransaction(@PathVariable id: Long, @Valid @RequestBody transaction: PatchTransactionRequest): TransactionDto {
        return transactionService.patch(id, transaction)
    }

}