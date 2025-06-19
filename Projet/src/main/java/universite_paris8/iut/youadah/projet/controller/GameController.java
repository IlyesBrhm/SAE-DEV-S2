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

        jeu = new Jeu(tileMap,playerLayer,overlayRouge,ath);
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
                jeu.getObjetAuSol(),
                playerLayer,
                jeu.getInventaire(),
                inventaireVue,
                paneCraft,
                ath,
                tableCraftVue,
                this::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                jeu.getCarte()
                );
        clavierController.configurerControles();


        SourisController sourisController = new SourisController(
                touchesAppuyees,
                jeu.getJoueur(),
                jeu.getEnnemie(),
                ennemieVue,
                coeurVue,
                playerLayer,
                jeu.getInventaire(),
                inventaireVue,
                ath,
                jeu.getCarte(),
                overlayRouge
        );

        sourisController.gestionSouris(barreVieEnnemi,carteVue);

        tableCraftVue = new TableCraftVue(jeu.getPaneCraft(), jeu.getTableCraft(), jeu.getInventaire(), inventaireVue);



        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(64);
        imageView.setFitWidth(64);

        new AnimationTimer() {
            private long dernierCoup = 0;
            private final long delaiEntreCoups = 1_000_000_000; // 1 seconde en nanosecondes

            @Override
            public void handle(long now) {
                if (!estMort) {
                    clavierController.gererTouches(imageView);

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
