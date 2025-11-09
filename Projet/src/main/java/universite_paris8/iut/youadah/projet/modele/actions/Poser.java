package universite_paris8.iut.youadah.projet.modele.actions;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;


public class Poser implements ActionStrategies {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;
    private int x,  y,  idBloc;

    public Poser(GameMap map, MapVue mapVue, Player joueur, int x, int y, int idBloc) {// jai modifier sa
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
        this.x = x;
        this.y = y;
        this.idBloc = idBloc;
    }

    public boolean executer() {
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
        return false;
    }
}



