package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.Inventaire;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.*;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController implements Initializable {

    @FXML private TilePane tileMap;
    @FXML private Pane playerLayer;
    @FXML private Label messageMort;
    @FXML private Button boutonQuitter;
    @FXML private Button boutonReapparaitre;
    @FXML private Pane overlayRouge;
    @FXML private Pane ath;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private final GaussianBlur effetFlou = new GaussianBlur(10);
    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    private Map carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private CoeurVue coeurVueArmure;
    private ClavierController clavierController;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private boolean estMort = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // carte
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue();
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(structure, tileMap);

        // inventaire
        inventaire = new Inventaire();
        inventaire.ajouterObjet(new Objet("pioche", 1)); // Ajoute un objet pour test
        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        // joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE, inventaire);
        joueurVue = new PlayerVue(joueur, ath);
        coeurVue = new CoeurVue(joueur.getPv(), false, ath);
        coeurVueArmure = new CoeurVue(joueur.getPvArmure(), true, ath);
        coeurVueArmure.getBarreVie().setLayoutY(40);

        coeurVue.mettreAJourPv(joueur.getPv());
        coeurVueArmure.mettreAJourPv(joueur.getPvArmure());

        // ajout des éléments visuels
        playerLayer.getChildren().addAll(
                coeurVueArmure.getBarreVie(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        // effets
        GestionEffetDegats.definirSuperposition(overlayRouge);

        // contrôles clavier
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

        // affichage de l'objet équipé
        Image image = new Image(getClass().getResource("/images/invp.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);

        // gestion clavier
        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            switch (event.getCode()) {
                case K -> {
                    joueur.decrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                case G -> {
                    joueur.incrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                case F1 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(0));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((0 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }

                case F2 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(1));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((1 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }

                case F3 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(2));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(2).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((2 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }

                case F4 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(3));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(3).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((3 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }

                case F5 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(4));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(4).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((4 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }

                case F6 -> {
                    joueur.setObjetPossede(inventaire.getInventaire().get(5));
                    System.out.println("objet posséder " + inventaire.getInventaire().get(5).getNom());

                    imageView.setFitHeight(64);
                    imageView.setFitWidth(64);
                    imageView.setX((5 * 64) + 730);
                    ath.getChildren().remove(imageView);
                    ath.getChildren().add(imageView);
                }
            }
        });

        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        // boucle de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!estMort) clavierController.gererTouches();
            }
        }.start();
    }

    private void mourir() {
        if (estMort) return;
        estMort = true;
        messageMort.setVisible(true);
        boutonQuitter.setVisible(true);
        boutonReapparaitre.setVisible(true);
        joueurVue.getNode().setVisible(false);

        tileMap.setEffect(effetFlou);
        playerLayer.setEffect(effetFlou);
    }

    @FXML
    private void reapparaitre() {
        estMort = false;
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE, inventaire);
        joueurVue = new PlayerVue(joueur, ath);

        coeurVue = new CoeurVue(joueur.getPv(), false, ath);
        coeurVueArmure = new CoeurVue(joueur.getPvArmure(), true, ath);
        coeurVueArmure.getBarreVie().setLayoutY(40);

        playerLayer.getChildren().setAll(
                coeurVueArmure.getBarreVie(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);

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
