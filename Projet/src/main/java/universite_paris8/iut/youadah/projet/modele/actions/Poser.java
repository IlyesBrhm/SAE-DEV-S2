package universite_paris8.iut.youadah.projet.modele.actions;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;


/** * Classe pour gérer la pose de blocs sur la carte.
 * Permet au joueur de poser un bloc à une position donnée s'il est à proximité.
 */
public class Poser {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;

    public Poser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
    }

    /**
     * Méthode pour poser un bloc à la position (x, y) si le joueur est à proximité.
     * @param x Coordonnée x de l'emplacement où poser le bloc.
     * @param y Coordonnée y de l'emplacement où poser le bloc.
     * @param idBloc ID du bloc à poser (par exemple, 2 pour terre, 1 pour herbe, etc.).
     */
    public void poserBloc(int x, int y, int idBloc) {
        double joueurX = joueur.getX() / 32.0;  // position en tuiles
        double joueurY = joueur.getY() / 32.0;

        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        if (distanceX <= 2 && distanceY <= 2) {
            // Seulement si l'emplacement est vide
            if (map.getTerrain()[y][x] == 0) {
                map.getTerrain()[y][x] = idBloc; // Exemple : 2 = terre, 1 = herbe, etc.
                mapVue.mettreAJourTuile(x, y, idBloc);
            }
        }
    }
}
