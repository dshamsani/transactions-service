package dev.dshamsani.transactions.repository

import dev.dshamsani.transactions.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long>