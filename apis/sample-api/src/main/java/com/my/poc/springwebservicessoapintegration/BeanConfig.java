package com.my.poc.springwebservicessoapintegration;

import com.my.poc.externaluserservice.ExternalUserService;
import com.my.poc.externaluserservice.ExternalUserServiceConfig;
import com.my.poc.soapintegration.*;
import com.my.poc.transaction.TransactionStore;
import com.my.poc.user.GetActiveUsers;
import com.my.poc.user.UserStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;
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
    @ConfigurationProperties("generic")
    public GenericConfig genericConfig() {
        return new GenericConfig();
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

    @Bean
    public XStreamMarshaller xStreamMarshaller() {
        return new XStreamMarshaller();
    }

    @Bean
    public GenericSoapConnector genericSoapConnector() {
        return new GenericSoapConnector();
    }

    @Bean
    public GenericUnmarshaller genericUnmarshaller() {
        GenericUnmarshaller genericUnmarshaller = new GenericUnmarshaller();
     //   genericUnmarshaller.setBindingName("binding");
        genericUnmarshaller.setTargetClass(SoapDTO.class); // location of soapDTO package
        return genericUnmarshaller;
    }

    @Bean
    public TransactionStore transactionStore(
        GenericSoapConnector genericSoapConnector,
        XStreamMarshaller xStreamMarshaller,
        GenericUnmarshaller genericUnmarshaller,
        GenericConfig genericConfig
    ) {
        return new TransactionStoreImpl(
                genericSoapConnector,
                xStreamMarshaller,
                genericUnmarshaller,
                genericConfig
        );
    }
}
