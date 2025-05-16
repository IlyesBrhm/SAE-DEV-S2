package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static universite_paris8.iut.youadah.projet.vue.MapRenderer.chargerImageTuile;

public class GameController implements Initializable {

    @FXML
    private GridPane tileMap;

    @FXML
    private Pane playerLayer;

    private static final int TAILLE_TUILE = 32;

    private Map carte;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;

    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, 58);

        // Affiche les tuiles dans le GridPane
        for (int y = 0; y < structure.length; y++) {
            for (int x = 0; x < structure[y].length; x++) {
                ImageView tuile = new ImageView(chargerImageTuile(structure[y][x]));
                tuile.setFitWidth(TAILLE_TUILE);
                tuile.setFitHeight(TAILLE_TUILE);
                tileMap.add(tuile, x, y);
            }
        }

        // Initialisation du joueur
        joueur = new Player(5 * TAILLE_TUILE, 10 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());
        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());

        // Contrôle clavier
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

        // Boucle d’animation
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
                    joueur.deplacerGauche(carte);
                }
                if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
                    joueur.deplacerDroite(carte);
                }
                if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE) || touchesAppuyees.contains(KeyCode.UP)) {
                    joueur.sauter();
                }

                joueur.mettreAJour(carte);
                joueurVue.mettreAJour(joueur);
            }
        }.start();
    }
}