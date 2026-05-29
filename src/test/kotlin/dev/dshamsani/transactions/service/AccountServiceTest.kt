package dev.dshamsani.transactions.service

import dev.dshamsani.transactions.dto.TransferAmountRequest
import dev.dshamsani.transactions.exception.AccountNotFoundException
import dev.dshamsani.transactions.model.Account
import dev.dshamsani.transactions.repository.AccountRepository
import dev.dshamsani.transactions.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Optional

class AccountServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val transactionRepository = mockk<TransactionRepository>()
    private val service = AccountService(accountRepository, transactionRepository)

    @Test
    fun `transfer subtracts from source account`() {
        val fromAccount = Account(id = 1, owner = "From", balance = BigDecimal("1000"))
        val toAccount = Account(id = 2, owner = "To", balance = BigDecimal("1000"))

        every { accountRepository.findById(1) } returns Optional.of(fromAccount)
        every { accountRepository.findById(2) } returns Optional.of(toAccount)
        every {accountRepository.save(fromAccount)} returns fromAccount
        every {accountRepository.save(toAccount)} returns toAccount

        service.transfer(
            TransferAmountRequest(
                fromId = fromAccount.id,
                toId = toAccount.id,
                amount = BigDecimal("100")
            )
        )

        Assertions.assertEquals(0, BigDecimal("900").compareTo(fromAccount.balance))
        Assertions.assertEquals(0, BigDecimal("1100").compareTo(toAccount.balance) )
        verify(exactly = 1) { accountRepository.save(fromAccount) }
        verify(exactly = 1) { accountRepository.save(toAccount) }
    }

    @Test
    fun `transfer throws when source account not found`() {
        every { accountRepository.findById(999)} returns Optional.empty()

        assertThrows<AccountNotFoundException> {
            service.transfer(TransferAmountRequest(
                999,2,BigDecimal("100")
            ))
        }
    }
}