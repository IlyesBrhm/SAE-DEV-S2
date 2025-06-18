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
    private CoeurVue coeurVueArmure;
    private ClavierController clavierController;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private ObjetAuSol objetAuSol;
    private boolean estMort = false;
    private Ennemie ennemie;
    private EnnemieVue ennemieVue;
    private BarreDeVieVue barreVieEnnemi;

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
        carteVue.afficherCarte(tileMap);

        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);
        ennemieVue = new EnnemieVue(ennemie);
        playerLayer.getChildren().addAll(ennemieVue.getNode());

        barreVieEnnemi = new BarreDeVieVue(ennemie);
        playerLayer.getChildren().add(barreVieEnnemi.getNode());

        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        objetAuSol = new ObjetAuSol(5, 19, playerLayer, new Pioche("pioche", 1, carte, carteVue, joueur, null, playerLayer));

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
                ennemie,
                ennemieVue,
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
        paneCraft.setStyle("-fx-background-color: rgba(30,30,30,0.85); -fx-border-color: white; -fx-border-width: 2px;");
        ath.getChildren().add(paneCraft);

        tableCraft = new TableCraft();
        tableCraft.ajouterRecette(new Recette(List.of(
                (Bloc) inventaire.getInventaire().get(2),
                (Bloc) inventaire.getInventaire().get(3)),
                new Potion("potionVie", 5, joueur, "vie")));

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
                    if (objetAuSol.ramasser(joueur, inventaire, playerLayer)) {
                        inventaireVue.maj();
                    }
                }
                case A -> {
                    Objet obj = joueur.getObjetPossede();
                    if (obj != null) {
                        int q = obj.getQuantite();
                        obj.setQuantite(1);
                        objetAuSol.deposerJoueur(obj, joueur, playerLayer);
                        obj.setQuantite(q - 1);

                        if (q <= 1) {
                            inventaire.getInventaire().remove(obj);
                            joueur.setObjetPossede(null); // Si c'était le dernier, on désélectionne
                        } else {
                            joueur.setObjetPossede(obj); // Sinon, on garde l’objet sélectionné
                        }

                        joueurVue.mettreAJourJoueur(joueur);
                        ath.getChildren().clear();
                        inventaireVue.afficherInventaire();
                        inventaireVue.maj();
                    }
                }


                case C ->  {
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
            double cibleX = event.getX();
            double cibleY = event.getY();
            Objet objetUtilise = joueur.getObjetPossede();

            Taper taper = new Taper();
            taper.attaquerAvecEpee(joueur, List.of(ennemie), overlayRouge);
            barreVieEnnemi.mettreAJourPv(ennemie.getPv());

            if (ennemie.estMort()) {
                playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
            }

            if (objetUtilise != null) {
                if (objetUtilise instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer); // ✅ appel ici
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

        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        new AnimationTimer() {
            private long dernierCoup = 0;
            private final long delaiEntreCoups = 1_000_000_000; // 1 seconde en nanosecondes

            @Override
            public void handle(long now) {
                if (!estMort) {
                    clavierController.gererTouches();

                    // Collision avec l’ennemi
                    double distance = Math.hypot(joueur.getX() - ennemie.getX(), joueur.getY() - ennemie.getY());
                    if (!ennemie.estMort() && distance < 32 && now - dernierCoup > delaiEntreCoups) {
                        ennemie.attaque(carte);  // au lieu de juste ennemie.attaque()

                        coeurVue.mettreAJourPv(joueur.getPv());
                        bouclierVue.mettreAJourPv(joueur.getPvArmure());
                        dernierCoup = now;
                    }


                    // Mise à jour de la barre de vie de l’ennemi
                    barreVieEnnemi.mettreAJourPv(ennemie.getPv());

                    if (ennemie.estMort()) {
                        playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
                    }
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
                carte // <--- passe ta GameMap ici
        );


        couche.getChildren().add(fleche.getNode());
        fleche.startAnimation();
    }
    @FXML
    private void reapparaitre() {
        estMort = false;

        // Nouveau joueur
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);



        // Réinitialiser l'inventaire
        inventaire.getInventaire().clear();
        inventaire.ajouterObjet(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer));
        inventaire.ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        inventaire.ajouterObjet(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3));
        inventaire.ajouterObjet(new Epee("Epee", 1, carte, carteVue, joueur, tileMap));
        inventaire.ajouterObjet(new Arc("Arc", 1, carte, carteVue, joueur, tileMap));

        // Rafraîchit l'inventaire visuel
        ath.getChildren().clear();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        // Mise à jour des vues du joueur et du mob
        playerLayer.getChildren().setAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie(),
                ennemieVue.getNode(),
                barreVieEnnemi.getNode()
        );

        // Mise à jour du clavier
        clavierController.setJoueur(joueur);
        clavierController.setJoueurVue(joueurVue);
        clavierController.setCoeurVue(coeurVue);
        clavierController.setBouclierVue(bouclierVue);

        // Réinitialiser la vue
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
