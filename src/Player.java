public class Player implements Comparable<Player> {

    private String name ;
    private String surname ;
    private String title ;
    private int elo ;
    private double tournPoints=0 ;
    private int id ;

    public int compareTo(Player player){

        return Double.toString(tournPoints).compareTo(Double.toString(player.getTournPoints())) ;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTournPoints() {
        return tournPoints;
    }

    public void addTournPoints(double risultato) {
        this.tournPoints = this.tournPoints+risultato ;
    }
}
