package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Casser {
    private GameMap map;
    private MapVue mapVue;
    private Player joueur;
    private boolean casseValide;


    public Casser(GameMap map, MapVue mapVue, Player joueur) {
        this.map = map;
        this.mapVue = mapVue;
        this.joueur = joueur;
        this.casseValide = false;
    }

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

    public boolean isCasseValide() {
        return casseValide;
    }
}



