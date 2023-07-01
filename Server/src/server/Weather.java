/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Matija
 */
public class Weather {

    public String getTempByCityName(String city) {

        try {
            String dhmzUrl = "https://vrijeme.hr/hrvatska_n.xml";

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(false);
            builderFactory.setValidating(false);

            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            URLConnection urlConnection = new URL(dhmzUrl).openConnection();
            urlConnection.addRequestProperty("Accept", "application/xml");

            Document document = builder.parse(urlConnection.getInputStream());
            document.getDocumentElement().normalize();

            String expression = String.format("/Hrvatska/Grad[contains(GradIme,'%s')]/Podatci/Temp", city);
            XPath xPath = XPathFactory.newInstance().newXPath();

            Node cityInfo = (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);

            return ((Element) cityInfo).getFirstChild().getTextContent();
            
        } catch (IOException | ParserConfigurationException | XPathExpressionException | DOMException | SAXException e) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, e);
            return "Error! Something went wrong.";
        }
    }

}
