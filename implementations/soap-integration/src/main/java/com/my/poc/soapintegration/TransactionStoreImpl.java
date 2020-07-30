package com.my.poc.soapintegration;

import com.my.poc.transaction.SubmitTransactionResponse;
import com.my.poc.transaction.Transaction;
import com.my.poc.transaction.TransactionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Slf4j
public class TransactionStoreImpl implements TransactionStore {

    private final GenericSoapConnector genericSoapConnector;
    private final XStreamMarshaller xStreamMarshaller;
    private final GenericUnmarshaller genericUnmarshaller;
    private final GenericConfig genericConfig;

    @Override
    public List<Transaction> getTransactions(String accountId) {

        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("SERVICE_NAME", "service-name");
        nameValueMap.put("ATTRIBUTE_NAME_1", "attribute-value-1");
        nameValueMap.put("ATTRIBUTE_NAME_2", "attribute-value-2");
        nameValueMap.put("ATTRIBUTE_NAME_3", "attribute-value-3");

        GenericInput genericInput = GenericInput.builder()
                .serviceNameHeader(nameValueMap.get("SERVICE_NAME"))
                .serviceNameDto(nameValueMap.get("SERVICE_NAME"))
                .keyValueSet(nameValueMap)
                .build();

        GenericOutput genericOutput = (GenericOutput) genericSoapConnector.makeWSCall(
                genericConfig.getSoapUrl(),
                genericInput,
                xStreamMarshaller,
                genericUnmarshaller
        );

        return processAndMapResponseToTransactionList(genericOutput);
    }

    private List<Transaction> processAndMapResponseToTransactionList(GenericOutput genericOutput) {

        String status = getValue(genericOutput, "STATUS", 0);

        if(!"OK".equals(status)) {
            log.error("Error processing soap response " + status);
            throw new GenericException(getValue(genericOutput, "ERROR_MSG", 0));
        }

        int numOfRows = Integer.parseInt(getValue(genericOutput, "NUMBER_OF_ROWS", 0));

        List<Transaction> transactionList = new ArrayList<>();

        for(int i = 0; i < numOfRows; i++) {

            // Use getValue by passing row i to fetch each attribute from the row
            // And build out the Transaction domain object
            // Add the built object to transactionList
        }

        return transactionList;
    }

    private String getValue(GenericOutput genericOutput, String attributeName, int i) {
        List<GenericNameValueNode> genericNameValueNodes = genericOutput.getNameValueNodes().stream()
                .filter(genericNameValueNode -> genericNameValueNode.getName().equals(attributeName))
                .collect(Collectors.toList());

        if (!genericNameValueNodes.isEmpty() && genericNameValueNodes.get(i) != null) {
            return genericNameValueNodes.get(i).getValue();
        }

        return null;
    }

    @Override
    public SubmitTransactionResponse submitTransaction(Transaction transaction) {
        return null;
    }
}
