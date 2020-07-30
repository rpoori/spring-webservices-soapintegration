package com.my.poc.soapintegration.specific;

import com.my.poc.transaction.SubmitTransactionResponse;
import com.my.poc.transaction.Transaction;

public class SpecificUtil {

    public static SpecificRequest getRequest(Transaction transaction) {
        // Formulate request based on input transaction
        return SpecificRequest.builder().build();
    }

    public static SubmitTransactionResponse mapResponse(SpecificResponse response) {
        // Map response from soap call and construct SubmitTransactionResponse based on it
        return SubmitTransactionResponse.builder()
                .status("SUCCESS")
                .build();
    }
}
