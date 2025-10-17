package universite_paris8.iut.youadah.projet.modele;

public class Player extends Personnage {
    private Objet objetPossede;

    public Player(double x, double y) {
        super(x, y);
        objetPossede = null;
    }

    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    public Objet getObjetPossede() {
        return objetPossede;
    }


    public boolean ramasser(Environnement env, Inventaire inventaire) {
        return env.ramasserAutourDuJoueur(this, inventaire);
    }


    public boolean deposerObjetEnMain(Environnement env) {
        if (objetPossede == null) return false;
        env.deposerDepuisJoueur(objetPossede, this);
        return true;
    }


    public boolean deposer(Objet objet, Environnement env) {
        if (objet == null) return false;
        env.deposerDepuisJoueur(objet, this);
        return true;
    }


}
