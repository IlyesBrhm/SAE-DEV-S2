package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import java.net.URL;
import java.util.ResourceBundle;

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

    private InitialiseurJeu initialiseur;
    private GestionnaireEtatJeu etat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiseur = new InitialiseurJeu(tileMap, playerLayer, ath, NB_COLONNES, TAILLE_TUILE);
        initialiseur.initialiser();

        etat = new GestionnaireEtatJeu(
                initialiseur.getJoueur(),
                initialiseur.getJoueurVue(),
                initialiseur.getBarreVie(),
                initialiseur.getBarreArmure(),
                messageMort,
                boutonQuitter,
                boutonReapparaitre,
                tileMap,
                playerLayer,
                effetFlou
        );

        ClavierController clavier = new ClavierController(
                initialiseur.getTouches(),
                initialiseur.getJoueur(),
                initialiseur.getJoueurVue(),
                initialiseur.getBarreVie(),
                initialiseur.getBarreArmure(),
                playerLayer,
                etat::mourir,
                GestionEffetDegats::declencherClignotementRouge,
                initialiseur.getCarte(),
                initialiseur.getInventaire(),
                initialiseur.getInventaireVue(),
                ath,
                initialiseur.getTableCraftVue()
        );

        clavier.configurerControles();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!etat.estMort()) {
                    clavier.gererTouches();
                }
            }
        }.start();
    }

    @FXML
    private void reapparaitre() {
        etat.reapparaitre();
    }

    @FXML
    private void quitterJeu() {
        System.exit(0);
    }
}
