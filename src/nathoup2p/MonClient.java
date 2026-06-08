package nathoup2p;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class MonClient {
    private String ip;
    private int port;
    private Socket client;
    private Envoyeur env;


    public MonClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void connecter(){

        try {
            this.client = new Socket(this.ip, this.port);

            OutputStream out = this.client.getOutputStream();
            PrintWriter out2 = new PrintWriter(out, true); //Afin d'envoyer mon texte au serveur

            InputStream text = this.client.getInputStream();
            InputStreamReader text2 = new InputStreamReader(text);
            BufferedReader text3 = new BufferedReader(text2);

            RecepteurServ rec = new RecepteurServ(text3);

            Thread t = new Thread(rec);
            t.start();

            env = new Envoyeur(out2);

            Scanner m = new Scanner(System.in);
            String MESSAGE = "m.nextLine();";

            while(!(MESSAGE.equalsIgnoreCase("$fin"))){
                MESSAGE = m.nextLine();


                if(MESSAGE.equalsIgnoreCase("$fin")){break;}
                this.Envoyer(MESSAGE);

            }

            this.stop();

            } catch (Exception e) {
            System.out.println("Client Error : " + e);
        }
    }

    public void stop(){
        try {
            this.client.close();
        } catch (Exception e) {
            System.out.println("Client Error Stop: " + e);
        }
    }

    public void Envoyer(String m){
        this.env.envoyer(m);
    }

    public static void main(String[] args){
        Scanner m = new Scanner(System.in);
        int port = 0;

        System.out.println("Lancer en tant que (S)erveur ou (C)lient ?");
        String choix = "caca";

        while(!(choix.equalsIgnoreCase("S") || choix.equalsIgnoreCase("C"))){
            choix = m.nextLine();
        }

        if(choix.equalsIgnoreCase("s")){
            System.out.println("Veuillez mettre le port pour le serveur");
            port = m.nextInt();


            MonServ s = new MonServ(port);
            s.start();
        }
        else{
            System.out.println("Veuillez entrez l'ip du serveur : ");
            String ip = m.nextLine();

            System.out.println("Puis son port : ");
            port = m.nextInt();

            MonClient c = new MonClient(ip, port);
            c.connecter();
        }
    }
}
