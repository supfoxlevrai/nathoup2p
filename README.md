# PROJET : NATHOU.P2P (Système de messagerie décentralisée) Note À Lire

Auteur      : Nathanaël Dos Santos
Formation (au moment du Projet)   : BUT1 Informatique
Module(s) lié    : R1.03 + R2.01 + R2.04 (Initiation au Développement / Réseaux)
Date (Début/Fin)       : 16 mars/ 5 Avril 2026

___

Bonjour !

Si vous voyez cette note, c'est que vous êtes sur le point de ou avez déjà utilisé mon programme et que vous voulez plus d'information sur l'origine de ce projet (ou juste, vous avez missclick ! [bruh](https://media1.tenor.com/m/G0YBmZyJU6IAAAAd/ratio-cope.gif =20x).

# I- "Préambule"

## Tout d'abord, pourquoi Nathou.p2p ?

### Nathou ?

"Nathou" est une private joke, tout simplement.

### .  ?

c'est littéralement un point 

### p2p ?

"p2p" est l'abréviation de : Peer-To-Peer

## Qu'est-ce que le Peer-To-Peer ?

Si je serais un saligot, je vous dirais d'aller chercher sur google mais comme je ne lui suis pas, voici plusieurs définition :

### fr.wikipedia.org/wiki/Pair-à-pair

Le **pair-à-pair**[[1]](https://fr.wikipedia.org/wiki/Pair-%C3%A0-pair#cite_note-GDT-1) ou système **pair à pair**[[2]](https://fr.wikipedia.org/wiki/Pair-%C3%A0-pair#cite_note-INFO909-2) (en [anglais](https://fr.wikipedia.org/wiki/Anglais "Anglais") _peer-to-peer_, souvent abrégé « **P2P** ») est un modèle d'échange en [réseau](https://fr.wikipedia.org/wiki/R%C3%A9seau "Réseau") où chaque entité est à la fois [client](https://fr.wikipedia.org/wiki/Client_(informatique) "Client (informatique)") et [serveur](https://fr.wikipedia.org/wiki/Serveur_informatique "Serveur informatique"), contrairement au modèle [client-serveur](https://fr.wikipedia.org/wiki/Client-serveur "Client-serveur"). Les termes « [pair](https://fr.wiktionary.org/wiki/pair "wikt:pair") », « [nœud](https://fr.wikipedia.org/wiki/N%C5%93ud_(r%C3%A9seau) "Nœud (réseau)") » et « utilisateur » sont généralement utilisés pour désigner les entités composant un tel système[[3]](https://fr.wikipedia.org/wiki/Pair-%C3%A0-pair#cite_note-3). Un système pair à pair peut être partiellement centralisé (une partie de l'échange passe par un [serveur central](https://fr.wikipedia.org/wiki/Serveur_central "Serveur central") intermédiaire) ou totalement décentralisé (les connexions se font entre participants sans infrastructure particulière). Il peut servir entre autres au [partage de fichier](https://fr.wikipedia.org/wiki/Partage_de_fichiers_en_pair-%C3%A0-pair "Partage de fichiers en pair-à-pair"), au [calcul distribué](https://fr.wikipedia.org/wiki/Calcul_distribu%C3%A9 "Calcul distribué") ou à la communication.

### [Ma définition(simpliste)](https://share.google/s16XLbt4NnJrtupng)

Les messageries non peer-to-peer (il y a un terme pour ça, voir au dessus), utilise un serveur central/intermédiaire que le destinateur "A" et récéptionneur "B" (et inversement) utilisent pour communiquer. De ce fait, si A n'est pas présent au moment où B envoie les messages, alors ces derniers seront stocker dans le Serveur et lorsque A se connecte, il pourra recevoir les messages de B

Dans les messageries peer-to-peer, A se "connecte" vers l'IP de B (et inversement) pour communiquer avec, bypassant le système de serveur central. Seul bémol est que si B n'est pas co, alors A ne pourra jamais se connecter vers l'IP de B (mais on s'en fou)

# II- Présentation du Projet : Nathou.p2p

## Objectif

Mon objectif est de créer une messagerie instantanée Peer-to-Peer (décentralisée) sécurisée par un encodage propriétaire (CoucHexa).

## ​Technologies/Outils :

 Java (Sockets, Multithreading) ![java](https://upload.wikimedia.org/wikipedia/fr/2/2e/Java_Logo.svg =20x) , 
 Environnement Linux (Kubuntu) ![kubuntu](https://upload.wikimedia.org/wikipedia/commons/1/1f/Kubuntu_logo.svg =20x),
 Telnet ![telnet](https://repository-images.githubusercontent.com/397512800/157c0e90-5fcd-4fc9-89cb-1766abd8c53e =50x).

# III- Architecture Technique

Le modèle est le suivant : P2P (chaque utilisateur est à la fois le Serveur et le Client)
Mais dans les fichiers, vous verez "MonServ" et "MonClient" mais ce sont juste des nom que j'ai posé, sinon c'est le même principe

J'ai utilisé les "Threads" pour permettre l'envoi + réception des messages en simultané sans blocage de l'interface (pour ne pas se retrouver dans une situation "talkie walkie" où B doit attendre que A parle pour qu'il parle

Le réseau fonctionne avec les Sockets TCP (class Socket/ServeurSocket de Java) sur un port dédié

# IV- Le Protocole de Sécurité : CoucHexa

## Principe

Système d'encodage par substitution et conversion hexadécimal personnalisée (voir l'explication plus détaillé dans le dossier dédié si je n'ai pas eu la flemme de le faire)

### Fonctionnement

1. On met les textes en majuscule (toUpperCase())
2. Traduction via l'alphabet statique(litérallement) propriétaire
3. Mécanisme de décodage inverse par séparation (*split*) et recherche de correspondance

Au moment où j'écris, nous sommes à la V1 de Nathoup2p et du CoucHexa et la V1 est fonctionnelle (difficilement cassable sans la table de correspondance)

# V- Instruction d'Utilisation

## Compilation :

```code
javac nathoup2p/*.java
java nathoup2p/MonServ.java (console A)
java nathoup2p/MonClient.java (console B)
```

# Note - Fin - À Retenir

Dans ce Projet, vous allez retrouvez :

- de l'Encapsulation
- Évidement de l'Algorithmique
- Ici, de la documentation
- Et bien plus encore !

---

### MENTIONS LÉGALES & AUTEUR

Ce projet a été entièrement développé par Nathanaël Dos Santos dans le cadre
du cursus BUT Informatique et de volonté d'améliorer sa culture informatique et son niveau informatique. 

L'algorithme de conversion "CoucHexa" et l'architecture réseau multi-threadée 
présentés ici sont des créations originales inspiré de système notable et reconnue.

© 2026 - Tous droits réservés.
