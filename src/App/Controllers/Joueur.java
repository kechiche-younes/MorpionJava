package App.Controllers;

public class Joueur {

    private int id;
    private String Name;
    private Boolean ai=false;
    private Boolean winner=false;
    public Boolean myTour=false;
    private int symbole;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Boolean getAi() {
        return ai;
    }
    public void setAi(Boolean ai) {
        this.ai = ai;
    }
    public Boolean getWin() {
        return winner;
    }
    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
    public int getSymbole() {
        return symbole;
    }
    public void setSymbole(int symbole) {
        this.symbole = symbole;
    }



}
