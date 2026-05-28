package com.example.transactions.service

import com.example.transactions.dto.TransactionDto
import com.example.transactions.dto.CreateTransactionRequest
import com.example.transactions.dto.PatchTransactionRequest
import com.example.transactions.dto.UpdateTransactionRequest
import com.example.transactions.dto.shared.PagedResponse
import com.example.transactions.dto.shared.toPagedResponse
import com.example.transactions.dto.toDto
import com.example.transactions.dto.toEntity
import com.example.transactions.exception.TransactionNotFoundException
import com.example.transactions.repository.TransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TransactionService(private val repository: TransactionRepository) {
    fun create(transactionRequest: CreateTransactionRequest): TransactionDto {

        return repository.save(transactionRequest.toEntity()).toDto()
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