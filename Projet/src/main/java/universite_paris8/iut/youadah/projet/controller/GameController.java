package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import  universite_paris8.iut.youadah.projet.vue.MapVue;

public class GameController implements Initializable {

    @FXML
    private TilePane tileMap;

    @FXML
    private Pane playerLayer;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private Map carte;
    private MapVue  carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;

    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue= new MapVue();
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);

        carteVue.afficherCarte(structure, tileMap);



        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());

        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());


        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            if (event.getCode() == KeyCode.K) {
                joueur.decrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());
            }

            if (event.getCode() == KeyCode.G) {
                joueur.incrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());
            }
        });
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
                    joueur.deplacerGauche(carte);
                }
                if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
                    joueur.deplacerDroite(carte);
                }
                if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
                    joueur.sauter();
                }

                joueur.mettreAJour(carte);
                joueurVue.mettreAJourJoeur(joueur);



            }
        }.start();
    }
}