package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet{
    Map carte;
    MapVue carteVue;
    Player joueur;
    TilePane tileMap;
    
    public Pioche(String nom, int rarete, Map c, MapVue cv, Player j, TilePane t) {
        super(nom, rarete, false);
        carte = c;
        carteVue = cv;
        joueur = j;
        tileMap = t;
    }

    public void utiliser(int x, int y){
        Casser casseur = new Casser(carte, joueur);

        boolean casse = casseur.casserBloc(x, y);
        if (casse) {
            carteVue.mettreAJourMap(carte.getTerrain(), tileMap);
        }
    }
}
