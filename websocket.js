const socket = new WebSocket("ws://localhost:5000");
let buffer = []; //tableau qui va stocké les messages (se vide tout les 100 seq)
const archive = []; //Un tableau de tableau qui stocke les buffers
let seq = 1; 

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
     * pourquoi seq/10-0.1 ? Car je prend le nombre de 
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

    let message = $('input').val()
    if(e.code == "Enter" && message.trim() != ""){

        if(seq%10 == 0){
            buffer.push(wrapNathouB(message, seq++));
            archive.push(wrapNathouA(buffer));
            buffer = [];

        }
        else{
            buffer.push(wrapNathouB(message, seq++));
            //seqplus();
            $('input').val("");
            wssend(message);
            
        }
        
        console.log("buffer : ");
        console.log(buffer);

        
        console.log("archive : ");
        console.log(archive);

        console.log("--------------------");


        
    }
})


/*function seqplus(){
    seq++;
} en commentaire car seq++*/