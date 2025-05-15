package universite_paris8.iut.youadah.projet.modele;

/**
 * Représente un objet du jeu avec un nom et un niveau de rareté.
 */
public class Objet {

    private String nom;
    private int rarete;
    private boolean consomable = false;

    public Objet(String nom, int rarete) {
        this.nom = nom;
        this.rarete = rarete;
        this.consomable = consomable;
    }

    public String getNom() {
        return nom;
    }

    public int getRarete() {
        return rarete;
    }

    public boolean getConsomable() {
        return consomable;
    }

    public void setConsomable(boolean consomable) {
        this.consomable = consomable;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRarete(int rarete) {
        this.rarete = rarete;
    }

    @Override
    public String toString() {
        return nom + " (Rareté : " + rarete + ")";
    }
}
