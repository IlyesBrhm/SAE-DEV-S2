package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.modele.actions.Tirer;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Arc extends Objet {

    private int degats;
    private int portee;

    private GameMap carte;        // Référence à la carte du jeu
    private MapVue carteVue;      // Référence à la vue de la carte (pour affichage)
    private Player joueur;        // Référence au joueur qui possède l'arc
    private Pane tileMap;     // Référence à la couche graphique représentant la carte

    public Arc(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, Pane tileMap) {
        super(nom, 1, false);  // On appelle le constructeur d’Objet (quantité=1, non empilable)
        this.degats = calculerDegatsSelonRarete(rarete);         // Calcul des dégâts selon la rareté
        this.portee = 150 + rarete * 20;                         // Portée de base augmentée selon la rareté

        // Références utiles pour utiliser l'arc dans le jeu
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }

    private int calculerDegatsSelonRarete(int rarete) {
        return Math.max(1, rarete);  // Minimum 1 dégât, sinon la rareté = dégâts
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
     * Tire une flèche en direction d’une cible (coordonnées écran).
     * @param cibleX Position X de la cible (pixels)
     * @param cibleY Position Y de la cible (pixels)
     * @param couche Couche graphique sur laquelle la flèche doit être affichée
     */
    public void tirerFleche(double cibleX, double cibleY, Pane couche) {
        // Position de départ, celle du joueur
        double departX = joueur.getX();
        double departY = joueur.getY();

        // Création d'une flèche dirigée vers la cible
        Fleche fleche = new Fleche(departX, departY, cibleX, cibleY);

        // On l’ajoute à la couche graphique et on lance son animation
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }

    /**
     * Méthode héritée de Objet : déclenchée quand le joueur utilise l’objet.
     * Ici, pourrait permettre de déclencher un tir de flèche.
     * @param x coordonnée x (peut être utilisée pour définir la cible)
     * @param y coordonnée y
     */
    @Override
    public void utiliser(int x, int y) {
        Tirer tirer = new Tirer();  // Crée une instance de l'action de tir

        // La ligne suivante est commentée mais permettrait de faire tirer l'arc avec gestion complète :
        // tirer.attaquerAvecArc(joueur, GameMap.getListeJoueurs(), carteVue.getOverlayPane(), this);
    }
}