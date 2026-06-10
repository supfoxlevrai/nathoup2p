package nathoup2p.V2;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.*;
import java.util.*;

//javac -cp ".:Java-WebSocket-1.6.0.jar:slf4j-api-2.0.7.jar:slf4j-api-2.1.0-alpha1.jar" nathoup2p/V2/*.java
//java -cp ".:Java-WebSocket-1.6.0.jar:slf4j-api-2.0.7.jar:slf4j-api-2.1.0-alpha1.jar" nathoup2p/V2/MainWebSocket.java

//IDÉE : JS --> EMAIL MAILTO


public class MonWebSocket extends WebSocketServer{
    private static final ArrayList<String[]> ARCHIVE = new ArrayList<String[]>();
    private static String[] Buffer;

    public MonWebSocket(int port) {//ouvre un port de connexion au "serveur"
        super(new InetSocketAddress(port));
        MonWebSocket.Buffer = new String[9];
    }

    /*MÉTHODE REDEFINIE*/
    public void onStart(){//Dès qu'on ouvre la page
        System.out.println("Le Serveur tourne bien !");
    }


    public void onOpen(WebSocket conn, ClientHandshake handshake){//Dès qu'on ouvre la page
        System.out.println("Une connexion a eu lieu");
    }


    public void onClose(WebSocket conn, int code, String reason, boolean remote){//Dès qu'on ferme la page
        System.out.println("Une connexion a été fermé"+reason+" "+code+" "+remote);
    }


    public void onMessage(WebSocket conn, String message){//JS => Envoie message


        System.out.println("MESSAGE : "+message);
    }

    public void onError(WebSocket conn, Exception ex) {//Obligatoire, abstraite
        System.err.println("Une erreur est survenue : " + ex.getMessage());
    }

    /*public static boolean isValideMessage(String message){
        if(!message.startsWith("+@")){
            return false;
        }





    }*/



}
