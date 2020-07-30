package com.my.poc.transaction;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubmitTransactionResponse {
    String status;
    String errorMsg;
}
