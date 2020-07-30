package com.my.poc.soapintegration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Slf4j
public class GenericSoapConnector extends WebServiceGatewaySupport {

    private static final String NAMESPACE = "namespace";
    private static final String NAMESPACE_URL = "http://namespace-url";

    public Object makeWSCall(String url, Object input, Marshaller marshaller, Unmarshaller unmarshaller) {
        setMarshaller(marshaller);
        setUnmarshaller(unmarshaller);

        return getWebServiceTemplate().marshalSendAndReceive(
                url,
                input,
                getRequestCallback(input)
        );
    }

    private WebServiceMessageCallback getRequestCallback(Object input) {
        return message -> {
            try {
                GenericInput genericInput = (GenericInput) input;

                SOAPMessage saajMessage = ((SaajSoapMessage) message).getSaajMessage();

                SOAPPart soapPart = saajMessage.getSOAPPart();

                SOAPEnvelope envelope = soapPart.getEnvelope();
                envelope.getHeader().removeContents();

                envelope.addNamespaceDeclaration(NAMESPACE, NAMESPACE_URL);

                SOAPElement header = envelope.getHeader().addChildElement("Header", NAMESPACE);

                SOAPElement messageInfo = header.addChildElement("MessageInfo", NAMESPACE);
                messageInfo.addAttribute(new QName("CorrelationId"), UUID.randomUUID().toString());
                messageInfo.addAttribute(new QName("MessageEncoding"), "Xml");
                messageInfo.addAttribute(new QName("MessageId"), "");
                messageInfo.addAttribute(new QName("MessageVersion"), "");
                messageInfo.addAttribute(new QName("Priority"), "");
                messageInfo.addAttribute(new QName("PublishTimeStamp"), "");
                messageInfo.addAttribute(new QName("SessionContext"), "");

                SOAPElement messageType = messageInfo.addChildElement("MessageType", NAMESPACE);
                messageType.addTextNode(genericInput.getServiceNameHeader());

                SOAPElement originInfo = header.addChildElement("OriginInfo", NAMESPACE);
                originInfo.addAttribute(new QName("ClientApiVersion"), "");
                originInfo.addAttribute(new QName("HostOperatingSystem"), "");
                originInfo.addAttribute(new QName("ProducerId"), "");
                originInfo.addAttribute(new QName("Source"), "source-name");

                header.addChildElement("SecurityInfo", NAMESPACE);

                SOAPBody body = envelope.getBody();
                body.removeContents();
                SOAPElement request = body.addChildElement("Request", NAMESPACE);
                SOAPElement extensions = request.addChildElement("Extensions", NAMESPACE);
                SOAPElement dto = extensions.addChildElement("soapDTO", "http://dto-namespace-url");

                SOAPElement serviceName = dto.addChildElement("serviceName");
                serviceName.addTextNode(genericInput.getServiceNameDto());

                ArrayList keyList = new ArrayList(genericInput.getKeyValueSet().keySet());
                Collections.sort(keyList);

                for(Object key: keyList) {
                    dto.addChildElement("name").addTextNode(key.toString());
                }

                for(Object key: keyList) {
                    dto.addChildElement("value").addTextNode(genericInput.getKeyValueSet().get(key.toString()));
                }

            } catch (Exception e) {
                log.error("Error in formulating soap request");
            }
        };
    }
}
