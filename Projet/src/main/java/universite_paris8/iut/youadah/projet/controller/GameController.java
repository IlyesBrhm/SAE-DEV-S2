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


    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
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
        inventaire.ajouterObjet(new Epee("Epee", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("Arc", 1, carte, carteVue, joueur, tileMap));

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

        tableCraft = new TableCraft();
        Bloc b1 = (Bloc) inventaire.getInventaire().get(2);
        Bloc b2 = (Bloc) inventaire.getInventaire().get(3);

        tableCraft.ajouterRecette(new Recette(
                List.of(b1, b2),
                new Potion("potionVie", 5, joueur, "vie")
        ));
        // Recette pour créer une pioche à partir de 2 pierres
        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3),
                        new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)
                ),
                new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer)
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new Bloc("Bois", 1, false, carte, carteVue, joueur, 2),
                        new Bloc("Bois", 1, false, carte, carteVue, joueur, 2),
                        new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)
                ),
                new Arc("Arc", 1, carte, carteVue, joueur, playerLayer)
        ));



        tableCraftVue = new TableCraftVue(paneCraft, tableCraft, inventaire, inventaireVue, ath);


        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());


            switch (event.getCode()) {
                case E -> {
                    boolean ramasse = objetAuSol.ramasser(joueur, inventaire, playerLayer);
                    if (ramasse) {
                        inventaireVue.maj();
                    } else {
                        System.out.println("Impossible de ramasser l'objet : inventaire plein.");
                    }
                }


                case A -> {
                    Objet objetADeposer = joueur.getObjetPossede();
                    if (objetADeposer != null) {
                        int quantiteOriginale = objetADeposer.getQuantite();

                        objetADeposer.setQuantite(1); // pour déposer 1 visuellement
                        objetAuSol.deposerJoueur(objetADeposer, joueur, playerLayer);

                        if (quantiteOriginale > 1) {
                            objetADeposer.setQuantite(quantiteOriginale - 1);
                        } else {
                            inventaire.getInventaire().remove(objetADeposer);

                            // ✅ Tenter de retrouver un autre objet identique pour le reselectionner
                            for (Objet o : inventaire.getInventaire()) {
                                if (o.getNom().equals(objetADeposer.getNom())) {
                                    joueur.setObjetPossede(o);
                                    break;
                                }
                            }

                            // ❌ Si aucun n’a été trouvé, on le désélectionne
                            if (!inventaire.getInventaire().contains(objetADeposer)) {
                                joueur.setObjetPossede(null);
                            }
                        }

                        joueurVue.mettreAJourJoueur(joueur);


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
                case C -> {
                    craftVisible = !craftVisible;

                    if (!ath.getChildren().contains(paneCraft)) {
                        ath.getChildren().add(paneCraft);
                    }

                    paneCraft.setVisible(craftVisible);
                    if (craftVisible) {
                        System.out.println("→ Affichage table de craft");
                        tableCraftVue.afficher();
                    }
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
            int z = (int) (event.getX() / 32);
            int e = (int) (event.getY() / 32);
            double cibleX = event.getX();
            double cibleY = event.getY();

            Objet objetUtilise = joueur.getObjetPossede();
            if (objetUtilise != null) {
                // Utilisation principale de l’objet (pose bloc, boire potion, etc.)
                objetUtilise.utiliser(z, e);

                // Si l'objet est un bloc ou un consommable, on décrémente
                if (objetUtilise instanceof Bloc || objetUtilise.getConsomable()) {
                    if (objetUtilise.getQuantite() > 1) {
                        objetUtilise.decrementerQuantite(1);
                    } else {
                        inventaire.getInventaire().remove(objetUtilise);
                        joueur.setObjetPossede(null);
                    }
                }

                // Mise à jour de l'objet après décrémentation
                Objet objetApresMaj = joueur.getObjetPossede();
                if (objetApresMaj instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else if (objetApresMaj != null && !(objetApresMaj instanceof Bloc || objetApresMaj.getConsomable())) {
                    // Ex : Épée ou autre arme utilisable
                    int x = (int) (cibleX / TAILLE_TUILE);
                    int y = (int) (cibleY / TAILLE_TUILE);
                    objetApresMaj.utiliser(x, y);
                }

                // Rafraîchir l'ATH et les PV
                coeurVue.mettreAJourPv(joueur.getPv());
                ath.getChildren().clear();
                inventaireVue.afficherInventaire();
                inventaireVue.maj();

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
    private void tirerFleche(double cibleX, double cibleY, Pane couche) {
        double departX = joueur.getX();
        double departY = joueur.getY();

        Fleche fleche = new Fleche(departX, departY, cibleX, cibleY);
        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
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