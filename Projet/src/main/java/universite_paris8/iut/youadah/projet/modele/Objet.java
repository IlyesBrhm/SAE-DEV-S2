package universite_paris8.iut.youadah.projet.modele;

public abstract class Objet {

    private String nom;
    private int rarete;
    private boolean consomable;

    public Objet(String nom, int rarete, boolean consomable) {
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


    public abstract void utiliser(int x, int y);

    @Override
    public String toString() {
        return nom + " (  Rareté : " + rarete + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objet)) return false;
        Objet autre = (Objet) o;
        return nom.equals(autre.nom); // même nom = même type d’objet
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
