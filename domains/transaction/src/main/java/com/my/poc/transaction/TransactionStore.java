package com.my.poc.transaction;

import java.util.List;

public interface TransactionStore {

    List<Transaction> getTransactions(String accountId);
    SubmitTransactionResponse submitTransaction(Transaction transaction);
}
