package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe pour gérer l'action de casser un bloc dans le jeu.
 * Permet de vérifier si le joueur est à proximité d'un bloc et de le casser.
 */
public class Casser {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;
    private boolean casseValide;


    /**
     * Constructeur de la classe Casser.
     * @param map
     * @param mapVue
     * @param joueur
     */
    public Casser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
        this.casseValide = false;
    }

    /**
     * Méthode pour casser un bloc à la position (x, y) si le joueur est à proximité.
     * @param x Coordonnée x du bloc à casser.
     * @param y Coordonnée y du bloc à casser.
     */
    public void casserBloc(int x, int y) {
        double joueurX = joueur.getX() / 32;  // conversion pixels → tuiles
        double joueurY = joueur.getY() / 32;

        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

            if (distanceX <= 2 && distanceY <= 2) {
                map.getTerrain()[y][x] = 0; // ID 0 = vide
                mapVue.mettreAJourTuile(x, y, 0);
                casseValide = true;
            }

    }

    /**
     * Vérifie si la casse du bloc est valide.
     * @return true si la casse est valide, false sinon.
     */
    public boolean isCasseValide() {
        return casseValide;
    }
}



