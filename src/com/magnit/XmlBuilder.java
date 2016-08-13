package com.magnit;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.transform.stream.StreamSource;
/**
 * Created by Oleg Letunovskij on 13.08.2016.
 */
public final class XmlBuilder {
    public static void generateXml(List<Integer> ls) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);

            for (Integer i: ls) {
                Element entryElement = doc.createElement("entry");
                Element fieldElement = doc.createElement("field");
                fieldElement.appendChild(doc.createTextNode(i.toString()));
                entryElement.appendChild(fieldElement);
                rootElement.appendChild(entryElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            Utils.createDir("C://temp1");
            StreamResult result = new StreamResult(new File("C://temp1/1.xml"));

            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public static void transformXslt() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File("C://temp1/test.xslt")));
            transformer.transform(new StreamSource(new File("C://temp1/1.xml")), new StreamResult(new File("C://temp1/2.xml")));
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
