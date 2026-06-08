package nathoup2p.V1;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class MonServ {
    public static void main(String[] args){
        try {
            System.out.println("Starting server...");

            // Create a ServerSocket listening on port
            int port = 9287;
            ServerSocket SERVEUR = new ServerSocket(port);
            System.out.println("Server started. Listening on port "+port+"...");

            // Accept a connection from a client
            Socket CLIENT = SERVEUR.accept();
            System.out.println("Client connected!");


            OutputStream out = CLIENT.getOutputStream();
            PrintWriter out2 = new PrintWriter(out, true);

            //out2.println("Bienvenue sur nathoup2p !");
            // Read the message from the client
            InputStream text = CLIENT.getInputStream();//Récupère l'Input du client, mais ne sais pas lire du texte brute, seulement il bouffe des octets
            InputStreamReader text2 = new InputStreamReader(text); //Il traduit ce que la 1ère couche à retourner (il bouffe en char) mais ne lit qu'un caractère à la x
            BufferedReader text3 = new BufferedReader(text2); //Stocke les caractères de ^ dans une zone mémoire/tampon pour ne pas appeler le réseau à chaque fois

            RecepteurServ rec = new RecepteurServ(text3);

            Thread t = new Thread(rec);
            t.start();

            Envoyeur env = new Envoyeur(out2);


            Scanner m = new Scanner(System.in);
            String messageServ = "m.nextLine;";


            while(!(messageServ.equalsIgnoreCase("$fin"))){
                messageServ = m.nextLine();

                if(messageServ.equalsIgnoreCase("$fin")){break;}

                env.envoyer(messageServ);

            }

            // Close the socket
            SERVEUR.close();

            } catch (Exception e) {
                System.out.println("Server Error: " + e);
                }
    }

}
