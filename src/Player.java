public class Player {

    private String name ;
    private String surname ;
    private String title ;
    private int elo ;
    private double tournPoints ;
    private int id ;


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

    public void setTournPoints(double tournPoints) {
        this.tournPoints = tournPoints;
    }
}
