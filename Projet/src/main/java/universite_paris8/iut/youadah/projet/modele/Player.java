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

    /** Le joueur tente de ramasser les objets sur sa tuile. */
    public boolean ramasser(Environnement env, Inventaire inventaire) {
        return env.ramasserAutourDuJoueur(this, inventaire);
    }

    /**
     * Le joueur dépose l’objet en main (sans gérer ici la quantité/inventaire).
     * La gestion des quantités reste côté Inventaire/CaseInventaire.
     * @return true si quelque chose a été déposé
     */
    public boolean deposerObjetEnMain(Environnement env) {
        if (objetPossede == null) return false;
        env.deposerDepuisJoueur(objetPossede, this);
        return true;
    }

    /** Dépose un objet donné à la position du joueur. */
    public boolean deposer(Objet objet, Environnement env) {
        if (objet == null) return false;
        env.deposerDepuisJoueur(objet, this);
        return true;
    }
}
