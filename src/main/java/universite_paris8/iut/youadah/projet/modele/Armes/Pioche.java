package universite_paris8.iut.youadah.projet.modele.Armes;

// Importation des classes nécessaires
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.modele.actions.Casser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant une pioche, un outil utilisé pour casser des blocs dans le monde du jeu.
 */
public class Pioche extends Objet {

    // Références aux éléments du jeu nécessaires pour l'utilisation de la pioche
    GameMap carte;
    MapVue carteVue;
    Player joueur;
    ObjetAuSol objetAuSol;
    Pane playerLayer;

    /**
     * Constructeur de la pioche.
     *
     * @param nom         Nom de la pioche (ex : "Pioche en pierre")
     * @param rarete      Rarete de l'objet (impact possible à développer)
     * @param carte       Référence à la carte du jeu
     * @param carteVue    Référence à la vue de la carte (graphisme)
     * @param joueur      Joueur possédant l'objet
     * @param objetAuSol  Gestion des objets déposés au sol
     * @param playerLayer Pane JavaFX pour l'affichage des objets déposés
     */
    public Pioche(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, ObjetAuSol objetAuSol, Pane playerLayer) {
        super(nom, rarete, false);  // Appelle le constructeur de Objet (non empilable)
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.objetAuSol = objetAuSol;
        this.playerLayer = playerLayer;
    }

    /**
     * Utilise la pioche pour casser un bloc à la position (x, y).
     * Si le bloc est cassable et proche du joueur, il est remplacé par du vide,
     * et une version "objet" du bloc est déposée au sol.
     *
     * @param x Coordonnée X de la tuile
     * @param y Coordonnée Y de la tuile
     */
    public void utiliser(int x, int y){
        // Vérifie que le bloc ciblé n’est pas vide
        if (!carteVue.getBloc(x, y).equals("Vide")) {
            // Crée un objet Bloc à partir de son nom
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, carteVue, joueur, carte.getTile(y, x));

            // Crée une instance de l'action Casser et tente de casser le bloc
            Casser casseur = new Casser(carte, carteVue, joueur);
            casseur.casserBloc(x, y);

            // Si le bloc a bien été cassé, on le fait apparaître au sol
            if (casseur.isCasseValide())
                objetAuSol.deposer(bloc, x, y, playerLayer);
        } else {
            // Si le bloc est vide, on affiche un message de debug
            System.out.println("aaaaaaaa");  // Message de debug indiquant qu'on essaie de casser un bloc vide
        }
    }
}
