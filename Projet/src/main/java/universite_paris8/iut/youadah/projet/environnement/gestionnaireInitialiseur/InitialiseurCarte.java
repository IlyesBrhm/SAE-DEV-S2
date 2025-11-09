package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class InitialiseurCarte {

    private static final int TAILLE_TUILE = 32;

    private GameMap carte;
    private MapVue carteVue;
    private final int hauteur;
    private final int largeur;

    public InitialiseurCarte(int hauteur, int largeur) {
        this.hauteur = hauteur;
        this.largeur = largeur;
    }

    public void initialiser(TilePane tileMap) {
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(hauteur, largeur);
        carteVue = new MapVue(structure);

        tileMap.setMaxWidth(TAILLE_TUILE * largeur);
        tileMap.setMinWidth(TAILLE_TUILE * largeur);
        carteVue.afficherCarte(tileMap);
    }

    public GameMap getCarte() {
        return carte;
    }

    public MapVue getCarteVue() {
        return carteVue;
    }
}