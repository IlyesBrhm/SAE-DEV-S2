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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new GameMap();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue = new MapVue(structure);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        carteVue.afficherCarte(tileMap);
        environnement = new Environnement(playerLayer);
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueur.setCarte(carte);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPv());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);
        ennemie.setCarte(carte);
        ennemieVue = new EnnemieVue(ennemie);
        playerLayer.getChildren().addAll(ennemieVue.getNode());



        barreVieEnnemi = new BarreDeVieVue(ennemie);
        playerLayer.getChildren().add(barreVieEnnemi.getNode());

        coeurVue.mettreAJourPv(joueur.getVie().getPv());
        bouclierVue.mettreAJourPv(joueur.getVie().getPvArmure());

        objetAuSol = new ObjetAuSol(5, 19, playerLayer, new Pioche("pioche", 1, carte, carteVue, joueur, null, playerLayer));
        ObjetAuSol objetAuSol1 = new ObjetAuSol(5,21,playerLayer,new Arc("Arc",1,carte,carteVue,joueur,tileMap));


        inventaire = new Inventaire();
        inventaire.ajouterObjet(new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer)));
        inventaire.ajouterObjet(new CaseInventaire(new Potion("potionVie", 1, joueur, "vie")));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2)));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)));
        inventaire.ajouterObjet(new CaseInventaire(new Epee("Epee", 1, carte, carteVue, joueur, tileMap)));
        inventaire.ajouterObjet(new CaseInventaire(new Arc("Arc", 1, carte, carteVue, joueur, tileMap)));

        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        GestionEffetDegats.definirSuperposition(overlayRouge);



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
                inventaire.getInventaire().get(2),
                inventaire.getInventaire().get(3)),
                new CaseInventaire(new Potion("potionVie", 5, joueur, "vie"))));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)),
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer))
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Bois", 1, false, carte, carteVue, joueur, 2)),
                        new CaseInventaire(new Bloc("Bois", 1, false, carte, carteVue, joueur, 2)),
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Arc("Arc", 1, carte, carteVue, joueur, playerLayer))
        ));

        tableCraftVue = new TableCraftVue(paneCraft, tableCraft, inventaire, inventaireVue, ath);

        playerLayer.setFocusTraversable(true);
        playerLayer.requestFocus();

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());
            this.gererTouches();

            switch (event.getCode()) {
                case E -> {
                    if (joueur.ramasser(environnement,inventaire)) {
                        inventaireVue.maj();

                    }
                }
                case A -> {
                    CaseInventaire caseInventaire = new CaseInventaire(new Arc("ukjhguhkh", 1, carte, carteVue, joueur, tileMap));
                    caseInventaire.setObjet(joueur.getObjetPossede());
                    if (caseInventaire != null) {
                        //caseInventaire.setQuantite(1);
                        int q = caseInventaire.getQuantite();
                        joueur.deposerObjetEnMain(environnement);
                        caseInventaire.setQuantite(q - 1);

                        if (caseInventaire.getQuantite() <= 1) {
                            inventaire.getInventaire().remove(caseInventaire);
                            joueur.setObjetPossede(null); // Si c'était le dernier, on désélectionne
                        } else {
                            joueur.setObjetPossede(caseInventaire.getObjet()); // Sinon, on garde l’objet sélectionné
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
                        joueur.setObjetPossede(inventaire.getInventaire().get(index).getObjet());
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
            CaseInventaire caseUtilise = new CaseInventaire(joueur.getObjetPossede());

            Taper taper = new Taper();
            taper.attaquerAvecEpee(joueur, List.of(ennemie), overlayRouge);
            barreVieEnnemi.mettreAJourPv(ennemie.getVie().getPv());

            if (ennemie.getVie().estMort()) {
                playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
            }

            if (caseUtilise.getObjet() != null) {
                if (caseUtilise.getObjet() instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer); // ✅ appel ici
                } else {
                    caseUtilise.getObjet().utiliser((int)(cibleX / TAILLE_TUILE), (int)(cibleY / TAILLE_TUILE));
                    if (caseUtilise.getObjet() instanceof Bloc || caseUtilise.getObjet().getConsomable()) {
                        caseUtilise.decrementerQuantite(1);
                        if (caseUtilise.getQuantite() <= 0) {
                            inventaire.getInventaire().remove(caseUtilise.getObjet());
                            joueur.setObjetPossede(null);
                        }
                    }
                    coeurVue.mettreAJourPv(joueur.getVie().getPv());
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

                    environnement.unTour();


                    joueur.deplacer();

                    miseAjour2();

                    // Collision avec l’ennemi
                    double distance = Math.hypot(joueur.getX() - ennemie.getX(), joueur.getY() - ennemie.getY());
                    if (!ennemie.getVie().estMort() && distance < 32 && now - dernierCoup > delaiEntreCoups) {
                        ennemie.attaque(carte);  // au lieu de juste ennemie.attaque()

                        coeurVue.mettreAJourPv(joueur.getVie().getPv());
                        bouclierVue.mettreAJourPv(joueur.getVie().getPvArmure());
                        dernierCoup = now;
                    }


                    // Mise à jour de la barre de vie de l’ennemi
                    barreVieEnnemi.mettreAJourPv(ennemie.getVie().getPv());

                    if (ennemie.getVie().estMort()) {
                        playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
                    }
                }
            }
        }.start();

    }


    public void gererTouches() {
        System.out.println("gérertouches");
        if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
            joueur.allerGauche();
            System.out.println("gauche");
        }
        if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
            joueur.allerDroite();
            System.out.println("droite");
        }
        if (!touchesAppuyees.contains(KeyCode.D) && !touchesAppuyees.contains(KeyCode.RIGHT) && !touchesAppuyees.contains(KeyCode.Q) && !touchesAppuyees.contains(KeyCode.LEFT)) {
            joueur.immobile();
        }

        if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
            joueur.sauter();
        }

    }

    private long maintenant;
    private final Runnable callbackMort = this::mourir;
    private final Runnable afficherDegat = GestionEffetDegats::declencherClignotementRouge;

    public void  miseAjour2() {
        joueur.mettreAJour(carte);
        joueurVue.mettreAJourJoueur(joueur);

        ennemieVue.mettreAJour(ennemie);
        ennemie.deplacementMob(carte);
        ennemie.mettreAJour(carte);

        // Gestion collision entre joueur et ennemie
        if ((int) ennemie.getX() == (int) joueur.getX() && (int) ennemie.getY() == (int) joueur.getY()) {
            long maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getVie().getDernierDegatFeu() > 1000) {
                ennemie.attaque(carte);  // au lieu de juste ennemie.attaque()

                joueur.getVie().setDernierDegatFeu(maintenant);
            }
        }

        // Mise à jour des barres de vie
        coeurVue.mettreAJourPv(joueur.getVie().getPv());
        bouclierVue.mettreAJourPv(joueur.getVie().getPvArmure());

        // Dégâts de feu si le joueur est sur un bloc feu (5)
        int tuileX = (int) (joueur.getX() / 32);
        int tuileY = (int) (joueur.getY() / 32);

        if (carte.getTile(tuileY, tuileX) == 5 && !joueur.getVie().estMort()) {
            maintenant = System.currentTimeMillis();
            if (maintenant - joueur.getVie().getDernierDegatFeu() > 1000) {
                joueur.getVie().setDernierDegatFeu(maintenant);

                if (joueur.getVie().getPvArmure() > 0) {
                    joueur.getVie().decrementerPvArmure(1);
                    bouclierVue.mettreAJourPv(joueur.getVie().getPvArmure());
                } else {
                    joueur.getVie().decrementerPvArmure(1);
                    coeurVue.mettreAJourPv(joueur.getVie().getPv());
                }

                afficherDegat.run();

                if (joueur.getVie().getPv() <= 0) {
                    callbackMort.run();
                }
            }
        }

        if (joueur.getVie().getPv() <= 0) {
            callbackMort.run();
        }
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
        joueur.setCarte(carte);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPvArmure());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);



        // Réinitialiser l'inventaire
        inventaire.getInventaire().clear();
        inventaire.ajouterObjet(new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, objetAuSol, playerLayer)) );
        inventaire.ajouterObjet(new CaseInventaire(new Potion("potionVie", 1, joueur, "vie")));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Terre", 1, false, carte, carteVue, joueur, 2)));
        inventaire.ajouterObjet(new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)));
        inventaire.ajouterObjet(new CaseInventaire(new Epee("Epee", 1, carte, carteVue, joueur, tileMap)));
        inventaire.ajouterObjet(new CaseInventaire(new Epee("Epee", 1, carte, carteVue, joueur, tileMap)));

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
