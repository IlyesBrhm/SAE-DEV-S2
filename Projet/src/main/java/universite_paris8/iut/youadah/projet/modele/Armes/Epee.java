package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;
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


    //Calcule les dégâts selon la rareté de l'épée par exemple : commune = 1, rare = 2, légendaire = 3
    private int calculerDegatsSelonRarete(int rarete) {
        return Math.max(1, rarete);  // minimum 1 dégât
    }

    public int getDegats() {
        return degats;
    }

    public GameMap getCarte() {
        return carte;
    }

    public MapVue getCarteVue() {
        return carteVue;
    }

    public Player getJoueur() {
        return joueur;
    }

    public TilePane getTileMap() {
        return tileMap;
    }

    public void utiliser(int x, int y){
        Taper taper = new Taper();
        //taper.infligerDegats();
    }

}