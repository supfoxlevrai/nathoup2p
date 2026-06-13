let port = 5140;
const socket = new WebSocket("ws://localhost:"+port);
let buffer = []; //tableau qui va stocké les messages (se vide tout les 10 seq)
const archive = []; //Un tableau de tableau qui stocke les buffers
const fin = "$fin"; //mot d'arrêt de connexion
let seq = 1; 
//$("#screen-text").addClass('invisible');

function wssend(message){
    socket.send(message);
}

function wrapNathouB(message, seq){
    /**
     * + : signal d'ouverture ("Attention, un nouveau message commence ici !")
     * @ : étiquette qui annonce le contenu textuel
     * message : le texte de l'utilisateur
     * / : barrière séparateur (texte fini)
     * seq : le numéro d'ordre (la séquence) du message dans le tableau 
     * / barrière séparateur finale
     */
    let wrappedMessage = "+@"+message+"/"+seq+"/"
    return wrappedMessage;
}

function wrapNathouA(buffer){
    /**
     * + : signal d'ouverture ("Attention, un nouveau bloc commence ici !")
     * & : étiquette qui annonce le nouveau buffer
     * [ : encadré d'ouverture du nouveau buffer
     * buffer : le contenu buffer copié (groupe de 50)
     * ] : encadré de fermeture du nouveau buffer
     * / : barrière séparateur (texte fini)
     * seqArchive : le numéro de la page dans l'archive (la séquence) du message dans le tableau 
     * / barrière séparateur finale
     * 
     * pourquoi seq/10-0.1 ? Car lorque cette fonction est appelé, seq est dans la table de 10. De ce fait, on va divisé
     * par 10, pour obtenir le nombre de page. Or, si vous regardez bien, une ligne avant que la fonction est appelé
     * seq est incrémenté. De ce fait, on fais - 0.1 car 11/10 = 1.1, 21/10 = 2.1,... N1/10 = N.1... 
     */
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

  let message_serveur = event.data;
  span.textContent += "\n"+message_serveur;
});

/*function seqplus(){
    seq++;
} en commentaire car seq++*/