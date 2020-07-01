package com.my.poc.externaluserservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse {

    private List<UserInfo> userInfoList;

    @Value
    @Builder
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserInfo {
        String id;
        String name;
        String status;
        //Other fields
    }
}
