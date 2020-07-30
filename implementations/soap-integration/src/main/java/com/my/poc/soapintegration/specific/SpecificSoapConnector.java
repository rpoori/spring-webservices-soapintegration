package com.my.poc.soapintegration.specific;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import java.util.Map;

@Slf4j
public class SpecificSoapConnector extends WebServiceGatewaySupport {

    private static final String XSD_URL = "http://xsd-url";

    public Object makeWSCall(String url, Object input, Map<String, String> credentials) {

        return getWebServiceTemplate().marshalSendAndReceive(
                url,
                input,
                getRequestCallback(credentials)
        );
    }

    private WebServiceMessageCallback getRequestCallback(Map<String, String> credentials) {
        return message -> {
            try {

                SOAPMessage saajMessage = ((SaajSoapMessage) message).getSaajMessage();
                SOAPHeader soapHeader = saajMessage.getSOAPHeader();
                SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(new QName(XSD_URL, "Security", "wsse"));

                SOAPElement token = soapHeaderElement.addChildElement("UsernameToken", "wsse");
                SOAPElement usernameElement = token.addChildElement("Username", "wsse");
                SOAPElement passwordElement = token.addChildElement("Password", "wsse");

                usernameElement.setTextContent(credentials.get("username"));
                passwordElement.setTextContent(credentials.get("password"));

            } catch(Exception e) {
                log.error("Error while processing header on soap request");
            }
        };
    }
}
