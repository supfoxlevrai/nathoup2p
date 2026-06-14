let port = 5680;
const socket = new WebSocket("ws://localhost:"+port);
let buffer = []; //tableau qui va stocké les messages (se vide tout les 10 seq)
const archive = []; //Un tableau de tableau qui stocke les buffers
const fin = "$fin"; //mot d'arrêt de connexion
let seq = 1; 
let stock_message = 0;

//$("#screen-text").addClass('invisible');

/**
 * Envoie un message brute au serveur WebSocket (JAVA)
 * @param {message} message : le message textuel à envoyer
 */
function wssend(message){
    socket.send(message);
}

/**
 * Enrobe un message textuel individuel pour le Buffer selon le protocole PNAB.
 * * Structure du signal (`+@message/seq/`) :
 * - `+` : Signal d'ouverture ("Attention, un nouveau message commence ici !")
 * - `@` : Étiquette qui annonce le contenu textuel
 * - `message` : Le texte de l'utilisateur
 * - `/` : Barrière séparateur (texte fini)
 * - `seq` : Le numéro d'ordre (la séquence) du message dans le tableau
 * - `/` : Barrière séparateur finale
 * * 
 * @param {string} message - Le texte de l'utilisateur à enrober.
 * @param {number} seq - Le numéro de séquence actuel du message.
 * @returns {string} Le message enrobé prêt pour l'envoi.
 */
function wrapNathouB(message, seq){
    let wrappedMessage = "+@"+message+"/"+seq+"/"
    return wrappedMessage;
}

/**
 * Enrobe un bloc complet de Buffer pour l'archivage selon le protocole PNAB.
 * * Structure du signal (`+&[buffer]/seqArchive/`) :
 * - `+` : Signal d'ouverture ("Attention, un nouveau bloc commence ici !")
 * - `&` : Étiquette qui annonce le nouveau buffer
 * - `[` : Encadré d'ouverture du nouveau buffer
 * - `buffer` : Le contenu buffer copié (groupe de 50)
 * - `]` : Encadré de fermeture du nouveau buffer
 * - `/` : Barrière séparateur (texte fini)
 * - `seqArchive` : Le numéro de la page dans l'archive
 * - `/` : Barrière séparateur finale
 * * Pourquoi `seq/10 - 0.1` ? 
 * Lorsque cette fonction est appelée, `seq` vient d'être incrémenté et est passé au palier supérieur (11, 21, 31...).
 * On divise par 10 pour obtenir le numéro de page. Le `- 0.1` permet de retomber pile sur le bon entier de la page déchue 
 * (ex: 11 / 10 = 1.1 -> 1.1 - 0.1 = Page 1 | 21 / 10 = 2.1 -> 2.1 - 0.1 = Page 2).
 * * 
 * @param {Array} buffer - Le tableau de messages à archiver.
 * @returns {string} Le bloc d'archive enrobé prêt pour l'envoi.
 */
function wrapNathouA(buffer){
    let seqArchive = seq/10-0.1;
    let wrappedMessage = "+&["+buffer+"]/"+seqArchive+"/"
    return wrappedMessage;
}

buttons.forEach(element => {
    element.addEventListener('click',(e)=>{

        let message = e.target.textContent;
        wssend(message);
    });
});

$("input").on("keypress", (e)=>{
    /*console.log(e);
    console.log(e.nodeName);
    console.log(e.code);
    console.log($('input').val())*/

    let message = $('input').val();
    
    //$("#screen-text").addClass('invisible');
    if(e.code == "Enter" && message.trim() != ""){
        //$("#screen-text").removeClass('invisible');
        
        if(seq%10 == 0){
            buffer.push(wrapNathouB(message, seq++));
            archive.push(wrapNathouA(buffer));
            buffer = [];
            
        }
        else{
            buffer.push(wrapNathouB(message, seq++));
            //seqplus();
            
        }
        $('input').val("");
        //wssend(message);
        wssend(wrapNathouB(message, seq-1));

        console.log("buffer : ");
        console.log(buffer);

        
        console.log("archive : ");
        console.log(archive);

        console.log("--------------------"); 
        
        //met dans un écran "d'archive"
        document.getElementById("screen-archive").textContent += wrapNathouB(message, seq-1);
    }
})

// Écouter les messages
socket.addEventListener("message", (event) => {
  console.log("Voici un message du serveur", event.data);
  //span.textContent = "";

  let message_serveur_data = event.data;//le message du serveur

  //On enlève les tout pour ne garder que la partie entre crochet
  let message_serveur = (message_serveur_data.substring(6, message_serveur_data.length)).substring(0, message_serveur_data.length-10);
    
  if(message_serveur.startsWith("[+$COUNT")){

    //On enlève le "seq" qui se retrouve avec le compteur (/X/)
    let nombre = message_serveur.substring(0, 12);
    //On enlève tout ce qui n'est pas un chiffre/nombre
    let connectee = nombre.replace(/[^0-9]/g, "");
    compteur(connectee);//Affichage

  }

  if(stock_message > 10){//Mis pour l'instant pour l'écran
    span.textContent = "";
    stock_message = 0;
    }
    span.textContent += "\n"+message_serveur;
    stock_message += 1;
        console.log("f")

});

function compteur(n){
    connected.textContent = n;
    console.log("e")

}
/*function seqplus(){
    seq++;
} en commentaire car seq++*/