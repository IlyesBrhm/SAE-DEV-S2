package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;
import universite_paris8.iut.youadah.projet.vue.MapRenderer;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static universite_paris8.iut.youadah.projet.vue.MapRenderer.chargerImageTuile;

public class GameController implements Initializable {

    @FXML
    private TilePane tileMap;

    @FXML
    private Pane playerLayer;

    private static final int TAILLE_TUILE = 32;
    private static final int NB_COLONNES = 58;

    private Map carte;
    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;

    private final Set<KeyCode> touchesAppuyees = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carte = new Map();
        int[][] structure = carte.creerTerrain(32, NB_COLONNES);

        tileMap.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        tileMap.setPrefTileWidth(TAILLE_TUILE);
        tileMap.setPrefTileHeight(TAILLE_TUILE);
        tileMap.setHgap(0);
        tileMap.setVgap(0);
        tileMap.setPadding(new Insets(0));
        tileMap.setTileAlignment(Pos.TOP_LEFT);
        tileMap.setPrefColumns(NB_COLONNES);
        tileMap.setMinWidth(TAILLE_TUILE * NB_COLONNES);
        tileMap.setMaxWidth(TAILLE_TUILE * NB_COLONNES);


        for (int y = 0; y < structure.length; y++) {
            for (int x = 0; x < structure[y].length; x++) {
                ImageView tuile = new ImageView(chargerImageTuile(structure[y][x]));
                tuile.setFitWidth(TAILLE_TUILE);
                tuile.setFitHeight(TAILLE_TUILE);
                tileMap.getChildren().add(tuile);
            }
        }


        joueur = new Player(5 * TAILLE_TUILE, 10 * TAILLE_TUILE);
        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getPv());
        coeurVue.mettreAJourPv(joueur.getPv());

        playerLayer.getChildren().add(joueurVue.getNode());
        playerLayer.getChildren().add(coeurVue.getBarreVie());


        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> touchesAppuyees.add(event.getCode()));
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

                MapRenderer.mettreAJourJoueur(joueur, carte);
                joueurVue.mettreAJour(joueur);


            }
        }.start();
    }
}
