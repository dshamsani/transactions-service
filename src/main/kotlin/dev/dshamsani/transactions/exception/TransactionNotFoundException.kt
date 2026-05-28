package dev.dshamsani.transactions.exception

class TransactionNotFoundException(id: Long) : RuntimeException("Transaction with id $id not found")