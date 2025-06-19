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
    Player joueur;
    TilePane tileMap;

    public Epee(String nom, int rarete, GameMap carte, Player joueur, TilePane tileMap) {
        super(nom, 1, false);
        this.degats = calculerDegatsSelonRarete(rarete);

        this.carte = carte;
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


    public Player getJoueur() {
        return joueur;
    }

    @Override
    public void utiliser(int x, int y, MapVue carteVue) {
        Taper taper = new Taper();
        //taper.infligerDegats();
    }

}