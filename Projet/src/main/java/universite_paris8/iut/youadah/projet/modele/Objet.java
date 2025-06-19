package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.MapVue;

public abstract class Objet {

    private String nom;
    private int rarete;
    private boolean consomable;
    private int quantite;

    public Objet(String nom, int rarete, boolean consomable) {
        this.nom = nom;
        this.rarete = rarete;
        this.consomable = consomable;
        this.quantite = 1; // par défaut 1
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void incrementerQuantite(int n) {
        this.quantite += n;
    }

    public void decrementerQuantite(int n) {
        this.quantite -= n;
    }

    public abstract void utiliser(int x, int y, MapVue carteVue);

    @Override
    public String toString() {
        return nom + " (x" + quantite + ", Rareté : " + rarete + ")";
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
