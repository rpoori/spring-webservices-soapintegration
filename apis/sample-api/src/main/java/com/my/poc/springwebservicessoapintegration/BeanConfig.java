package com.my.poc.springwebservicessoapintegration;

import com.my.poc.externaluserservice.ExternalUserService;
import com.my.poc.externaluserservice.ExternalUserServiceConfig;
import com.my.poc.soapintegration.*;
import com.my.poc.soapintegration.generic.GenericConfig;
import com.my.poc.soapintegration.generic.GenericSoapConnector;
import com.my.poc.soapintegration.specific.SpecificConfig;
import com.my.poc.soapintegration.specific.SpecificSoapConnector;
import com.my.poc.transaction.TransactionStore;
import com.my.poc.user.GetActiveUsers;
import com.my.poc.user.UserStore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
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
    @ConfigurationProperties("specific")
    public SpecificConfig specificConfig() {
        return new SpecificConfig();
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

//    @Bean
//    public GenericUnmarshaller genericUnmarshaller() {
//        GenericUnmarshaller genericUnmarshaller = new GenericUnmarshaller();
//        genericUnmarshaller.setBindingName("binding");
//        genericUnmarshaller.setTargetPackage("com.my.poc.soapintegration"); // location of soapDTO package
////        genericUnmarshaller.setTargetClass(SoapDTO.class);
//        return genericUnmarshaller;
//    }

    @Bean
    public TransactionStore transactionStore(
        GenericSoapConnector genericSoapConnector,
        XStreamMarshaller xStreamMarshaller,
//        GenericUnmarshaller genericUnmarshaller,
        GenericConfig genericConfig,
        SpecificSoapConnector specificSoapConnector,
        Jaxb2Marshaller jaxb2Marshaller,
        SpecificConfig specificConfig
    ) {
        return new TransactionStoreImpl(
                genericSoapConnector,
                xStreamMarshaller,
//                genericUnmarshaller,
                genericConfig,
                specificSoapConnector,
                jaxb2Marshaller,
                specificConfig
        );
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
   //     marshaller.setContextPath("org.example.math");
        marshaller.setPackagesToScan("org.example.math");
        return marshaller;
    }

    @Bean
    public SpecificSoapConnector specificSoapConnector() {
        SpecificSoapConnector specificSoapConnector = new SpecificSoapConnector();
        return specificSoapConnector;
    }
}
