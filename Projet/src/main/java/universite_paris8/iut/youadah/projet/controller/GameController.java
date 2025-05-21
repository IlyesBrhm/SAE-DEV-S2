package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.MapVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController implements Initializable {

    @FXML
    private TilePane tileMap;

    @FXML
    private Pane playerLayer;

    @FXML
    private Label messageMort;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private Map carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;

    private Rectangle boxDegat;
    private final Set<KeyCode> touchesAppuyees = new HashSet<>();
    private boolean estMort = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation de la carte
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue();
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(structure, tileMap);

        // Initialisation du joueur et de sa vue
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());

        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());

        // Initialisation de la hitbox rouge
        initialiserBoxDegat();

        // Gestion des touches
        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            if (event.getCode() == KeyCode.K && !estMort) {
                joueur.decrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());

                afficherBoxDegat();

                if (joueur.getPv() <= 0) {
                    mourir();
                }
            }

            if (event.getCode() == KeyCode.G && !estMort) {
                joueur.incrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());
            }
        });

        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        // Boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (estMort) return;

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

    private void initialiserBoxDegat() {
        boxDegat = new Rectangle(32, 32); // adapte si ton sprite a une autre taille
        boxDegat.setFill(Color.TRANSPARENT);
        boxDegat.setStroke(Color.RED);
        boxDegat.setStrokeWidth(2);
        boxDegat.setVisible(false);
        playerLayer.getChildren().add(boxDegat);
    }

    private void afficherBoxDegat() {
        boxDegat.setLayoutX(joueur.getX());
        boxDegat.setLayoutY(joueur.getY());
        boxDegat.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            javafx.application.Platform.runLater(() -> boxDegat.setVisible(false));
        }).start();
    }

    private void mourir() {
        if (!estMort) {
            estMort = true;
            System.out.println("💀 Le joueur est mort !");
            messageMort.setVisible(true);
            joueurVue.getNode().setVisible(false);

            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                javafx.application.Platform.exit();
            }).start();
        }
    }
}
