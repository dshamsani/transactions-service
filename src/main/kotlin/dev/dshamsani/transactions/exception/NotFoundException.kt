package dev.dshamsani.transactions.exception

abstract class NotFoundException(message: String) : RuntimeException(message)

class AccountNotFoundException(id: Long) : NotFoundException("Account with id: $id")
class TransactionNotFoundException(id: Long) : NotFoundException("Transaction with id: $id")