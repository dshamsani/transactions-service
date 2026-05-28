package com.example.transactions.repository

import com.example.transactions.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TransactionRepository: JpaRepository<Transaction, Long>