package universite_paris8.iut.youadah.projet.modele;

/**
 * Représente un objet du jeu avec un nom et un niveau de rareté.
 */
public class Objet {

    private String nom;
    private int rarete;

    public Objet(String nom, int rarete) {
        this.nom = nom;
        this.rarete = rarete;
    }

    public String getNom() {
        return nom;
    }

    public int getRarete() {
        return rarete;
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
