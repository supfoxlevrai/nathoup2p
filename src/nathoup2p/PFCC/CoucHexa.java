package nathoup2p.PFCC;
//CLONE D'UN FICHIER DU MÊME NOM DANS UN DOSSIERS DIFFÉRENT !!!!

import java.util.*;
import java.math.*;

public class CoucHexa {                     //A : 1010 ; B : 1011 ; C : 1100 ; D : 1101 ; E : 1110 ; F : 1111
    private static final String[] Niveau0 = {"A", "B", "C", "D", "E", "F"};     //000
    private static final String[] Niveau1 = {"G", "H", "I", "J", "K", "L"}; //-6 //001
    private static final String[] Niveau2 = {"M", "N", "O", "P", "Q", "R"}; //-12 //010
    private static final String[] Niveau3 = {"S", "T", "U", "V", "W", "X"}; //-18 //011
    private static final String[] Niveau4 = {"Y", "Z", "'", ".", " ", "?"}; //-24 //100
    //DORMIR = 0001101 0101100


    //Texte --> Binaire
    public String CocHexa(String texte){
        //texte = MANGER

        String CODE_HexatoCoucHexa = HexaToCoucHexa(texte);
        //CODE_HexatoCoucHexa = 2A042B1A0E2F
        //CODE_HexatoCoucHexa --> 2     A     0     A      2    B      1    A      0    E      2    F

        String BINAIRE_CoucHexatoBinaire = CoucHexaToBinaire(CODE_HexatoCoucHexa);
        //[010][1010] [000][1010] [010][1011] [001][1010] [000][1110] [010][1111]
        //[0101010][0001010][0101011][0011010][0001110][0101111]
        //010101000010100101011001101000011100101111

        return BINAIRE_CoucHexatoBinaire;
    }


    public String HexaToCoucHexa(String texte){
        //MANGER --> M A N G E R --> 2A 0A 2B 1A 0E 2F
        String CODE = "";

        int i = 0;
        while(i < texte.length()){
            String c = "";
            char lettre = texte.charAt(i);

            c += trouverNiveau(lettre);

            //Avoir la lettre
            if(trouverNiveau(lettre).equals("0")){
                    c += String.valueOf(lettre);
            }

            else if(trouverNiveau(lettre).equals("1")){
                  c +=  String.valueOf((char) (lettre - 6));
            }

            else if(trouverNiveau(lettre).equals("2")){
                c += String.valueOf((char) (lettre - 12));
            }

            else if(trouverNiveau(lettre).equals("3")){
                c += String.valueOf((char) (lettre - 18));
            }

            else if(trouverNiveau(lettre).equals("4")){
                // "'", ".", " ", "?"
                if(String.valueOf(lettre).equals("'")){
                    c += "C";
                }
                else if(String.valueOf(lettre).equals(".")){
                    c += "D";
                }

                else if(String.valueOf(lettre).equals(" ")){
                    c += "E";
                }

                else if(String.valueOf(lettre).equals("?")){
                    c += "F";
                }

                else{
                    c += String.valueOf((char) (lettre - 24));
                }
            }

            else{
                c += "(Error)";
            }
            CODE += c;

            i++;
         }
        return CODE;//Sous le format : NXNXNX.. (N = niveau; X = la lettre)
    }

    public String CoucHexaToBinaire(String couchexa){
        //   2A       0A      2B     1A      0E       2F
        // 0101010 0001010 0101011 0011010 0001110 0101111
        String binaire = "";
        char[] couchexaBinaire = couchexa.toCharArray();

        for(char c: couchexaBinaire){

            switch(c){
                case 48: //0 en char --> 48
                    binaire += "000";
                    break;

                case 49://1 en char --> 49
                    binaire += "001";
                    break;

                case 50://2 en char --> 50
                    binaire += "010";
                    break;

                case 51://3 en char --> 51
                    binaire += "011";
                    break;

                case 52://4 en char --> 52
                    binaire += "100";
                    break;

                default :
                    int bibi = Character.digit((c), 16);
                    binaire += Integer.toBinaryString(bibi);
            }
        }
        return binaire;
    }

    public BigInteger BinairetoDecimal(String binaire){
        //binaire = 0101010 0001010 0101011 0011010 0001110 0101111
        BigInteger decimal = new BigInteger(binaire, 2);

        //decimal = 1445883971375
        return decimal;
    }


    /*Binaire --> Texte
    public String CocHexa(int code){
    //010101000010100101011001101000011100101111 (1)
    //[0101010][0001010][0101011][0011010][0001110][0101111] (2)
    //[010][1010] [000][1010] [010][1011] [001][1010] [000][1110] [010][1111] (3)
    // 2     A     0     A      2    B      1    A      0    E      2    F
    //2A0A2B1A0E2F
    //MANGER

    }

    private String BinaireToCoucHexa(int code){return "";}*/

    public String CoucHexaToHexa(String couchexa){
        String hexa = "";

        if(couchexa.length() % 2 != 0){
            return "Il manque des bouts !";
        }
        //0D2C2F2A1C2F
        // DORMIR

        for(int i = 0; i < couchexa.length() - 1; i += 2){
            String c = "";
            char cara = couchexa.charAt(i); //couche -> dans quel niveau sommes-nous ?
            char cara2 = couchexa.charAt(i+1); //position de la lettre

            if(String.valueOf(cara).equals("0")){
                c += String.valueOf(cara2);
                }

            else{
                if(String.valueOf(cara).equals("1")){
                    int pos = trouverPosition(String.valueOf(cara2));
                    c+= Niveau1[pos];
                    }
                else if(String.valueOf(cara).equals("2")){
                    int pos = trouverPosition(String.valueOf(cara2));
                    c+= Niveau2[pos];
                    }
                else if(String.valueOf(cara).equals("3")){
                    int pos = trouverPosition(String.valueOf(cara2));
                    c+= Niveau3[pos];
                    }
                else if(String.valueOf(cara).equals("4")){
                    int pos = trouverPosition(String.valueOf(cara2));
                    c+= Niveau4[pos];
                    }
                }

                hexa += c;
        }

        return hexa;
    }


    //AUTRE MÉTHODE
    public String trouverNiveau(char lettre) {
        if (Arrays.asList(Niveau0).contains(String.valueOf(lettre))){
            return "0";
        }

        else if (Arrays.asList(Niveau1).contains(String.valueOf(lettre))){
            return "1";
        }

        else if  (Arrays.asList(Niveau2).contains(String.valueOf(lettre))){
            return "2";
        }

        else if  (Arrays.asList(Niveau3).contains(String.valueOf(lettre))) {
            return "3";
        }

        else if  (Arrays.asList(Niveau4).contains(String.valueOf(lettre))) {
            return "4";
        }

        return "-1"; // Pas trouvé

    }

    public int trouverPosition(String lettre){
        for(int i = 0; i < Niveau0.length; i++){
            if(Niveau0[i].equals(lettre.toUpperCase())){
                return i;
            }
            }

            return -1;

    }



    public boolean egal(String s1, String s2){
            return s1.equals(s2);
    }
}
