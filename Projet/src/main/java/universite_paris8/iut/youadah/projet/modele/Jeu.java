package universite_paris8.iut.youadah.projet.modele;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.controller.ClavierController;
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.modele.Armes.*;
import universite_paris8.iut.youadah.projet.modele.actions.Taper;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Jeu {
    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private final GaussianBlur effetFlou = new GaussianBlur(10);
    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    private GameMap carte;
    private Player joueur;
    private Inventaire inventaire;
    private ObjetAuSol objetAuSol;
    private boolean estMort = false;
    private Ennemie ennemie;

    private TableCraft tableCraft;
    private Pane paneCraft;
    private boolean craftVisible = false;


    private TilePane tileMap;
    private Pane playerLayer;
    private Label messageMort;
    private Button boutonQuitter;
    private Button boutonReapparaitre;
    private Pane overlayRouge;
    private Pane ath;

    public Jeu(TilePane tileMap, Pane playerLayer, Pane overlayRouge, Pane ath, Label messageMort, Button boutonQuitter, Button boutonReapparaitre) {
        this.tileMap = tileMap;
        this.playerLayer = playerLayer;
        this.overlayRouge = overlayRouge;
        this.ath = ath;
        this.messageMort = messageMort;
        this.boutonQuitter = boutonQuitter;
        this.boutonReapparaitre = boutonReapparaitre;

    }

    public void initialiserJeu(){
        carte = new GameMap();
        carte.creerTerrain(TAILLE_TUILE, NB_COLONNES);

        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);

        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);

        objetAuSol = new ObjetAuSol(5, 19, playerLayer, new Pioche("pioche", 1, carte, joueur, null, playerLayer));

        inventaire = new Inventaire();

        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, joueur, objetAuSol, playerLayer));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, joueur, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, joueur, 3));
        inventaire.ajouterObjet(new Epee("Epee", 1, carte, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("Arc", 1, carte, joueur));

        GestionEffetDegats.definirSuperposition(overlayRouge);

        paneCraft = new Pane();
        paneCraft.setVisible(false);
        paneCraft.setLayoutX(300);
        paneCraft.setLayoutY(150);
        paneCraft.setPrefSize(400, 200);
        paneCraft.setStyle("-fx-background-color: rgba(30,30,30,0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(paneCraft);

        tableCraft = new TableCraft();
        tableCraft.ajouterRecette(new Recette(
                List.of(
                    new Bloc("Terre", 1, false, carte, joueur, 3),
                    new Bloc("Pierre", 1, false, carte, joueur, 3)
                ),
                new Potion("potionVie", 5, joueur, "vie")
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new Bloc("Pierre", 1, false, carte, joueur, 3),
                        new Bloc("Pierre", 1, false, carte, joueur, 3)
                ),
                new Pioche("pioche", 1, carte, joueur, objetAuSol, playerLayer)
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new Bloc("Bois", 1, false, carte, joueur, 2),
                        new Bloc("Bois", 1, false, carte, joueur, 2),
                        new Bloc("Pierre", 1, false, carte, joueur, 3)
                ),
                new Arc("Arc", 1, carte, joueur)
        ));


        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();





        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));
    }

    public Pane getPaneCraft() {
        return paneCraft;
    }

    public TableCraft getTableCraft() {
        return tableCraft;
    }


    public Player getJoueur() {
        return joueur;
    }

    public Ennemie getEnnemie() {
        return ennemie;
    }

    public GameMap getCarte() {
        return carte;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public ObjetAuSol getObjetAuSol() {
        return objetAuSol;
    }
}
