package com.my.poc.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    String id;
    String name;
    String status;
}
