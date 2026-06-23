package nathoup2p.V2;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.*;
import java.util.*;

/*
================================================================================
⚠️ ATTENTION : Il faut impérativement exécuter ces commandes depuis la RACINE 
du projet (le dossier 'src'), là où se trouvent les fichiers .jar !
================================================================================

LINUX & MACOS (Utilisent le deux-points ":" comme séparateur)
================================================================================
Compilation :
javac -cp ".:Java-WebSocket-1.6.0.jar:slf4j-api-2.0.7.jar:slf4j-api-2.1.0-alpha1.jar" nathoup2p/V2/*.java

Exécution :
java -cp ".:Java-WebSocket-1.6.0.jar:slf4j-api-2.0.7.jar:slf4j-api-2.1.0-alpha1.jar" nathoup2p.V2.MainWebSocket

================================================================================
WINDOWS (Utilise le point-virgule ";" comme séparateur et les antislashes "\")
================================================================================
Compilation :
javac -cp ".;Java-WebSocket-1.6.0.jar;slf4j-api-2.0.7.jar;slf4j-api-2.1.0-alpha1.jar" nathoup2p\V2\*.java

Exécution :
java -cp ".;Java-WebSocket-1.6.0.jar;slf4j-api-2.0.7.jar;slf4j-api-2.1.0-alpha1.jar" nathoup2p.V2.MainWebSocket
================================================================================
*/

//IDÉE : JS --> EMAIL MAILTO


public class MonWebSocket extends WebSocketServer{
    private static final ArrayList<String[]> ARCHIVE = new ArrayList<String[]>();
    private static String[] BUffer;
    private static int connected;
    private static int systemMessage;

    public MonWebSocket(int port) {//ouvre un port de connexion au "serveur"
        super(new InetSocketAddress(port));
        MonWebSocket.BUffer = new String[9];
    }

    /*MÉTHODE REDEFINIE*/
    public void onStart(){//Dès qu'on ouvre la page
        System.out.println("Le Serveur tourne bien !");
        MonWebSocket.connected = 0;
        MonWebSocket.systemMessage = 0;
    }


    public void onOpen(WebSocket conn, ClientHandshake handshake){//Dès qu'on ouvre la page
        //System.out.println("CONN : "+conn);
        System.out.println("Une connexion a eu lieu");
        MonWebSocket.connected++;
        systemMessage++;
        int compteur = MonWebSocket.connected;

        for(WebSocket ws:this.getConnections()){
            this.onSend(ws, "+$CONNECTED : "+conn+"/"+MonWebSocket.systemMessage+"/");
            this.onSend(ws, "+$COUNT : "+compteur+"/"+MonWebSocket.systemMessage+"/");
        }
        //System.out.println("HANDSHAKE : "+handshake);

    }


    public void onClose(WebSocket conn, int code, String reason, boolean remote){//Dès qu'on ferme la page
        //System.out.println("CONN : "+conn);
        System.out.println("Une connexion a été fermé");
        MonWebSocket.connected--;
        systemMessage++;
        int compteur = MonWebSocket.connected;

        for(WebSocket ws:this.getConnections()){
            this.onSend(ws, "+$DISCONNECTED : "+conn+"/"+MonWebSocket.systemMessage+"/");
            this.onSend(ws, "+$COUNT : "+compteur+"/"+MonWebSocket.systemMessage+"/");
        }

        //System.out.println("CODE : "+reason);
        //System.out.println("REMOTE : "+remote);

        /*try{
            this.stop();
        }
        catch(InterruptedException I){
            System.err.println(I.getMessage());
        }*/
    }


   public void onMessage(WebSocket conn, String message_recu) {
       // [RÉCEPTION] Intercepte les messages envoyés par le JavaScript

       if (message_recu.length() >= 7) {
           String message = MonWebSocket.PNAB(message_recu);
           System.out.println("MESSAGE : " + message);

           if((message.toLowerCase()).equals("$fin")){

               try{this.stop();//Demande au serveur d'arrêter d'écouter sur le port
                   }
                    catch(InterruptedException I){
                       System.err.println(I.getMessage());
                    }
                    finally{
                        System.exit(0);//Tue instantanément le programme Java et ferme le terminal
                    }
            }

           if(message.startsWith("+#")){//+#
                this.systemCommandLine(message, conn);
            }

            else{
                int i = 0;
                for(WebSocket ws:this.getConnections()){
                    this.onSend(ws, message); //Envoie du message aux connexion
                    i++;
                }
            }
        }

       else {
            System.out.println("MESSAGE : " + message_recu);
            this.onSend(conn, message_recu);
            }
    }

    public void onSend(WebSocket conn, String message_recu){
        conn.send("\n+\n+\n ["+message_recu+"]\n+\n+\n");
    }

    public void onError(WebSocket conn, Exception ex) {
        // [OBLIGATOIRE] Méthode abstraite héritée à redéfinir
        System.err.println("Une erreur est survenue : " + ex.getMessage());
    }

    public static void StockBuffer(String message) {
        // [STOCKAGE] Parcourt le tableau temporaire pour placer le message dans la première case vide (null)
        //Condition de sortie un peu moche mais assumée
        boolean remplit = false;
        int i = 0;
        while(!remplit && i < BUffer.length){
            String place = BUffer[i];
            if (place == null) {
                BUffer[i] = message;
                remplit = true; //pour ne remplir qu'une case à la fois
            }
            i++;
        }
    }

    /*private boolean isSystemCommandLine(){
        return this.startsWith("+#");
    }*/

    private void systemCommandLine(String commandLine, WebSocket conn){
        //On vérifie si la commande envoyé existe

        //On vérfie déjà si c'est une commande system (+#)
        //if(!commandLine.isSystemCommandLine()){return false;} normalement oui lors de l'appel de cette méthode

        //On va vérifier ensuite que la commande n'est pas juste un texte où, comme de par hasard, il se trouve qu'une commande systeme se retrouve au début

        String command = String.valueOf(commandLine.substring(2, commandLine.length()));
        int longueur_command = command.length();


        switch(longueur_command){
            case 4:
                //USER
                if(command.equals("USER")){
                    //Va retourner toute la liste de toutes les fenêtres de navigateurs actuellement ouvertes et connectées au serveur
                    //Tout les clients/utilisateurs actifs en gros
                    int i = 0;
                    for(WebSocket ws:this.getConnections()){
                        System.out.println("CONNEXION "+(i+1)+" : "+ ws);//Connexion X
                        this.onSend(conn,(ws+""));//Celui qui a fait la commande obtient le résultat, non pas les autres!
                        i++;
                    }
                }
                break;

            default:
                break;

        }
    }

    public static String PNAB(String message) {
        // PNAB = Protocole Nathou Archive-Buffer
        if(!message.startsWith("+@")){
            return "NATHOU.ERROR : le message envoyé n'est pas bon";
        }
        // Étape 1 : Sauvegarde du message brut dans le Buffer de sécurité
        StockBuffer(message);

        // Étape 2 : Inversion de la chaîne pour analyser la fin du message (gestion de la taille variable de seq)
        String[] message_tab = message.split("");
        String messageR = "";
        for (int i = message_tab.length - 1; i >= 0; i--) {
            messageR += message_tab[i];
            }

        // Étape 3 : Repérage des barrières de séparation dans la chaîne inversée
        int index1 = messageR.indexOf("/"); // Première barrière rencontrée (Barrière finale)
        int index2 = messageR.indexOf("/", index1 + 1); // Deuxième barrière rencontrée (Fin du texte)

        // Étape 4 : Extraction et remise à l'endroit du numéro de séquence (seq)
        // Utilisation de subSequence (char -> String via String.valueOf)
        StringBuilder seq_sb = new StringBuilder();
        String seQ = String.valueOf(message.subSequence(index1, index2 + 1)); // Récupère le bloc /XXX/
        seq_sb.append(seQ);
        String seq = String.valueOf(seq_sb.reverse()); // Remet la séquence à l'endroit sans refaire de boucle

        // Étape 5 : Extraction du message textuel utile
        // 5.1 : On retire le tag fixe de départ "+@" (les 2 premiers caractères)
        message = String.valueOf(message.subSequence(2, message.length()));
        // 5.2 : On coupe la fin correspondant à la taille du bloc seq mesuré grâce à index2
        message = String.valueOf(message.subSequence(0, message.length() - index2 - 1));

        return message;
    }

    /* ===========================================================================
    LABORATOIRE DE RECHERCHE - PROTOCOLE PNAB
    ===========================================================================
    Cette section archive les phases de tests et d'algorithmes menées sur
    les consoles en ligne (W3Schools) pour aboutir au protocole final.

    ---------------------------------------------------------------------------
    PHASE 1 : Logique d'inversement de la chaîne (Vérification des index)
    ---------------------------------------------------------------------------
    * Test de la détection du header "+@"
    * Inversion via boucle décrémentale

    public class Main {
    public static void main(String[] args) {
    String mot = "+@hello/1/";
    String rmot = "";
    String[] motm = mot.split("");
    System.out.println(mot.startsWith("+@"));
    for(int i = motm.length - 1; i >= 0; i--){ rmot += motm[i]; }
    ...
    }
    }

    ---------------------------------------------------------------------------
    PHASE 2 : Chasse aux index et isolation des barrières "/"
    ---------------------------------------------------------------------------
    * Test de la méthode indexOf(symbole, offset)
    * Validation du comportement de subSequence() sur un pattern complexe

    public class Main {
    public static void main(String[] args) {
    String s = "/9/0/olleh@+";
    int index1 = s.indexOf("/");
    int index2 = s.indexOf("/", index1+1);
    ...
    System.out.println(s.subSequence(index1, index2+1));
    }
    }

    ---------------------------------------------------------------------------
    PHASE 3 : Synthèse et validation du désenrobage (Code Final validé)
    ---------------------------------------------------------------------------
    * Extraction réussie de la séquence et du message avec une taille de seq > 1000

    public class Main {
    public static void main(String[] args) {
    String message = "+@heckzikczkpckzoc/1900/";
    ...
    // Résultat validé : seq et message isolés proprement.
    }
    }
    ===========================================================================
    */



}
