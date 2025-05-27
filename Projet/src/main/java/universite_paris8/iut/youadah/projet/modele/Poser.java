package universite_paris8.iut.youadah.projet.modele;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Poser {
    private Map map;
    private MapVue mapVue;
    private Player joueur;

    public Poser(Map map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
    }

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
