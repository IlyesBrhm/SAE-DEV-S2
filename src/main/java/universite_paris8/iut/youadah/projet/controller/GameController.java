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
import universite_paris8.iut.youadah.projet.modele.Armes.*;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.vue.*;

import java.net.URL;
import java.util.*;

/**
 * Contrôleur principal du jeu.
 * Cette classe gère l'initialisation du jeu, les interactions utilisateur,
 * la logique de jeu et la boucle de jeu principale.
 * Elle coordonne les différents éléments du jeu comme le joueur, les ennemis,
 * l'inventaire, le crafting et les interactions avec l'environnement.
 */
public class GameController implements Initializable {

    // === RÉFÉRENCES FXML : éléments définis dans le fichier .fxml ===
    @FXML private TilePane tileMap;                 // Conteneur de la carte du monde (structure de tuiles)
    @FXML private Pane playerLayer;                // Couche sur laquelle sont affichés le joueur et les éléments mobiles
    @FXML private Label messageMort;               // Message affiché à la mort du joueur
    @FXML private Button boutonQuitter;            // Bouton pour quitter le jeu après la mort
    @FXML private Button boutonReapparaitre;       // Bouton pour réapparaître après la mort
    @FXML private Pane overlayRouge;               // Superposition rouge lors des dégâts
    @FXML private Pane ath;                        // Interface (HUD) contenant les barres de vie, inventaire, etc.

    // === CONSTANTES DE JEU ===
    private static final int TAILLE_TUILE = 32;     // Taille d'une tuile en pixels
    private static final int NB_COLONNES = 58;      // Largeur de la carte en nombre de tuiles

    // === VARIABLES DU CONTROLLER ===
    private final GaussianBlur effetFlou = new GaussianBlur(10);       // Effet visuel pour mort
    private final Set<KeyCode> touchesAppuyees = new HashSet<>();      // Ensemble des touches pressées

    // === OBJETS DE MODÈLE ET VUES ===
    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private BouclierVue bouclierVue;
    private CoeurVue coeurVueArmure; // Non utilisé ici
    private ClavierController clavierController;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private ObjetAuSol objetAuSol;
    private boolean estMort = false;
    private Ennemie ennemie;
    private EnnemieVue ennemieVue;
    private BarreDeVieVue barreVieEnnemi;

    // === CRAFTING ===
    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane paneCraft;
    private boolean craftVisible = false;

    // === INITIALISATION DU JEU ===
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Génération de la carte
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue(structure);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(tileMap);

        // Création du joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);

        // Création des barres de vie/bouclier
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        // Création d’un ennemi
        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);
        ennemieVue = new EnnemieVue(ennemie);
        playerLayer.getChildren().addAll(ennemieVue.getNode());

        barreVieEnnemi = new BarreDeVieVue(ennemie);
        playerLayer.getChildren().add(barreVieEnnemi.getNode());

        // Objet au sol (exemple : pioche)
        objetAuSol = new ObjetAuSol(5, 19, playerLayer, new Pioche("pioche", 1, carte, carteVue, joueur, null, playerLayer));

        // Inventaire initial
        inventaire = new Inventaire();
        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3));
        inventaire.ajouterObjet(new Epee("Epee", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("Arc", 1, carte, carteVue, joueur, tileMap));

        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        // Ajout des éléments du joueur
        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        // Effet de dégâts
        GestionEffetDegats.definirSuperposition(overlayRouge);

        // Contrôles clavier
        clavierController = new ClavierController(
                touchesAppuyees, joueur, joueurVue, ennemie, ennemieVue,
                coeurVue, bouclierVue, playerLayer, this::mourir,
                GestionEffetDegats::declencherClignotementRouge, carte
        );
        clavierController.configurerControles();

        // Image de sélection d’objet
        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/inventory selected.png").toExternalForm()));
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);

        // Panneau de craft
        paneCraft = new Pane();
        paneCraft.setVisible(false);
        paneCraft.setLayoutX(300);
        paneCraft.setLayoutY(150);
        paneCraft.setPrefSize(400, 200);
        paneCraft.setStyle("-fx-background-color: rgba(30,30,30,0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(paneCraft);

        // Table de craft avec recettes
        tableCraft = new TableCraft();
        tableCraft.ajouterRecette(new Recette(
                List.of((Bloc) inventaire.getInventaire().get(2), (Bloc) inventaire.getInventaire().get(3)),
                new Potion("potionVie", 5, joueur, "vie")));
        tableCraft.ajouterRecette(new Recette(
                List.of(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3), new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)),
                new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer)));
        tableCraft.ajouterRecette(new Recette(
                List.of(new Bloc("Bois", 1, false, carte, carteVue, joueur, 2), new Bloc("Bois", 1, false, carte, carteVue, joueur, 2), new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)),
                new Arc("Arc", 1, carte, carteVue, joueur, playerLayer)));
        tableCraftVue = new TableCraftVue(paneCraft, tableCraft, inventaire, inventaireVue, ath);

        // Écoute des touches clavier
        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            switch (event.getCode()) {
                case E -> { // Ramasser objet
                    if (objetAuSol.ramasser(joueur, inventaire, playerLayer)) {
                        inventaireVue.maj();
                    }
                }
                case A -> { // Déposer objet
                    Objet obj = joueur.getObjetPossede();
                    if (obj != null) {
                        int q = obj.getQuantite();
                        obj.setQuantite(1);
                        objetAuSol.deposerJoueur(obj, joueur, playerLayer);
                        obj.setQuantite(q - 1);
                        if (q <= 1) {
                            inventaire.getInventaire().remove(obj);
                            joueur.setObjetPossede(null);
                        }
                        joueurVue.mettreAJourJoueur(joueur);
                        ath.getChildren().clear();
                        inventaireVue.afficherInventaire();
                        inventaireVue.maj();
                    }
                }
                case C -> { // Ouvrir / fermer le craft
                    craftVisible = !craftVisible;
                    if (!ath.getChildren().contains(paneCraft)) ath.getChildren().add(paneCraft);
                    paneCraft.setVisible(craftVisible);
                    if (craftVisible) tableCraftVue.afficher();
                }
                case F1, F2, F3, F4, F5, F6 -> { // Sélection rapide
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

        // Souris = attaque / interaction
        ath.setOnMouseClicked(event -> {
            double cibleX = event.getX();
            double cibleY = event.getY();
            Objet objetUtilise = joueur.getObjetPossede();

            // Attaque à l’épée
            new Taper().attaquerAvecEpee(joueur, List.of(ennemie), overlayRouge);
            barreVieEnnemi.mettreAJourPv(ennemie.getPv());

            if (ennemie.estMort()) {
                playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
            }

            if (objetUtilise != null) {
                if (objetUtilise instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else {
                    objetUtilise.utiliser((int)(cibleX / TAILLE_TUILE), (int)(cibleY / TAILLE_TUILE));
                    if (objetUtilise instanceof Bloc || objetUtilise.getConsomable()) {
                        objetUtilise.decrementerQuantite(1);
                        if (objetUtilise.getQuantite() <= 0) {
                            inventaire.getInventaire().remove(objetUtilise);
                            joueur.setObjetPossede(null);
                        }
                    }
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                ath.getChildren().clear();
                inventaireVue.afficherInventaire();
                inventaireVue.maj();
            }
        });

        // Touche relâchée
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        // Boucle de jeu
        new AnimationTimer() {
            private long dernierCoup = 0;
            private final long delaiEntreCoups = 1_000_000_000;

            @Override
            public void handle(long now) {
                if (!estMort) {
                    clavierController.gererTouches();

                    // Si le joueur est proche de l'ennemi
                    double distance = Math.hypot(joueur.getX() - ennemie.getX(), joueur.getY() - ennemie.getY());
                    if (!ennemie.estMort() && distance < 32 && now - dernierCoup > delaiEntreCoups) {
                        ennemie.attaque(carte);
                        coeurVue.mettreAJourPv(joueur.getPv());
                        bouclierVue.mettreAJourPv(joueur.getPvArmure());
                        dernierCoup = now;
                    }

                    barreVieEnnemi.mettreAJourPv(ennemie.getPv());
                    if (ennemie.estMort()) {
                        playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
                    }
                }
            }
        }.start();
    }

    // Gère la mort du joueur
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

    // Gère le tir à l'arc
    private void tirerFleche(double cibleX, double cibleY, Pane couche) {
        Fleche fleche = new Fleche(joueur.getX(), joueur.getY(), cibleX, cibleY, List.of(ennemie), overlayRouge, 2, carte);
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }

    // Respawn du joueur
    @FXML
    private void reapparaitre() {
        estMort = false;

        // Nouveau joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        // Réinitialisation de l'inventaire
        inventaire.getInventaire().clear();
        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3));
        inventaire.ajouterObjet(new Epee("Epee", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("Arc", 1, carte, carteVue, joueur, tileMap));

        ath.getChildren().clear();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        playerLayer.getChildren().setAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie(),
                ennemieVue.getNode(),
                barreVieEnnemi.getNode()
        );

        clavierController.setJoueur(joueur);
        clavierController.setJoueurVue(joueurVue);
        clavierController.setCoeurVue(coeurVue);
        clavierController.setBouclierVue(bouclierVue);

        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);
    }

    // Quitter le jeu
    @FXML
    private void quitterJeu() {
        System.exit(0);
    }
}
