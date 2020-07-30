package com.my.poc.soapintegration;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jibx.JibxMarshaller;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class GenericUnmarshaller extends JibxMarshaller {

    @Override
    protected Object unmarshalDomNode(Node node) throws XmlMappingException {
        Node childNode = node.getLastChild().getFirstChild();

        SoapDTO soapDTO = (SoapDTO) super.unmarshalDomNode(childNode);
        List<GenericNameValueNode> genericNameValueNodes = new ArrayList<>();

        int y = 1;
        while(y < soapDTO.getNames().length) {
            genericNameValueNodes.add(GenericNameValueNode.builder()
                    .name(soapDTO.getNames()[y])
                    .value(soapDTO.getValues()[y])
                    .build());
            y++;
        }

        return GenericOutput.builder()
                .successful(true)
                .nameValueNodes(genericNameValueNodes)
                .build();
    }
}
