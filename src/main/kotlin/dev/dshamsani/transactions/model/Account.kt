package dev.dshamsani.transactions.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal


@Entity
@Table(name = "accounts")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "owner", nullable = false)
    val owner: String,

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    var balance: BigDecimal,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    val transactions: List<Transaction> = mutableListOf()
)