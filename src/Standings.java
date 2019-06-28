import java.util.*;

public class Standings {

    private List<Player> classifica = new ArrayList<Player>() ; //è una lista di giocatori
    private int nTurno ;

    public void sortClassifica(){
        Collections.sort(classifica);
        Collections.reverse(classifica);
    }


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


    //public void orderStandings(){
        //this.classifica.sort();
    //}

    public String printClassifica(){
        String message=null ;
        int numeroTurniNecessari=0 ;
        int i=1 ;
        if(this.nTurno==0)
            message="CLASSIFICA INIZIALE\n\n" ;
        else{
            //if(this.nTurno==classifica.size() || this.nTurno+1==classifica.size())
                //message="CLASSIFICA FINALE\n\n" ;
            //else
                message="CLASSIFICA AL TURNO "+this.nTurno+"\n\n" ;

            message=message+"Pos."+"    "+"COGNOME"+"         "+"NOME"+"         "+"PUNTEGGIO\n\n" ; }

        for(Player player1 : this.classifica) {
            String cognome ;
            String nome ;
            String allunga="          " ;

            message=message+i+"       " ;

            cognome=player1.getSurname()+allunga ;
            message=message+cognome.substring(0,10) ;

            message=message+"      " ;


            nome=player1.getName()+allunga ;
            message=message+nome.substring(0,3) ;

            message=message+"             " ;


            message=message+player1.getTournPoints()+"\n" ;
            i=i+1 ;
        }

        return message ;
    }



}
