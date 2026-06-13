package nathoup2p.V2;

import java.io.*;
import java.util.*;

public class MainWebSocket {

    public static void main(String[] args){
        //Génère un numéro de 1000 à 9000 inclus afin d'évité à remettre un port à chaque fois
        Random r = new Random();
        int max = 9000, min = 1000, port = r.nextInt(max - min + 1) + min;
        System.out.println(port);

        MonWebSocket serveur = new MonWebSocket(port);
        serveur.start();
    }



}
