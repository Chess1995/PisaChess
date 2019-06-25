import java.util.ArrayList;
import java.util.List;

public class Pairing {

    private Player player1 ;
    private Player player2 ;
    private double results ;
    private List<String> moves = new ArrayList<String>() ; //è la lista di mosse del match
    private int board ;
    private int matchId ;
    private int seguito=0 ; //serve per indicare se un match è già visionato da un arbitro


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public double getResults() {
        return results;
    }

    public void setResults(double results) {
        this.results = results;
    }

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public int getSeguito() {
        return seguito;
    }

    public void setSeguito(int seguito) {
        this.seguito = seguito;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }


    public List<String> getMoves() {
        return moves;
    }

    public void addMoves(String move) {
        moves.add(move) ;
    }

    public String getFormatOfResults(){
        String message=null ;
        if (this.results==1)
            message="1-0" ;
        else
        if(this.results==0.5)
            message="1/2" ;
        else
            message="0-1" ;

        return message ;
    }


}
