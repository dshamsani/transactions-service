package dev.dshamsani.transactions.service

import dev.dshamsani.transactions.dto.AccountDto
import dev.dshamsani.transactions.dto.CompactTransactionDto
import dev.dshamsani.transactions.dto.CreateAccountRequest
import dev.dshamsani.transactions.dto.shared.PagedResponse
import dev.dshamsani.transactions.dto.shared.toPagedResponse
import dev.dshamsani.transactions.dto.toAccount
import dev.dshamsani.transactions.dto.toCompactDto
import dev.dshamsani.transactions.dto.toDto
import dev.dshamsani.transactions.exception.AccountNotFoundException
import dev.dshamsani.transactions.repository.AccountRepository
import dev.dshamsani.transactions.repository.TransactionRepository
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service


@Service
class AccountService(private val accountRepository: AccountRepository, private val transactionRepository: TransactionRepository) {


    fun getAll(): List<AccountDto> {
        return accountRepository.findAll().map { it.toDto() }
    }

    fun create(accountRequest: CreateAccountRequest): AccountDto {
        return accountRepository.save(accountRequest.toAccount()).toDto()
    }

    @Transactional(readOnly = true)
    fun getById(accountId: Long): AccountDto {
        return accountRepository.findById(accountId).orElseThrow { AccountNotFoundException(accountId) }.toDto()
    }


    fun getTransactionsByAccountId(accountId: Long, pageable: Pageable): PagedResponse<CompactTransactionDto> {
        return transactionRepository.findByAccountId(accountId, pageable).map { it.toCompactDto()}.toPagedResponse()
    }
}