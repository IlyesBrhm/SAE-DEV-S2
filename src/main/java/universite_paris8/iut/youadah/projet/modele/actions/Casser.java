// Déclaration du package dans lequel se trouve cette classe
package universite_paris8.iut.youadah.projet.modele.actions;

// Importation des classes nécessaires à cette action
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant l'action de "casser" un bloc dans la carte du jeu.
 * Elle vérifie que le joueur est à portée du bloc ciblé avant de le supprimer.
 */
public class Casser {

    // Référence à la carte du jeu (modèle)
    private GameMap map;

    // Référence à la vue de la carte (interface graphique)
    private MapVue mapVue;

    // Référence au joueur qui effectue l'action
    private Player joueur;

    // Booléen permettant de savoir si l’action de casse a réussi
    private boolean casseValide;

    /**
     * Constructeur de la classe Casser
     * @param map la carte du jeu
     * @param mapVue la vue graphique de la carte
     * @param joueur le joueur effectuant l’action
     */
    public Casser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
        this.casseValide = false; // initialement, aucune casse n'a été validée
    }

    /**
     * Méthode principale permettant de casser un bloc aux coordonnées données
     * @param x colonne de la tuile à casser
     * @param y ligne de la tuile à casser
     */
    public void casserBloc(int x, int y) {
        // Position du joueur convertie de pixels en indices de tuiles (chaque tuile fait 32x32 px)
        double joueurX = joueur.getX() / 32;
        double joueurY = joueur.getY() / 32;

        // Calcul de la distance entre le joueur et la tuile ciblée
        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        // Si la tuile est à une distance raisonnable (2 cases ou moins), on peut la casser
        if (distanceX <= 2 && distanceY <= 2) {
            map.getTerrain()[y][x] = 0; // On remplace la tuile par l'ID 0 (sol vide)
            mapVue.mettreAJourTuile(x, y, 0); // On met à jour l'affichage de cette tuile dans la vue
            casseValide = true; // L'action est considérée comme réussie
        }
    }

    /**
     * Indique si la dernière tentative de casse a été validée
     * @return true si le bloc a bien été cassé, false sinon
     */
    public boolean isCasseValide() {
        return casseValide;
    }
}
