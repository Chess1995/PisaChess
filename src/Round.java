import java.util.ArrayList;
import java.util.List;

public class Round {
    
    private List<Pairing> pairingList = new ArrayList<Pairing>() ; // Il turno è una lista di abbinamenti!
    private int nRound ; //identifica il numero del turno.


    public int getnRound() {
        return nRound;
    }

    public void setnRound(int nRound) {
        this.nRound = nRound;
    }

    public void addPairing(Pairing pairing){
        this.getPairingList().add(pairing) ;
    }

    public String printRound(){
        int i=1 ;
        String message=null ;
        String allunga="          " ;
        message="TURNO "+this.nRound+"\n\n\n" ;
        message=message+"TAVOLO"+"       BIANCO"+"                "+"NERO\n" ;
        //message=message+"        "+"Cognome    Nome      "+"Cognome   Nome\n" ;
        for (Pairing pairing :this.pairingList){
            String nome ;
            String cognome ;
            message=message+i+"            ";
            i++ ;

            //stampa
            cognome=pairing.getPlayer1().getSurname()+allunga ;
            message=message+cognome.substring(0,10) ;
            message=message+"   " ;
            nome=pairing.getPlayer1().getName()+allunga ;
            message=message+nome.substring(0,3)+"      " ;

            cognome=pairing.getPlayer2().getSurname()+allunga ;
            message=message+cognome.substring(0,10) ;
            message=message+"   " ;
            nome=pairing.getPlayer2().getName()+allunga ;
            message=message+nome.substring(0,3)+"\n";



        }

        return(message) ;
    }

    public List<Pairing> getPairingList() {
        return pairingList;
    }

    public void setPairingList(List<Pairing> pairingList) {
        this.pairingList = pairingList;
    }
}
