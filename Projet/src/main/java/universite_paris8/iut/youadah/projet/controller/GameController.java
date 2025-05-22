package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import universite_paris8.iut.youadah.projet.modele.Inventaire;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.*;


import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controleur implements Initializable {

    @FXML
    private TilePane tileMap;

    @FXML
    private Pane playerLayer;

    @FXML
    private Pane ath;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private Map carte;
    private MapVue  carteVue;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private Inventaire inventaire;
    private InventaireVue inventaireVue;

    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);
        carteVue= new MapVue();
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);

        carteVue.afficherCarte(structure, tileMap);
        inventaire = new Inventaire();
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE, inventaire);
        joueurVue = new PlayerVue(joueur,playerLayer);
        coeurVue = new CoeurVue(joueur.getPv(),ath);
        coeurVue.mettreAJourPv(joueur.getPv());

        joueurVue.afficherJoueur();
        coeurVue.afficherCoeur();

        Objet pioche = new Objet("pioche",1);


        inventaireVue = new InventaireVue(ath, inventaire);
        inventaireVue.afficherInventaire();
        inventaire.ajouterObjet(pioche);
        inventaireVue.maj();

        Image image = new Image(getClass().getResource("/images/invp.png").toExternalForm());
        ImageView imageView = new ImageView(image);

        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> {
            touchesAppuyees.add(event.getCode());

            if (event.getCode() == KeyCode.K) {
                joueur.decrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());
            }

            if (event.getCode() == KeyCode.G) {
                joueur.incrementerPv(1);
                coeurVue.mettreAJourPv(joueur.getPv());
            }
            if (event.getCode() == KeyCode.F1) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((0*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F2) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((1*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F3) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((2*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F4) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((3*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F5) {

                joueur.setObjetPossede(inventaire.getInventaire().get(0));
                System.out.println("objet posséder " + inventaire.getInventaire().get(0).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((4*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }

            if (event.getCode() == KeyCode.F6) {
                joueur.setObjetPossede(inventaire.getInventaire().get(1));
                System.out.println("objet posséder " + inventaire.getInventaire().get(1).getNom());

                imageView.setFitHeight(64);
                imageView.setFitWidth(64);
                imageView.setX((5*64)+730);
                ath.getChildren().remove(imageView);
                ath.getChildren().add(imageView);
            }
        });
        playerLayer.setOnKeyReleased(event -> touchesAppuyees.remove(event.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (touchesAppuyees.contains(KeyCode.Q) || touchesAppuyees.contains(KeyCode.LEFT)) {
                    joueur.deplacerGauche(carte);
                }
                if (touchesAppuyees.contains(KeyCode.D) || touchesAppuyees.contains(KeyCode.RIGHT)) {
                    joueur.deplacerDroite(carte);
                }
                if (touchesAppuyees.contains(KeyCode.Z) || touchesAppuyees.contains(KeyCode.SPACE)) {
                    joueur.sauter();
                }

                joueur.mettreAJour(carte);
                joueurVue.mettreAJourJoueur(joueur);



                if (!joueur.estVivant()) {
                    VBox menuMort = new VBox(20); // espacement vertical entre les éléments
                    menuMort.setAlignment(Pos.CENTER);
                    menuMort.setLayoutX(300); // adapte en fonction de ta taille de fenêtre
                    menuMort.setLayoutY(200);

                    Text titre = new Text("Vous êtes mort");
                    titre.setFont(new Font("Arial", 40));

                    Button quitterBtn = new Button("Quitter");
                    quitterBtn.setOnAction(e -> quitter());

                    menuMort.getChildren().addAll(titre,  quitterBtn);
                    ath.getChildren().add(menuMort);
                }

            }
        }.start();
    }

    public void quitter() {
        javafx.application.Platform.exit();
        System.exit(0);
    }

}