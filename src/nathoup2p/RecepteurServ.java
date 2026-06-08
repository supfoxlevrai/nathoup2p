package nathoup2p;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import nathoup2p.PFCC.CoucHexa;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RecepteurServ implements Runnable {
    private BufferedReader MESSAGE;
    private CoucHexa chx;

    public RecepteurServ(BufferedReader txt){
        this.MESSAGE = txt;
        this.chx = new CoucHexa();
    }

    public void run(){
        try {
        String m = "Yaya Touré";

        while((true)){
            m = MESSAGE.readLine();
            if(m == null || m.equalsIgnoreCase("$fin")){System.out.println("Connexion fermée par l'autre utilisateur");break;}
            //Lit le tout

            String CTH = chx.CoucHexaToHexa(m);
            System.out.println("["+(LocalTime.now()).format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"] Message: " + CTH);


        }
        }
             catch (Exception e) {
                 System.out.println("Déconnexion réussite !");
            }
    }
}
