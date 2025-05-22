package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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

    @FXML
    private Button boutonQuitter;

    @FXML
    private Button boutonReapparaitre;

    @FXML
    private Pane overlayRouge; // récupéré depuis le FXML

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private final GaussianBlur effetFlou = new GaussianBlur(10);

    private Map carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private ClavierController clavierController;

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

        // Liaison du pane overlayRouge déjà présent dans le FXML
        GestionEffetDegats.definirSuperposition(overlayRouge);

        // Clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                playerLayer,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                carte
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

    private void mourir() {
        if (!estMort) {
            estMort = true;
            messageMort.setVisible(true);
            boutonQuitter.setVisible(true);
            boutonReapparaitre.setVisible(true);
            joueurVue.getNode().setVisible(false);

            tileMap.setEffect(effetFlou);
            playerLayer.setEffect(effetFlou);
        }
    }


    @FXML
    private void reapparaitre() {
        estMort = false;

        // Créer un nouveau joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);

        // Retirer ancien joueur s'il existe
        playerLayer.getChildren().removeIf(node -> node == joueurVue.getNode());

        // Retirer ancien cœur s'il existe
        if (coeurVue != null && coeurVue.getBarreVie().getParent() == playerLayer) {
            playerLayer.getChildren().remove(coeurVue.getBarreVie());
        }

        // Créer et ajouter nouvelle barre de vie
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());

        // Ajouter les éléments au bon ordre (overlayRouge est déjà présent)
        playerLayer.getChildren().add(0, joueurVue.getNode());
        playerLayer.getChildren().add(1, coeurVue.getBarreVie());

        // Réinitialiser affichages
        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);

        // Supprimer les effets visuels de mort
        tileMap.setEffect(null);
        playerLayer.setEffect(null);

        // Reconfigurer le contrôleur clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                playerLayer,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                carte
        );
        clavierController.configurerControles();
    }

    @FXML
    private void quitterJeu() {
        System.exit(0);
    }
}
