package universite_paris8.iut.youadah.projet.modele;

public abstract class Objet {
    public String nom;
    public int rarete;


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

    public abstract void utiliser();

    public String toString() {
        return nom + " (rarete: " + rarete + ")";
    }
}


