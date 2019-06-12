import java.util.ArrayList;
import java.util.List;

public class Standings {

    private List<Player> classifica = new ArrayList<Player>() ; //è una lista di giocatori
    private int nTurno ;


    public int getnTurno() {
        return nTurno;
    }

    public void setnTurno(int nTurno) {
        this.nTurno = nTurno;
    }

    public void addPlayer(Player player){
        classifica.add(player) ;
    }

    public List<Player> getClassifica() {
        return classifica;
    }

    public String printClassifica(){
        String message=null ;
        if(this.nTurno==0)
            message="CLASSIFICA INIZIALE\n\n" ;
        else
            if(this.nTurno==classifica.size() || this.nTurno+1==classifica.size())
                message="CLASSIFICA FINALE\n\n" ;
            else
                message="CLASSIFICA AL TURNO "+this.nTurno ;

            message=message+"Pos."+"    "+"COGNOME"+"         "+"NOME"+"         "+"PUNTEGGIO\n" ;
        return message ;
    }



}
