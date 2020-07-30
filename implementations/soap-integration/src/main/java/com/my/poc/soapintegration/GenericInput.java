package com.my.poc.soapintegration;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class GenericInput {
    String serviceNameHeader;
    String serviceNameDto;
    Map<String, String> keyValueSet;
}
