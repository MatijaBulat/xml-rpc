/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

/**
 *
 * @author Matija
 */
public class Server {

    private static final int SERVER_PORT = 8080;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            System.out.println("Starting XML-RPC server");

            WebServer webServer = new WebServer(SERVER_PORT);
            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("Weather", Weather.class);

            xmlRpcServer.setHandlerMapping(phm);

            XmlRpcServerConfigImpl serverConfig
                    = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
            serverConfig.setContentLengthOptional(false);
            serverConfig.setEnabledForExtensions(true);

            webServer.start();
            System.out.println("Server started.");

        } catch (Exception e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }

}
