package com.my.poc.externaluserservice;

import com.my.poc.user.User;
import com.my.poc.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ExternalUserService implements UserStore {

    private final RestTemplate restTemplate;
    private final ExternalUserServiceConfig config;

    @Override
    public List<User> getUsers(String segment) {

        URI uri = UriComponentsBuilder.fromHttpUrl(config.getRestApiUrl())
                .pathSegment("{segment}", "users")
                .buildAndExpand(segment)
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        RequestEntity<Void> requestEntity = new RequestEntity<>(
                httpHeaders,
                HttpMethod.GET,
                uri
        );

        return mapResponseToUserDomain(this.restTemplate.exchange(requestEntity, UserInfoResponse.class).getBody());
    }

    private List<User> mapResponseToUserDomain(UserInfoResponse userInfoResponse) {
        return userInfoResponse.getUserInfoList().stream()
                .map(userInfo -> User.builder()
                        .id(userInfo.getId())
                        .name(userInfo.getName())
                        .status(userInfo.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
