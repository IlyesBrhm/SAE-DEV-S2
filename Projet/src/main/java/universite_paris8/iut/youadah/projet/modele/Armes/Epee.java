package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.objet.Objet;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Epee extends Objet {

    private int degats;

    GameMap carte;
    MapVue carteVue;
    Player joueur;
    TilePane tileMap;

    public Epee(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, TilePane tileMap) {
        super(nom, 1, false);
        this.degats = calculerDegatsSelonRarete(rarete);

        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }



    private int calculerDegatsSelonRarete(int rarete) {
        return Math.max(1, rarete);
    }


    public Player getJoueur() {
        return joueur;
    }

    public TilePane getTileMap() {
        return tileMap;
    }

    public void utiliser(int x, int y){

    }

}