package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Arc extends Objet {

    private int degats;
    private int portee;

    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private TilePane tileMap;

    public Arc(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, TilePane tileMap) {
        super(nom, 1, false);
        this.degats = calculerDegatsSelonRarete(rarete);
        this.portee = 150 + rarete * 20; // une portée plus grande selon la rareté

        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.tileMap = tileMap;
    }

    private int calculerDegatsSelonRarete(int rarete) {
        return Math.max(1, rarete);  // minimum 1 dégât
    }

    public int getDegats() {
        return degats;
    }

    public int getPortee() {
        return portee;
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

    public void tirerFleche(double cibleX, double cibleY, Pane couche) {
        // On prend la position du joueur (en pixels)
        double departX = joueur.getX();
        double departY = joueur.getY();

        Fleche fleche = new Fleche(departX, departY, cibleX, cibleY);
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }


    @Override
    public void utiliser(int x, int y) {
        Tirer tirer = new Tirer();
        //tirer.attaquerAvecArc(joueur, GameMap.getListeJoueurs(), carteVue.getOverlayPane(), this);
    }
}
