package com.example.transactions.exception

class TransactionNotFoundException(id: Long) : RuntimeException("Transaction with id $id not found")