package com.my.poc.springbootdomaindrivendesign;

import com.my.poc.externaluserservice.ExternalUserService;
import com.my.poc.externaluserservice.ExternalUserServiceConfig;
import com.my.poc.user.GetActiveUsers;
import com.my.poc.user.UserStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConfigurationProperties("external-user-service")
    public ExternalUserServiceConfig externalUserServiceConfig() {
        return new ExternalUserServiceConfig();
    }

    @Bean
    public UserStore userStore(
            RestTemplate restTemplate,
            ExternalUserServiceConfig externalUserServiceConfig
    ) {
        return new ExternalUserService(restTemplate, externalUserServiceConfig);
    }

    @Bean
    public GetActiveUsers getActiveUsers(UserStore userStore) {
        return new GetActiveUsers(userStore);
    }
}
