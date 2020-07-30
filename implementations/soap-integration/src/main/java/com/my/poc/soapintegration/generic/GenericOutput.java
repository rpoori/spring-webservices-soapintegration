package com.my.poc.soapintegration.generic;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@Builder
public class GenericOutput {
    Boolean successful;
    List<GenericNameValueNode> nameValueNodes;
    Map<String, String> statusSet;
}
