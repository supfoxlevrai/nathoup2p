package nathoup2p;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import nathoup2p.PFCC.CoucHexa;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Envoyeur {
    private PrintWriter out;
    private CoucHexa chx;


    public Envoyeur(PrintWriter out){
        this.out = out;
        this.chx = new CoucHexa();

    }

    public void envoyer(String message){
        String m = message;

        String HTC = chx.HexaToCoucHexa(m.toUpperCase());

        out.println(HTC);
    }





}
