package com.my.poc.springwebservicessoapintegration;

import com.my.poc.transaction.SubmitTransactionResponse;
import com.my.poc.transaction.Transaction;
import com.my.poc.transaction.TransactionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionStore transactionStore;

    @GetMapping("{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountId){
        return ResponseEntity.ok(transactionStore.getTransactions(accountId));
    }

    @PostMapping
    public ResponseEntity<SubmitTransactionResponse> submitTransaction(Transaction transaction){
        return ResponseEntity.ok(transactionStore.submitTransaction(transaction));
    }
}
