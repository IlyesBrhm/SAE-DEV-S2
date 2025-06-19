package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.MapVue;

import java.util.List;

/**
 * Représente un arc utilisé par le joueur pour tirer des flèches infligeant des dégâts à distance.
 * Cette classe gère la création et le tir de flèches, ainsi que leurs propriétés comme les dégâts et la portée.
 */
public class Arc extends Objet {

    // Caractéristiques de l'arc
    private final int degats;    // Quantité de dégâts infligés par chaque flèche
    private final int portee;    // Distance maximale de tir en pixels

    // Références aux éléments du jeu nécessaires pour l'utilisation de l'arc
    private final GameMap carte;    // Carte du jeu pour la détection des collisions
    private final MapVue carteVue;  // Vue de la carte pour l'affichage
    private final Player joueur;    // Joueur qui utilise l'arc
    private final Pane tileMap;     // Conteneur graphique pour l'affichage des flèches

    /**
     * Constructeur de l'arc.
     *
     * @param nom      Nom de l'arc (ex: "Arc en bois", "Arc elfique")
     * @param rarete   Niveau de rareté qui détermine les dégâts et la portée
     * @param carte    Référence à la carte du jeu pour la détection des collisions
     * @param carteVue Référence à la vue de la carte pour l'affichage
     * @param joueur   Joueur qui utilisera l'arc
     * @param tileMap  Conteneur graphique pour l'affichage des flèches
     */
    public Arc(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, Pane tileMap) {
        super(nom, 1, false);  // Appelle le constructeur de Objet (quantité=1, non empilable)
        this.degats = Math.max(1, rarete);             // Dégâts selon rareté (minimum 1)
        this.portee = 150 + rarete * 20;               // Portée selon rareté (base 150 + bonus)
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }

    /**
     * Récupère les dégâts infligés par chaque flèche tirée par cet arc.
     * 
     * @return La valeur des dégâts
     */
    public int getDegats() {
        return degats;
    }

    /**
     * Récupère la portée maximale de tir de l'arc en pixels.
     * 
     * @return La valeur de la portée
     */
    public int getPortee() {
        return portee;
    }

    /**
     * Récupère le joueur qui utilise cet arc.
     * 
     * @return Le joueur associé à cet arc
     */
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


    /**
     * Implémentation de la méthode utiliser héritée de la classe Objet.
     * Cette méthode est actuellement vide car le tir d'arc est géré différemment.
     * 
     * Note: Le tir réel est géré dans GameController via un clic de souris
     * qui appelle directement la méthode tirerFleche() avec les coordonnées cibles.
     * Cette méthode pourrait être utilisée à l'avenir pour implémenter un tir automatique
     * dans une direction prédéfinie.
     *
     * @param x Coordonnée X de la cible (non utilisée actuellement)
     * @param y Coordonnée Y de la cible (non utilisée actuellement)
     */
    @Override
    public void utiliser(int x, int y) {
        // Ici, on pourrait éventuellement déclencher un tir automatique selon direction
        // Mais le tir réel est géré dans GameController via un clic souris.
    }
}
