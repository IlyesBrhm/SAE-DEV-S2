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
    private ClavierController clavierController;

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

        // Initialisation du joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());

        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());

        // Hitbox rouge
        initialiserBoxDegat();

        // Clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                playerLayer,
                this::mourir,
                this::afficherBoxDegat,
                carte // 💡 on passe bien la carte
        );
        clavierController.configurerControles();

        // Boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (estMort) return;
                clavierController.gererTouches();
            }
        }.start();
    }

    private void initialiserBoxDegat() {
        boxDegat = new Rectangle(64, 64);
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
        }
    }
}
