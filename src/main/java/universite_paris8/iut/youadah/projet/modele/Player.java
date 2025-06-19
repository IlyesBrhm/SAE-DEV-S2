package universite_paris8.iut.youadah.projet.modele;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Classe représentant le joueur dans le jeu
 * Hérite de la classe Personnage et ajoute la gestion de l'objet actuellement possédé
 */
public class Player extends Personnage {
    // L'objet actuellement tenu par le joueur
    private Objet objetPossede;

    /**
     * Constructeur du joueur
     * 
     * @param x Position X initiale du joueur
     * @param y Position Y initiale du joueur
     */
    public Player(double x, double y) {
        super(x,y);
        objetPossede = null; // Aucun objet possédé au départ
    }

    /**
     * Définit l'objet actuellement possédé par le joueur
     * 
     * @param objetPossede Le nouvel objet à posséder
     */
    public void setObjetPossede(Objet objetPossede) {
        this.objetPossede = objetPossede;
    }

    /**
     * Récupère l'objet actuellement possédé par le joueur
     * 
     * @return L'objet possédé ou null si le joueur ne possède aucun objet
     */
    public Objet getObjetPossede() {
        return objetPossede;
    }
}
