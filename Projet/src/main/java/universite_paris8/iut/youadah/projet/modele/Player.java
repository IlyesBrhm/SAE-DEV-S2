package universite_paris8.iut.youadah.projet.modele;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe représentant un joueur dans le jeu.
 * Hérite de la classe Personnage.
 */
public class Player extends Personnage {
    // Propriété pour stocker l'objet possédé par le joueur
    private Objet objetPossede;

    public Player(double x, double y) {
        super(x,y);
        objetPossede = null;
    }

    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    public Objet getObjetPossede() {
        return objetPossede;
    }
}