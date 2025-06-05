package universite_paris8.iut.youadah.projet.modele;

import java.awt.*;

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
        return nom + " (Rareté : " + rarete + ")";
    }
}