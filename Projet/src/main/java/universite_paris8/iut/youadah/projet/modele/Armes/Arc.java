package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.MapVue;

import java.util.List;

/**
 * Représente un arc utilisé par le joueur pour tirer des flèches infligeant des dégâts à distance.
 */
public class Arc extends Objet {

    private final int degats;
    private final int portee;

    private final GameMap carte;
    private final Player joueur;

    public Arc(String nom, int rarete, GameMap carte, Player joueur) {
        super(nom, 1, false);
        this.degats = Math.max(1, rarete);             // Dégâts selon rareté
        this.portee = 150 + rarete * 20;               // Portée selon rareté
        this.carte = carte;
        this.joueur = joueur;
    }

    public int getDegats() {
        return degats;
    }

    public int getPortee() {
        return portee;
    }

    public Player getJoueur() {
        return joueur;
    }

    /**
     * Tire une flèche dirigée vers une cible, avec gestion des dégâts à la collision.
     * @param cibleX Coordonnée X cible
     * @param cibleY Coordonnée Y cible
     * @param couche Pane où afficher la flèche
     * @param cibles Liste des ennemis à toucher
     * @param overlay Effet visuel de dégâts
     */
    public void tirerFleche(double cibleX, double cibleY, Pane couche, List<Personnage> cibles, Pane overlay) {
        Fleche fleche = new Fleche(
                joueur.getX(),
                joueur.getY(),
                cibleX,
                cibleY,
                cibles,
                overlay,
                degats,
                carte // ici tu passes la carte
        );
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }

    @Override
    public void utiliser(int x, int y, MapVue carteVue) {
        // L'arc n'est pas utilisé directement sur la carte, mais pour tirer des flèches

    }

}
