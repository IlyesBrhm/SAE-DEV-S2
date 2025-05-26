package universite_paris8.iut.youadah.projet.modele;

import java.awt.*;

public abstract class Objet {

    private String nom;
    private int rarete;
    private boolean consomable;
    private Image image;

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

    public void setConsomable(boolean consomable) {
        this.consomable = consomable;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRarete(int rarete) {
        this.rarete = rarete;
    }

    public abstract void utiliser(int x, int y);

    @Override
    public String toString() {
        return nom + " (Rareté : " + rarete + ")";
    }
}