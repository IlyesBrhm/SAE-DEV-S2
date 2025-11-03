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
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private ObjetAuSol objetAuSol;
    private boolean estMort = false;
    private Ennemie ennemie;
    private EnnemieVue ennemieVue;
    private BarreDeVieVue barreVieEnnemi;
    private Environnement environnement;
    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane paneCraft;
    private boolean craftVisible = false;
    private ImageView selectionInventaire;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserJoueur();
        initialiserEnnemi();
        initialiserEnvironnement();
        initialiserInventaire();
        initialiserCraft();
        initialiserInterface();
        configurerGestionnairesEvenements();
        demarrerBoucleJeu();
    }

    private void initialiserCarte() {
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue(structure);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(tileMap);
    }

    private void initialiserEnvironnement() {
        environnement = new Environnement(
                playerLayer, carte, joueur, ennemie, joueurVue, ennemieVue,
                barreVieEnnemi, coeurVue, bouclierVue, overlayRouge,
                this::mourir, GestionEffetDegats::declencherClignotementRouge
        );
    }

    private void initialiserJoueur() {
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueur.setCarte(carte);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPv());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );
    }

    private void initialiserEnnemi() {
        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);
        ennemie.setCarte(carte);
        ennemieVue = new EnnemieVue(ennemie);
        barreVieEnnemi = new BarreDeVieVue(ennemie);

        playerLayer.getChildren().addAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
    }

    private void initialiserInventaire() {
        inventaire = new Inventaire();
        ajouterObjetsInitiauxInventaire();

        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        creerObjetsAuSol();
        creerSelectionInventaire();
    }

    private void ajouterObjetsInitiauxInventaire() {
        inventaire.ajouterObjet(new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, null, playerLayer)));
        inventaire.ajouterObjet(new CaseInventaire(new Potion("potionVie", 1, joueur, "vie")));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2)));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)));
        inventaire.ajouterObjet(new CaseInventaire(new Epee("Epee", 1, carte, carteVue, joueur, tileMap)));
        inventaire.ajouterObjet(new CaseInventaire(new Arc("Arc", 1, carte, carteVue, joueur, tileMap)));
    }

    private void creerObjetsAuSol() {
        objetAuSol = new ObjetAuSol(5, 19, playerLayer, new Pioche("pioche", 1, carte, carteVue, joueur, null, playerLayer));
        new ObjetAuSol(5, 21, playerLayer, new Arc("Arc", 1, carte, carteVue, joueur, tileMap));
    }

    private void creerSelectionInventaire() {
        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        selectionInventaire = new ImageView(image);
        selectionInventaire.setFitHeight(64);
        selectionInventaire.setFitWidth(64);
    }

    private void initialiserCraft() {
        paneCraft = new Pane();
        paneCraft.setVisible(false);
        paneCraft.setLayoutX(300);
        paneCraft.setLayoutY(150);
        paneCraft.setPrefSize(400, 200);
        paneCraft.setStyle("-fx-background-color: rgba(30,30,30,0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(paneCraft);

        tableCraft = new TableCraft();
        ajouterRecettesCraft();

        tableCraftVue = new TableCraftVue(paneCraft, tableCraft, inventaire, inventaireVue, ath);
    }

    private void ajouterRecettesCraft() {
        // Recette Potion
        tableCraft.ajouterRecette(new Recette(
                List.of(inventaire.getInventaire().get(2), inventaire.getInventaire().get(3)),
                new CaseInventaire(new Potion("potionVie", 5, joueur, "vie"))
        ));

        // Recette Pioche
        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)),
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer))
        ));

        // Recette Arc
        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Bois", 1, false, carte, carteVue, joueur, 2)),
                        new CaseInventaire(new Bloc("Bois", 1, false, carte, carteVue, joueur, 2)),
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Arc("Arc", 1, carte, carteVue, joueur, playerLayer))
        ));
    }

    private void initialiserInterface() {
        GestionEffetDegats.definirSuperposition(overlayRouge);
        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();
    }

    private void configurerGestionnairesEvenements() {
        playerLayer.setOnKeyPressed(this::gererAppuiTouche);
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));
        ath.setOnMouseClicked(this::gererClicSouris);
    }

    private void gererAppuiTouche(javafx.scene.input.KeyEvent event) {
        touchesAppuyees.add(event.getCode());


        switch (event.getCode()) {
            case E -> ramasserObjet();
            case A -> deposerObjet();
            case C -> basculerCraft();
            case F1, F2, F3, F4, F5, F6 -> selectionnerObjetInventaire(event.getCode());
        }
    }

    private void ramasserObjet() {
        if (joueur.ramasser(environnement, inventaire)) {
            inventaireVue.maj();
        }
    }

    private void deposerObjet() {
        if (joueur.getObjetPossede() == null) return;

        CaseInventaire caseReelle = inventaire.trouverCase(joueur.getObjetPossede());
        if (caseReelle == null) return;

        joueur.deposerObjetEnMain(environnement);
        caseReelle.decrementerQuantite(1);

        if (caseReelle.estVide()) {
            inventaire.getInventaire().remove(caseReelle);
            joueur.setObjetPossede(null);
        }

        joueurVue.mettreAJourJoueur(joueur);
        rafraichirInventaire();
    }

    private void basculerCraft() {
        craftVisible = !craftVisible;
        paneCraft.setVisible(craftVisible);
        if (craftVisible) {
            tableCraftVue.afficher();
        }
    }

    private void selectionnerObjetInventaire(KeyCode code) {
        int index = code.ordinal() - KeyCode.F1.ordinal();
        if (index < inventaire.getInventaire().size()) {
            joueur.setObjetPossede(inventaire.getInventaire().get(index).getObjet());
            selectionInventaire.setX((index * 64) + 730);
            ath.getChildren().remove(selectionInventaire);
            ath.getChildren().add(selectionInventaire);
        }
    }

    private void gererClicSouris(javafx.scene.input.MouseEvent event) {
        double cibleX = event.getX();
        double cibleY = event.getY();

        attaquerAvecEpee();

        // Trouve directement la vraie case au lieu d'en créer une nouvelle
        if (joueur.getObjetPossede() != null) {
            CaseInventaire caseReelle = inventaire.trouverCase(joueur.getObjetPossede());

            if (caseReelle != null) {
                if (caseReelle.getObjet() instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else {
                    utiliserObjetNormal(caseReelle, cibleX, cibleY);
                }
                rafraichirInventaire();
            }
        }
    }

    private void attaquerAvecEpee() {
        Taper taper = new Taper();
        taper.attaquerAvecEpee(joueur, List.of(ennemie), overlayRouge);
        barreVieEnnemi.mettreAJourPv(ennemie.getVie().getPv());

        if (ennemie.getVie().estMort()) {
            playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
        }
    }



    private void utiliserObjetNormal(CaseInventaire caseUtilise, double cibleX, double cibleY) {
        caseUtilise.getObjet().utiliser((int)(cibleX / TAILLE_TUILE), (int)(cibleY / TAILLE_TUILE));

        // Trouve la vraie case dans l'inventaire
        CaseInventaire caseReelle = inventaire.trouverCase(caseUtilise.getObjet());

        if (caseReelle != null && (caseUtilise.getObjet() instanceof Bloc || caseUtilise.getObjet().getConsomable())) {
            caseReelle.decrementerQuantite(1);

            if (caseReelle.estVide()) {
                inventaire.getInventaire().remove(caseReelle);
                joueur.setObjetPossede(null);
            }
        }

        coeurVue.mettreAJourPv(joueur.getVie().getPv());
    }

    private void rafraichirInventaire() {
        ath.getChildren().clear();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();
    }

    private void gererTouches() {
        boolean moving = false;

        if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
            joueur.allerGauche();
            moving = true;
        }
        if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
            joueur.allerDroite();
            moving = true;
        }
        if (!moving) {
            joueur.immobile();
        }

        // AJOUTE CETTE LIGNE : Déplace le joueur selon sa direction
        joueur.deplacer();

        if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
            joueur.sauter();
        }
    }

    private void demarrerBoucleJeu() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!estMort) {
                    gererTouches();
                    environnement.unTour();
                }
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

    private void tirerFleche(double cibleX, double cibleY, Pane couche) {
        Fleche fleche = new Fleche(
                joueur.getX(), joueur.getY(),
                cibleX, cibleY,
                List.of(ennemie),
                overlayRouge,
                2,
                carte
        );
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }

    @FXML
    private void reapparaitre() {
        estMort = false;

        reinitialiserJoueur();
        reinitialiserInventaire();
        reinitialiserInterface();
    }

    private void reinitialiserJoueur() {
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueur.setCarte(carte);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPv());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);
    }

    private void reinitialiserInventaire() {
        inventaire.getInventaire().clear();
        ajouterObjetsInitiauxInventaire();
        rafraichirInventaire();
    }

    private void reinitialiserInterface() {
        playerLayer.getChildren().setAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie(),
                ennemieVue.getNode(),
                barreVieEnnemi.getNode()
        );

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