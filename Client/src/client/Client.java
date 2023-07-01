/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author Matija
 */
public class Client {

    private static final String SERVER_URL = "http://localhost:8080";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            XmlRpcClient client = setupXmlRpcClient();

            String city = getInputCity();

            Object[] cityInfo = new Object[]{city};
            String temp = (String) client.execute("Weather.getTempByCityName", cityInfo);

            System.out.println("Temp: " + temp);
        } catch (XmlRpcException | MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static XmlRpcClient setupXmlRpcClient() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL(SERVER_URL));

        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);

        return client;
    }

    private static String getInputCity() {
        Locale loc = new Locale("hr", "HR");
        
        try (Scanner sc = new Scanner(System.in, "UTF-8")) {
            sc.useLocale(loc);
            System.out.print("Unesite naziv grad: ");
            return sc.nextLine();
        }
    }
}
