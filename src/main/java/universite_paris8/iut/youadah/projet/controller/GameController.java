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
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;
import universite_paris8.iut.youadah.projet.vue.vie.BouclierVue;
import universite_paris8.iut.youadah.projet.vue.vie.CoeurVue;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
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

    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private BouclierVue bouclierVue;
    private CoeurVue coeurVueArmure;
    private ClavierController clavierController;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private boolean estMort = false;

    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane paneCraft;
    private boolean craftVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisation carte
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(TAILLE_TUILE, NB_COLONNES);
        carteVue = new MapVue();
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(structure, tileMap);

        // Inventaire & joueur
        inventaire = new Inventaire();
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE, inventaire);
        joueurVue = new PlayerVue(joueur, ath);

        // Vues vie & armure
        coeurVue = new CoeurVue(joueur.getPv(), ath);
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);
        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        // Ajout objets dans inventaire
        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, tileMap, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, carteVue, joueur, tileMap, 3));
        inventaire.ajouterObjet(new Epee("epee", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("arc", 1, carte, carteVue, joueur, tileMap));

        // Vue inventaire
        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        // Ajout éléments au playerLayer
        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        // Effet overlay rouge dégâts
        GestionEffetDegats.definirSuperposition(overlayRouge);

        // Contrôles clavier
        clavierController = new ClavierController(
                touchesAppuyees,
                joueur,
                joueurVue,
                coeurVue,
                bouclierVue,
                playerLayer,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                carte
        );
        clavierController.configurerControles();

        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);

        // Pane craft
        paneCraft = new Pane();
        paneCraft.setVisible(false);
        paneCraft.setLayoutX(300);
        paneCraft.setLayoutY(150);
        paneCraft.setPrefSize(400, 200);
        paneCraft.setStyle("-fx-background-color: rgba(30, 30, 30, 0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(paneCraft);

        tableCraft = new TableCraft();
        Bloc b1 = (Bloc) inventaire.getInventaire().get(2);
        Bloc b2 = (Bloc) inventaire.getInventaire().get(3);

        tableCraft.ajouterRecette(new Recette(
                List.of(b1, b2),
                new Potion("potionVie", 5, joueur, "vie")
        ));
        tableCraftVue = new TableCraftVue(paneCraft, tableCraft, inventaire, inventaireVue, ath);

        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();

        // Gestion touches pressées
        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            switch (event.getCode()) {
                case C -> {
                    craftVisible = !craftVisible;
                    paneCraft.setVisible(craftVisible);
                    if (craftVisible) {
                        tableCraftVue.afficher();
                    }
                }
                case ESCAPE -> {
                    craftVisible = false;
                    paneCraft.setVisible(false);
                }
                case F1, F2, F3, F4, F5, F6 -> {
                    int index = event.getCode().ordinal() - KeyCode.F1.ordinal();
                    if (index < inventaire.getInventaire().size()) {
                        joueur.setObjetPossede(inventaire.getInventaire().get(index));
                        imageView.setX((index * 64) + 730);
                        ath.getChildren().remove(imageView);
                        ath.getChildren().add(imageView);
                    }
                }
            }
        });

        // Gestion clic souris sur ath - tir de flèche
        ath.setOnMouseClicked(event -> {
            double cibleX = event.getX();
            double cibleY = event.getY();

            if (joueur.getObjetPossede() != null) {
                // Si objet est consommable
                if (joueur.getObjetPossede().getConsomable()) {
                    // Utilise objet consommable (ex : potion)
                    int x = (int) (cibleX / TAILLE_TUILE);
                    int y = (int) (cibleY / TAILLE_TUILE);
                    joueur.getObjetPossede().utiliser(x, y);

                    inventaire.getInventaire().remove(joueur.getObjetPossede());
                    joueur.setObjetPossede(null);
                    ath.getChildren().clear();
                    inventaireVue.afficherInventaire();
                    inventaireVue.maj();
                    coeurVue.mettreAJourPv(joueur.getPv());
                    return;
                }

                // Sinon, si l'objet permet de tirer une flèche (ex: Arc)
                if (joueur.getObjetPossede() instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else {
                    // Sinon utilise l'objet (ex : épée)
                    int x = (int) (cibleX / TAILLE_TUILE);
                    int y = (int) (cibleY / TAILLE_TUILE);
                    joueur.getObjetPossede().utiliser(x, y);
                }
            }
        });

        // Gestion relâchement touches
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        // Boucle animation principale
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!estMort) clavierController.gererTouches();
            }
        }.start();
    }

    private void tirerFleche(double cibleX, double cibleY, Pane couche) {
        double departX = joueur.getX();
        double departY = joueur.getY();

        Fleche fleche = new Fleche(departX, departY, cibleX, cibleY);
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
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
        joueur.reinitialiser(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue.remettrePersoDeBase(joueur);
        coeurVue.mettreAJourPv(joueur.getPv());
        if (coeurVueArmure != null)
            coeurVueArmure.mettreAJourPv(joueur.getPvArmure());
        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);
    }

    @FXML
    private void quitterJeu() {
        System.exit(0);
    }
}
