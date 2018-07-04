package com.surgical.decision3.functions;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;

/**
 * Created by Nattapon on 26/5/2560.
 */

public class XMLParser {

    public String marshall(Object object) {
        Serializer serializer = new Persister( new AnnotationStrategy() );
        StringWriter stringWriter = new StringWriter();
        try {
            serializer.write(object, stringWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
    public Object unMarshall(Class classObject, String xmlString) {
        Serializer serializer = new Persister( new AnnotationStrategy() );
        Object dt = null;
        try {
            dt = serializer.read(classObject, xmlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

}
