package com.warba.middlware.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Common utilities methods
 * 
 * @author Salah Abu Msameh
 */
public class Utils {

	/**
	 * format xml to pretty output
	 * 
	 * @param xml
	 * @throws TransformerException 
	 */
	public static String prettyXMLFormat(String xml) throws TransformerException {
		
		//init factory
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", 2);
        
        //init transformer
        Transformer transformer = transformerFactory.newTransformer(); 
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
        
        StreamResult sResult = new StreamResult(new StringWriter());
        transformer.transform(new StreamSource(new StringReader(xml)), sResult);
        
        return sResult.getWriter().toString();
	}
}
