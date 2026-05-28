package dev.dshamsani.transactions.service

import dev.dshamsani.transactions.dto.TransactionDto
import dev.dshamsani.transactions.dto.CreateTransactionRequest
import dev.dshamsani.transactions.dto.PatchTransactionRequest
import dev.dshamsani.transactions.dto.UpdateTransactionRequest
import dev.dshamsani.transactions.dto.shared.PagedResponse
import dev.dshamsani.transactions.dto.shared.toPagedResponse
import dev.dshamsani.transactions.dto.toDto
import dev.dshamsani.transactions.exception.AccountNotFoundException
import dev.dshamsani.transactions.exception.TransactionNotFoundException
import dev.dshamsani.transactions.model.Transaction
import dev.dshamsani.transactions.repository.AccountRepository
import dev.dshamsani.transactions.repository.TransactionRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TransactionService(
    private val repository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    fun create(transactionRequest: CreateTransactionRequest): TransactionDto {
        val account = accountRepository.findById(transactionRequest.accountId)
            .orElseThrow { AccountNotFoundException(transactionRequest.accountId) }

        val transaction = Transaction(
            amount = transactionRequest.amount,
            description = transactionRequest.description,
            account = account
        )

        return repository.save(transaction).toDto()
    }

    fun getById(id: Long): TransactionDto {
        return repository.findById(id).orElseThrow { TransactionNotFoundException(id) }
            .toDto()
    }

    fun getAll(pageable: Pageable): PagedResponse<TransactionDto> {
        return repository.findAll(pageable).map { it.toDto() }.toPagedResponse()
    }

    fun delete(id: Long) {
        if (!repository.existsById(id)) {
            throw TransactionNotFoundException(id)
        }

        repository.deleteById(id)
    }

    @Transactional
    fun update(id: Long, transactionRequest: UpdateTransactionRequest): TransactionDto {
        val transaction =
            repository.findById(id).orElseThrow { TransactionNotFoundException(id) }

        transaction.amount = transactionRequest.amount
        transaction.description = transactionRequest.description
        transaction.updatedAt = LocalDateTime.now()

        val updated = repository.save(transaction)

        return updated.toDto()
    }

    @Transactional
    fun patch(id: Long, transactionRequest: PatchTransactionRequest): TransactionDto {
        val transaction =
            repository.findById(id).orElseThrow { TransactionNotFoundException(id) }

        transactionRequest.amount?.let { transaction.amount = it }
        transactionRequest.description?.let { transaction.description = it }
        transaction.updatedAt = LocalDateTime.now()

        val updated = repository.save(transaction)

        return updated.toDto()
    }

}