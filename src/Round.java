import java.util.ArrayList;

public class Round {

    ArrayList<Pairing> pairingList = new ArrayList<Pairing>() ; // Il turno è una lista di abbinamenti!
    private int nRound ; //identifica il numero del turno.


    public int getnRound() {
        return nRound;
    }

    public void setnRound(int nRound) {
        this.nRound = nRound;
    }
}
