package dev.dshamsani.transactions.repository

import dev.dshamsani.transactions.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TransactionRepository: JpaRepository<Transaction, Long> {

    @EntityGraph(attributePaths = ["account"])
    override fun findAll(pageable: Pageable): Page<Transaction>
}

