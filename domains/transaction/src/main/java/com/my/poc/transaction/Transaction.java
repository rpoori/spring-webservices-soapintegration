package com.my.poc.transaction;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transaction {
    String transactionId;
    String transactionStatus;
    String transactionType;
    String transactionAmount;
    String fromAccountId;
    String toAccountId;
}
