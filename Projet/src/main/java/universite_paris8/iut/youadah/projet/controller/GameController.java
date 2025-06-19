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

    private MapVue carteVue;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private BouclierVue bouclierVue;
    private ClavierController clavierController;
    private InventaireVue inventaireVue;
    private boolean estMort = false;
    private EnnemieVue ennemieVue;
    private BarreDeVieVue barreVieEnnemi;

    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane paneCraft;
    private boolean craftVisible = false;

    private Jeu jeu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        jeu = new Jeu(tileMap,playerLayer,overlayRouge,ath,messageMort,boutonQuitter,boutonReapparaitre);
        jeu.initialiserJeu();
        this.paneCraft = jeu.getPaneCraft();
        this.tableCraft = jeu.getTableCraft();

        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);

        carteVue = new MapVue(jeu.getCarte());
        carteVue.afficherCarte(tileMap);

        joueurVue = new PlayerVue(jeu.getJoueur());

        coeurVue = new CoeurVue(jeu.getJoueur().getPv());

        bouclierVue = new BouclierVue(jeu.getJoueur().getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        ennemieVue = new EnnemieVue(jeu.getEnnemie());

        barreVieEnnemi = new BarreDeVieVue(jeu.getEnnemie());
        playerLayer.getChildren().add(barreVieEnnemi.getNode());

        playerLayer.getChildren().addAll(ennemieVue.getNode());

        coeurVue.mettreAJourPv(jeu.getJoueur().getPv());
        bouclierVue.mettreAJourPv(jeu.getJoueur().getPvArmure());




        inventaireVue = new InventaireVue(ath, jeu.getInventaire());
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        playerLayer.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );

        clavierController = new ClavierController(
                touchesAppuyees,
                jeu.getJoueur(),
                joueurVue,
                jeu.getEnnemie(),
                ennemieVue,
                coeurVue,
                bouclierVue,
                playerLayer,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                jeu.getCarte()
        );
        clavierController.configurerControles();


        ath.setOnMouseClicked(event -> {
            double cibleX = event.getX();
            double cibleY = event.getY();
            Objet objetUtilise = jeu.getJoueur().getObjetPossede();

            Taper taper = new Taper();
            taper.attaquerAvecEpee(jeu.getJoueur(), List.of(jeu.getEnnemie()), overlayRouge);
            barreVieEnnemi.mettreAJourPv(jeu.getEnnemie().getPv());

            if (jeu.getEnnemie().estMort()) {
                playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
            }

            if (objetUtilise != null) {
                if (objetUtilise instanceof Arc) {
                    tirerFleche(cibleX, cibleY, playerLayer);
                } else {
                    objetUtilise.utiliser((int)(cibleX / TAILLE_TUILE), (int)(cibleY / TAILLE_TUILE), carteVue);
                    if (objetUtilise instanceof Bloc || objetUtilise.getConsomable()) {
                        objetUtilise.decrementerQuantite(1);
                        if (objetUtilise.getQuantite() <= 0) {
                            jeu.getInventaire().getInventaire().remove(objetUtilise);
                            jeu.getJoueur().setObjetPossede(null);
                        }
                    }
                    coeurVue.mettreAJourPv(jeu.getJoueur().getPv());
                }

                ath.getChildren().clear();
                inventaireVue.afficherInventaire();
                inventaireVue.maj();
            }

        });

        tableCraftVue = new TableCraftVue(jeu.getPaneCraft(), jeu.getTableCraft(), jeu.getInventaire(), inventaireVue, ath);



        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);

        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            switch (event.getCode()) {
                case E -> {
                    if (jeu.getObjetAuSol().ramasser(jeu.getJoueur(), jeu.getInventaire(), playerLayer)) {
                        inventaireVue.maj();
                    }
                }
                case A -> {
                    Objet obj = jeu.getJoueur().getObjetPossede();
                    if (obj != null) {
                        int q = obj.getQuantite();
                        obj.setQuantite(1);
                        jeu.getObjetAuSol().deposerJoueur(obj, jeu.getJoueur(), playerLayer);
                        obj.setQuantite(q - 1);

                        if (q <= 1) {
                            jeu.getInventaire().getInventaire().remove(obj);
                            jeu.getJoueur().setObjetPossede(null); // Si c'était le dernier, on désélectionne
                        } else {
                            jeu.getJoueur().setObjetPossede(obj); // Sinon, on garde l’objet sélectionné
                        }

                        joueurVue.mettreAJourJoueur(jeu.getJoueur());
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
                    if (index < jeu.getInventaire().getInventaire().size()) {
                        jeu.getJoueur().setObjetPossede(jeu.getInventaire().getInventaire().get(index));
                        imageView.setX((index * 64) + 730);
                        ath.getChildren().remove(imageView);
                        ath.getChildren().add(imageView);
                    }
                }

            }
        });

        new AnimationTimer() {
            private long dernierCoup = 0;
            private final long delaiEntreCoups = 1_000_000_000; // 1 seconde en nanosecondes

            @Override
            public void handle(long now) {
                if (!estMort) {
                    clavierController.gererTouches();

                    // Collision avec l’ennemi
                    double distance = Math.hypot(jeu.getJoueur().getX() - jeu.getEnnemie().getX(), jeu.getJoueur().getY() - jeu.getEnnemie().getY());
                    if (!jeu.getEnnemie().estMort() && distance < 32 && now - dernierCoup > delaiEntreCoups) {
                        jeu.getEnnemie().attaque(jeu.getCarte());  // au lieu de juste ennemie.attaque()

                        coeurVue.mettreAJourPv(jeu.getJoueur().getPv());
                        bouclierVue.mettreAJourPv(jeu.getJoueur().getPvArmure());
                        dernierCoup = now;
                    }


                    // Mise à jour de la barre de vie de l’ennemi
                    barreVieEnnemi.mettreAJourPv(jeu.getEnnemie().getPv());

                    if (jeu.getEnnemie().estMort()) {
                        playerLayer.getChildren().removeAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
                    }
                }
            }
        }.start();

    }

    @FXML
    private void reapparaitre() {
        estMort = false;

        // Réinitialiser le joueur (PV, armure, position)
        Player joueur = jeu.getJoueur();
        joueur.setPv(10);
        joueur.setPvArmure(5);
        joueur.setX(5 * 32);
        joueur.setY(19 * 32);

        // Réinitialiser les vues liées au joueur
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        bouclierVue = new BouclierVue(joueur.getPvArmure(), ath);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        coeurVue.mettreAJourPv(joueur.getPv());
        bouclierVue.mettreAJourPv(joueur.getPvArmure());

        // Réinitialiser l’inventaire
        jeu.getInventaire().getInventaire().clear();
        jeu.getInventaire().ajouterObjet(new Pioche("pioche", 1, jeu.getCarte(), joueur, jeu.getObjetAuSol(), playerLayer));
        jeu.getInventaire().ajouterObjet(new Potion("potionVie", 1, joueur, "vie"));
        jeu.getInventaire().ajouterObjet(new Bloc("Terre", 1, false, jeu.getCarte(), joueur, 2));
        jeu.getInventaire().ajouterObjet(new Bloc("Pierre", 1, false, jeu.getCarte(), joueur, 3));
        jeu.getInventaire().ajouterObjet(new Epee("Epee", 1, jeu.getCarte(), joueur, tileMap));
        jeu.getInventaire().ajouterObjet(new Arc("Arc", 1, jeu.getCarte(), joueur));

        // Rafraîchir l'inventaire visuel
        ath.getChildren().clear();
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        // Réinitialiser l'ennemi
        jeu.getEnnemie().setPv(5); // si la méthode existe
        ennemieVue = new EnnemieVue(jeu.getEnnemie());
        barreVieEnnemi = new BarreDeVieVue(jeu.getEnnemie());

        // Mettre à jour les éléments dans la couche joueur
        playerLayer.getChildren().setAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie(),
                ennemieVue.getNode(),
                barreVieEnnemi.getNode()
        );

        // Réassocier au contrôleur clavier
        clavierController.setJoueur(joueur);
        clavierController.setJoueurVue(joueurVue);
        clavierController.setCoeurVue(coeurVue);
        clavierController.setBouclierVue(bouclierVue);

        // Masquer les éléments de mort
        boutonQuitter.setVisible(false);
        boutonReapparaitre.setVisible(false);
        messageMort.setVisible(false);
        tileMap.setEffect(null);
        playerLayer.setEffect(null);
    }



    private void tirerFleche(double cibleX, double cibleY, Pane couche) {
        Fleche fleche = new Fleche(
                jeu.getJoueur().getX(), jeu.getJoueur().getY(),
                cibleX, cibleY,
                List.of(jeu.getEnnemie()),
                overlayRouge,
                2,
                jeu.getCarte() // <--- passe ta GameMap ici
        );


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
    private void quitterJeu() {
        System.exit(0);
    }
}
