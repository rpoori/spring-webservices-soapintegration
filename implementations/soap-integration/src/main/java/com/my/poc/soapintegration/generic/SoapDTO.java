package com.my.poc.soapintegration.generic;

import lombok.*;
import org.jibx.runtime.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoapDTO implements Serializable, IMarshallable, IUnmarshallable {

    private String[] names;
    private String[] values;
    private static final String JiBX_bindingList = "|com.my.poc.soapintegration.JiBX_bindingFactory|";

    @Override
    public String JiBX_getName() {
        return null;
    }

    @Override
    public void marshal(IMarshallingContext iMarshallingContext) throws JiBXException {

    }

    @Override
    public String JiBX_className() {
        return null;
    }

    @Override
    public void unmarshal(IUnmarshallingContext iUnmarshallingContext) throws JiBXException {

    }
}
