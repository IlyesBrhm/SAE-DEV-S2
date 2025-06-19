// Déclaration du package de la classe
package universite_paris8.iut.youadah.projet.modele.actions;

// Importation des classes nécessaires
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant l'action de poser un bloc sur la carte du jeu.
 * Vérifie que l'emplacement est vide et que le joueur est à portée.
 */
public class Poser {

    // Référence à la carte du jeu (modèle)
    private GameMap map;

    // Référence à la vue graphique de la carte
    private MapVue mapVue;

    // Référence au joueur effectuant l'action
    private Player joueur;

    /**
     * Constructeur de la classe Poser
     * @param map la carte du jeu
     * @param mapVue la vue de la carte
     * @param joueur le joueur qui pose le bloc
     */
    public Poser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
    }

    /**
     * Tente de poser un bloc à la position (x, y), si celle-ci est vide et à portée
     * @param x colonne de la tuile cible
     * @param y ligne de la tuile cible
     * @param idBloc l’identifiant du bloc à poser (ex : 1 = herbe, 2 = terre, etc.)
     */
    public void poserBloc(int x, int y, int idBloc) {
        // Conversion des coordonnées du joueur (en pixels) vers des indices de tuiles
        double joueurX = joueur.getX() / 32.0;
        double joueurY = joueur.getY() / 32.0;

        // Calcul de la distance entre le joueur et la case ciblée
        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        // Vérifie si la tuile est à portée du joueur
        if (distanceX <= 2 && distanceY <= 2) {
            // Vérifie que la case est vide (ID = 0)
            if (map.getTerrain()[y][x] == 0) {
                // Place le bloc dans la map (modèle)
                map.getTerrain()[y][x] = idBloc;

                // Met à jour la vue pour refléter le nouveau bloc placé
                mapVue.mettreAJourTuile(x, y, idBloc);
            }
        }
    }
}
