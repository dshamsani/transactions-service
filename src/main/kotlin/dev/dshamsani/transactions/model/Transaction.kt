package dev.dshamsani.transactions.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    var amount: BigDecimal,

    @Column(name = "description", nullable = false, length = 500)
    var description: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)