package org.example.math;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.10
 * 2020-07-30T14:17:15.950-04:00
 * Generated source version: 3.1.10
 * 
 */
@WebService(targetNamespace = "http://example.org/math", name = "WsMath")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WsMath {

    @WebMethod(action = "http://example.org/math/#sum")
    @WebResult(name = "answer", targetNamespace = "http://example.org/math", partName = "response")
    public Answer sum(
        @WebParam(partName = "parameters", name = "add", targetNamespace = "http://example.org/math")
        Add parameters
    );
}
