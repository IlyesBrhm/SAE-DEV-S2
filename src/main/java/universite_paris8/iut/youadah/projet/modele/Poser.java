package universite_paris8.iut.youadah.projet.modele;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Poser {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;

    public Poser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
    }

    public void poserBloc(int x, int y, int idBloc) {
        int joueurXTile = (int)(joueur.getX() / 32.0);
        int joueurYTile = (int)(joueur.getY() / 32.0);

        double distanceX = Math.abs(x - joueurXTile);
        double distanceY = Math.abs(y - joueurYTile);

        // 1 verif distance
        // 2 emplacement vide ?
        // 3 pas sur le joueur (il occupe 2 cases en hauteur)
        if (distanceX <= 2 && distanceY <= 2
                && map.getTerrain()[y][x] == 0
                && !(x == joueurXTile && (y == joueurYTile || y == joueurYTile + 1))) {
            map.getTerrain()[y][x] = idBloc;
            mapVue.mettreAJourTuile(x, y, idBloc);
        }
    }
}
