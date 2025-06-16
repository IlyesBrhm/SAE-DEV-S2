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
    private ObjetAuSol objetAuSol;
    private boolean estMort = false;


    private Pane paneCraft;
    private boolean craftVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue(structure);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);

        carteVue.afficherCarte( tileMap);
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);


        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        //test objet au sol ramaser
        Objet objet = new Pioche("pioche", 1,carte,carteVue,joueur,objetAuSol, playerLayer);
        ObjetVue objetVue = new ObjetVue(objet);
        objetAuSol = new ObjetAuSol(5,19,playerLayer, objet);


        // inventaire
        inventaire = new Inventaire();
        inventaire.ajouterObjet(new Pioche("pioche", 1,carte, carteVue,joueur, objetAuSol, playerLayer)); // Ajoute un objet pour test
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3));

        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        GestionEffetDegats.definirSuperposition(overlayRouge);

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


        paneCraft = new Pane();
        paneCraft.setVisible(false);
        paneCraft.setLayoutX(300);
        paneCraft.setLayoutY(150);
        paneCraft.setPrefSize(400, 200);
        paneCraft.setStyle("-fx-background-color: rgba(30, 30, 30, 0.85); -fx-border-color: white; -fx-border-width: 2px;");

        paneCraft.setPrefSize(400, 200);
        ath.getChildren().add(paneCraft);



        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());


            switch (event.getCode()) {
                case E -> {
                    objetAuSol.ramasser(joueur, inventaire, playerLayer);
                    inventaireVue.maj();
                }

                case A -> {
                    Objet objetADeposer = joueur.getObjetPossede();

                    if (objetADeposer != null && objetADeposer instanceof Bloc) {
                        // Crée une vraie copie du bloc à poser
                        Bloc blocOriginal = (Bloc) objetADeposer;
                        Bloc blocACopier = new Bloc(
                                blocOriginal.getNom(),
                                blocOriginal.getRarete(),
                                blocOriginal.getConsomable(),
                                carte,
                                carteVue,
                                joueur,
                                blocOriginal.getIdBloc()
                        );
                        blocACopier.setQuantite(1); // facultatif ici

                        ObjetAuSol.deposerJoueur(blocACopier, joueur, playerLayer);

                        blocOriginal.decrementerQuantite(1);
                        if (blocOriginal.getQuantite() <= 0) {
                            inventaire.retirerObjet(blocOriginal);
                            joueur.setObjetPossede(null);
                        }

                        ath.getChildren().clear();
                        inventaireVue.afficherInventaire();
                        inventaireVue.maj();
                    }
                }



                case K -> {
                    joueur.decrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
                }
                case G -> {
                    joueur.incrementerPv(1);
                    coeurVue.mettreAJourPv(joueur.getPv());
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

        ath.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / 32);
            int y = (int) (event.getY() / 32);

            Objet objetClique = joueur.getObjetPossede();

            if (objetClique != null) {
                objetClique.utiliser(x, y); // toujours utiliser l’objet

                // uniquement si c’est un bloc ou un objet consommable → décrémenter
                if (objetClique instanceof Bloc || objetClique.getConsomable()) {
                    objetClique.decrementerQuantite(1);

                    if (objetClique.getQuantite() <= 0) {
                        inventaire.retirerObjet(objetClique);
                        joueur.setObjetPossede(null);
                    }

                    ath.getChildren().clear();
                    inventaireVue.afficherInventaire();
                    inventaireVue.maj();
                }

                coeurVue.mettreAJourPv(joueur.getPv());
            } else {
                System.out.println("non");
            }
        });


        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

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
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);

        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPv(), ath);

        playerLayer.getChildren().setAll(
                coeurVueArmure.getBarreVie(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        inventaire.getInventaire().clear();
        ath.getChildren().clear();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();
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