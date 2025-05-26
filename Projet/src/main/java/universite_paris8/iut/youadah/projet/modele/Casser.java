package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Casser {
    private Map map;
    private MapVue mapVue;
    private Player joueur;

    public Casser(Map m, MapVue mapVue, Player p) {
        this.map = m;
        this.mapVue = mapVue;
        joueur = p;
    }

    public void casserBloc(int x, int y) {
        double joueurX = joueur.getX() / 32;  // conversion pixels → tuiles
        double joueurY = joueur.getY() / 32;

        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        if (distanceX <= 2 && distanceY <= 2) {
            map.getTerrain()[y][x] = 0; // ID 0 = vide
            mapVue.mettreAJourTuile(x, y, 0);
        }
    }
}



