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
import universite_paris8.iut.youadah.projet.modele.Inventaire;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.MapVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;
import universite_paris8.iut.youadah.projet.vue.*;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Pane overlayRouge;
    @FXML
    private Pane ath;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private final GaussianBlur effetFlou = new GaussianBlur(10);

    private Map carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private CoeurVue coeurVueArmure;
    private ClavierController clavierController;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;

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
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE,inventaire);
        joueurVue = new PlayerVue(joueur,ath);

        // Initialisation des barres de vie
        coeurVue = new CoeurVue(joueur.getPv(),false, ath);
        coeurVue.mettreAJourPv(joueur.getPv());

        coeurVueArmure = new CoeurVue(joueur.getPvArmure(),true, ath);
        coeurVueArmure.mettreAJourPv(joueur.getPvArmure());
        coeurVueArmure.getBarreVie().setLayoutY(40); // Décalage vers le bas

        // Ajout à la couche graphique
        playerLayer.getChildren().add(coeurVueArmure.getBarreVie());
        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());

        GestionEffetDegats.definirSuperposition(overlayRouge);

        // Clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                coeurVueArmure,
                playerLayer,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                carte
        );
        clavierController.configurerControles();

        inventaire = new Inventaire();
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE, inventaire);
        joueurVue = new PlayerVue(joueur, playerLayer);
        coeurVue = new CoeurVue(joueur.getPv(), false,ath);
        coeurVue.mettreAJourPv(joueur.getPv());

        joueurVue.afficherJoueur();
        coeurVue.afficherCoeur();

        Objet pioche = new Objet("pioche", 1);


        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaire.ajouterObjet(pioche);
        inventaireVue.maj();

        Image image = new Image(getClass().getResource("/images/invp.png").toExternalForm());
        ImageView imageView = new ImageView(image);

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
            if (event.getCode() == KeyCode.F1) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((0 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F2) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((1 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F3) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((2 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F4) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((3 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F5) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((4 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F6) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((5 * 64) + 730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }
        });
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));


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
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE,inventaire);
        joueurVue = new PlayerVue(joueur,ath);

        // Nettoyage
        playerLayer.getChildren().removeIf(node -> node == joueurVue.getNode()
                || node == coeurVue.getBarreVie()
                || node == coeurVueArmure.getBarreVie());

        // Recréation des barres
        coeurVue = new CoeurVue(joueur.getPv(),false,ath);
        coeurVue.mettreAJourPv(joueur.getPv());

        coeurVueArmure = new CoeurVue(joueur.getPvArmure(),true,ath);
        coeurVueArmure.mettreAJourPv(joueur.getPvArmure());
        coeurVueArmure.getBarreVie().setLayoutY(40);

        // Ajout des éléments
        playerLayer.getChildren().add(coeurVueArmure.getBarreVie());
        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());

        // Réinitialiser affichage
        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);

        // Reconfigurer clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                coeurVueArmure,
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
