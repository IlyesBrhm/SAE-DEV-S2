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

    /**
     * Incrémente la quantité de l'objet.
     * @param n Nombre à ajouter à la quantité actuelle.
     */
    public void incrementerQuantite(int n) {
        this.quantite += n;
    }

    /**
     * Décrémente la quantité de l'objet.
     * @param n Nombre à soustraire de la quantité actuelle.
     */
    public void decrementerQuantite(int n) {
        this.quantite -= n;
    }

    /**
     * Utilise l'objet à la position (x, y) sur la carte.
     * @param x Coordonnée x de l'emplacement.
     * @param y Coordonnée y de l'emplacement.
     * @param carteVue Vue de la carte pour mettre à jour l'affichage.
     */
    public abstract void utiliser(int x, int y, MapVue carteVue);

    @Override
    public String toString() {
        return nom + " (x" + quantite + ", Rareté : " + rarete + ")";
    }

    /**
     * Vérifie si deux objets sont égaux en comparant leur nom.
     * Deux objets sont considérés égaux s'ils ont le même nom.
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
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
