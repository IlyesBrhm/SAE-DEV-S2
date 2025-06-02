package universite_paris8.iut.youadah.projet.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.input.KeyCode;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InitialiseurJeu {
    private final TilePane cartePane;
    private final Pane coucheJoueur;
    private final Pane ath;
    private final int nbColonnes;
    private final int tailleTuile;

    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private CoeurVue coeurVueArmure;
    private ClavierController controleurClavier;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane panneauCraft;
    private final Set<KeyCode> touches = new HashSet<>();

    public InitialiseurJeu(TilePane cartePane, Pane coucheJoueur, Pane ath, int nbColonnes, int tailleTuile) {
        this.cartePane = cartePane;
        this.coucheJoueur = coucheJoueur;
        this.ath = ath;
        this.nbColonnes = nbColonnes;
        this.tailleTuile = tailleTuile;
    }

    public void initialiser() {
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(32, nbColonnes);
        carteVue = new MapVue();
        carteVue.afficherCarte(structure, cartePane);

        inventaire = new Inventaire();
        joueur = new Player(5 * tailleTuile, 19 * tailleTuile, inventaire);

        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, carteVue, joueur, cartePane));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, cartePane, 2));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, cartePane, 2));

        joueurVue = new PlayerVue(joueur, ath);
        coeurVue = new CoeurVue(joueur.getPv(), false, ath);
        coeurVueArmure = new CoeurVue(joueur.getPvArmure(), true, ath);
        coeurVueArmure.getBarreVie().setLayoutY(40);

        coeurVue.mettreAJourPv(joueur.getPv());
        coeurVueArmure.mettreAJourPv(joueur.getPvArmure());

        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        coucheJoueur.getChildren().addAll(
                coeurVueArmure.getBarreVie(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        panneauCraft = new Pane();
        panneauCraft.setVisible(false);
        panneauCraft.setLayoutX(300);
        panneauCraft.setLayoutY(150);
        panneauCraft.setPrefSize(400, 200);
        panneauCraft.setStyle("-fx-background-color: rgba(30, 30, 30, 0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(panneauCraft);

        tableCraft = new TableCraft();
        Bloc b1 = (Bloc) inventaire.getInventaire().get(2);
        Bloc b2 = (Bloc) inventaire.getInventaire().get(3);
        tableCraft.ajouterRecette(new Recette(List.of(b1, b2), new Potion("potionVie", 1, joueur, "vie")));
        tableCraftVue = new TableCraftVue(panneauCraft, tableCraft, inventaire, inventaireVue, ath);

        GestionEffetDegats.definirSuperposition((Pane) ath.lookup("#overlayRouge"));

        controleurClavier = new ClavierController(
                touches,
                joueur,
                joueurVue,
                coeurVue,
                coeurVueArmure,
                coucheJoueur,
                () -> {},
                GestionEffetDegats::declencherClignotementRouge,
                carte,
                inventaire,
                inventaireVue,
                ath,
                tableCraftVue
        );
        controleurClavier.configurerControles();
    }

    public Player getJoueur() { return joueur; }
    public PlayerVue getJoueurVue() { return joueurVue; }
    public CoeurVue getBarreVie() { return coeurVue; }
    public CoeurVue getBarreArmure() { return coeurVueArmure; }
    public ClavierController getControleurClavier() { return controleurClavier; }
    public Set<KeyCode> getTouches() { return touches; }
    public GameMap getCarte() { return carte; }
    public Inventaire getInventaire() { return inventaire; }
    public InventaireVue getInventaireVue() { return inventaireVue; }
    public TableCraftVue getTableCraftVue() { return tableCraftVue; }
}