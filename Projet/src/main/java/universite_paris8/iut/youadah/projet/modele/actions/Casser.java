
package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Casser implements ActionStrategies {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;
    private int x, y;

    public Casser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
    }


    public void definirCoordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean executer() {
        double joueurX = joueur.getX() / 32;
        double joueurY = joueur.getY() / 32;

        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        if (distanceX <= 2 && distanceY <= 2) {
            map.getTerrain()[y][x] = 0; // ID 0 = vide
            mapVue.mettreAJourTuile(x, y, 0);
            return true;
        }
        return false;
    }
}