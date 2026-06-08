package nathoup2p.V1;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class MonClient {
    public static void main(String[] args){

         try {
        Socket s = new Socket("localhost", 9287);
        //System.out.println(REPONSE);

        OutputStream out = s.getOutputStream();
        PrintWriter out2 = new PrintWriter(out, true); //Afin d'envoyer mon texte au serveur

        InputStream text = s.getInputStream();
        InputStreamReader text2 = new InputStreamReader(text);
        BufferedReader text3 = new BufferedReader(text2);

        RecepteurServ rec = new RecepteurServ(text3);

        Thread t = new Thread(rec);
        t.start();

        Envoyeur env = new Envoyeur(out2);


       Scanner m = new Scanner(System.in);
       String MESSAGE = "m.nextLine();";

       while(!(MESSAGE.equalsIgnoreCase("$fin"))){
           MESSAGE = m.nextLine();


            if(MESSAGE.equalsIgnoreCase("$fin")){break;}
            env.envoyer(MESSAGE);

        }

        s.close();

        } catch (Exception e) {
            System.out.println("Server Error : " + e);
            }
    }
}
