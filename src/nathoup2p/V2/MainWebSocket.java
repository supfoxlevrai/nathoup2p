package nathoup2p.v2;

public class MainWebSocket {

    public static void main(String[] args){
        MonWebSocket serveur = new MonWebSocket(5000);
        serveur.start();
        serveur.onStart();


    }

}
