package com.my.poc.soapintegration.generic;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GenericNameValueNode {
    String name;
    String value;
}
