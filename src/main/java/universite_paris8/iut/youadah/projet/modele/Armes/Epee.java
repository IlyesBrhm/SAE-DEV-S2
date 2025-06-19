package universite_paris8.iut.youadah.projet.modele.Armes;

// Importation des classes nécessaires
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant une épée, qui est un type d'objet que le joueur peut utiliser.
 * Elle contient les informations nécessaires pour l’utiliser dans le monde du jeu.
 */
public class Epee extends Objet {

    // Dégâts que l'épée inflige
    private int degats;

    // Références utiles pour pouvoir interagir avec le monde du jeu
    GameMap carte;
    MapVue carteVue;
    Player joueur;
    TilePane tileMap;

    /**
     * Constructeur de l'épée.
     *
     * @param nom       Le nom de l'épée (ex : "Épée de feu")
     * @param rarete    Niveau de rareté (impacte les dégâts)
     * @param carte     Référence à la carte du jeu
     * @param carteVue  Référence à la vue de la carte (pour mise à jour graphique)
     * @param joueur    Référence au joueur qui possède l'épée
     * @param tileMap   Référence au composant graphique représentant la grille de tuiles
     */
    public Epee(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, TilePane tileMap) {
        super(nom, 1, false); // appelle le constructeur de Objet avec quantité = 1, et non empilable
        this.degats = calculerDegatsSelonRarete(rarete);

        // Sauvegarde les références nécessaires à l'utilisation
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }

    /**
     * Calcule les dégâts selon la rareté de l'épée.
     * Plus la rareté est grande, plus l'épée est puissante.
     *
     * @param rarete Un entier représentant la rareté
     * @return Les dégâts calculés (minimum 1)
     */
    private int calculerDegatsSelonRarete(int rarete) {
        return Math.max(1, rarete);  // Minimum de 1 dégât
    }

    // Getters pour accéder aux attributs privés
    public int getDegats() {
        return degats;
    }

    public GameMap getCarte() {
        return carte;
    }

    public MapVue getCarteVue() {
        return carteVue;
    }

    public Player getJoueur() {
        return joueur;
    }

    public TilePane getTileMap() {
        return tileMap;
    }

    /**
     * Méthode prévue pour utiliser l'épée (actuellement incomplète).
     * Elle pourrait servir à infliger des dégâts à une cible en (x, y).
     * 
     * Cette méthode crée une instance de l'action Taper mais ne l'utilise pas encore.
     * Dans une implémentation future, elle devrait identifier les ennemis à la position (x,y)
     * et leur infliger des dégâts en fonction de la puissance de l'épée.
     *
     * @param x Coordonnée X de la cible
     * @param y Coordonnée Y de la cible
     */
    public void utiliser(int x, int y){
        Taper taper = new Taper();
        // Ici tu pourrais appeler une méthode de Taper pour attaquer à cette position.
        // Mais la méthode actuelle de Taper utilise des listes de cibles, donc il faut l’adapter.
        // taper.attaquerAvecEpee(joueur, cibles, overlay);  ← exemple de ce que tu pourrais faire
    }
}
