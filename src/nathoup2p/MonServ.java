package nathoup2p;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class MonServ {
    private int port;
    private ServerSocket serveur;
    private Socket client;
    private Envoyeur env;

    public MonServ(int port){
        this.port = port;
    }

    public void start(){
         try {
            System.out.println("Starting server...");
            this.serveur = new ServerSocket(port);
            System.out.println("Server started. Listening on port "+port+"...");
            this.client = this.serveur.accept();
            System.out.println("Client connected : "+this.client.getInetAddress());


            OutputStream out = this.client.getOutputStream();
            PrintWriter out2 = new PrintWriter(out, true);

            //out2.println("Bienvenue sur nathoup2p !");
            // Read the message from the client
            InputStream text = this.client.getInputStream();//Récupère l'Input du client, mais ne sais pas lire du texte brute, seulement il bouffe des octets
            InputStreamReader text2 = new InputStreamReader(text); //Il traduit ce que la 1ère couche à retourner (il bouffe en char) mais ne lit qu'un caractère à la x
            BufferedReader text3 = new BufferedReader(text2); //Stocke les caractères de ^ dans une zone mémoire/tampon pour ne pas appeler le réseau à chaque fois

            RecepteurServ rec = new RecepteurServ(text3);
            env = new Envoyeur(out2);

            Thread t = new Thread(rec);
            t.start();

            Scanner m = new Scanner(System.in);
            String messageServ = "m.nextLine;";


            while(!(messageServ.equalsIgnoreCase("$fin"))){
                messageServ = m.nextLine();

                if(messageServ.equalsIgnoreCase("$fin")){break;}

            this.Envoyer(messageServ);
            }
            this.stop();

        } catch (Exception e) {
             System.out.println("Server Error: " + e);
        }

    }

    public void stop(){
        try {
            this.serveur.close();
        } catch (Exception e) {
            System.out.println("Server Error Stop: " + e);
        }
    }

    public void Envoyer(String m){
        this.env.envoyer(m);
    }




    public static void main(String[] args){
        MonServ s = new MonServ(9282);
        s.start();

        Scanner m = new Scanner(System.in);
        String messageServ = "m.nextLine;";


        while(!(messageServ.equalsIgnoreCase("$fin"))){
            messageServ = m.nextLine();

            if(messageServ.equalsIgnoreCase("$fin")){break;}

                s.Envoyer(messageServ);
        }
        s.stop();
    }
}
