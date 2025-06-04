package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet{
    GameMap carte;
    MapVue carteVue;
    Player joueur;
    TilePane tileMap;
    
    public Pioche(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, TilePane tileMap) {
        super(nom, rarete, false);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }

    public void utiliser(int x, int y){
        Casser casseur = new Casser(carte, carteVue,joueur);
        casseur.casserBloc(x, y);
    }
}
