package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Map;
import universite_paris8.iut.youadah.projet.modele.Player;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class GameController implements Initializable {

    @FXML
    private GridPane tileMap;

    @FXML
    private Pane playerLayer;

    private static final int TILE_SIZE = 32;
    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;

    private Map map;
    private Player player;
    private ImageView playerView;

    private final Set<KeyCode> pressedKeys = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        map = new Map();
        int[][] terrain = map.creerTerrain(32, 58);

        // Remplir la carte dans le GridPane
        for (int y = 0; y < terrain.length; y++) {
            for (int x = 0; x < terrain[y].length; x++) {
                ImageView tile = new ImageView(getImageAssociee(terrain[y][x]));
                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tileMap.add(tile, x, y);
            }
        }

        // Initialiser le joueur
        player = new Player(5 * TILE_SIZE, 10 * TILE_SIZE);
        playerView = new ImageView(player.getCurrentImage());
        playerView.setFitWidth(TILE_SIZE);
        playerView.setFitHeight(TILE_SIZE);
        playerView.setTranslateX(player.x);
        playerView.setTranslateY(player.y);

        // Ajouter le joueur au Pane au-dessus
        playerLayer.getChildren().add(playerView);

        // Gérer les touches clavier
        playerLayer.setFocusTraversable(true);
        playerLayer.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        playerLayer.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        // Animation de jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pressedKeys.contains(KeyCode.Q) || pressedKeys.contains(KeyCode.LEFT)) {
                    player.moveLeft();
                }
                if (pressedKeys.contains(KeyCode.D) || pressedKeys.contains(KeyCode.RIGHT)) {
                    player.moveRight();
                }
                if (pressedKeys.contains(KeyCode.Z) || pressedKeys.contains(KeyCode.SPACE)) {
                    player.jump();
                }

                player.update(map);
                updatePlayerView();
            }
        }.start();
    }

    private void updatePlayerView() {
        playerView.setImage(player.getCurrentImage());
        playerView.setTranslateX(player.x);
        playerView.setTranslateY(player.y);
    }

    private Image getImageAssociee(int id) {
        return switch (id) {
            case 0 -> new Image(getClass().getResource("/images/Vide.png").toExternalForm());
            case 1 -> new Image(getClass().getResource("/images/Herbe.png").toExternalForm());
            case 2 -> new Image(getClass().getResource("/images/Terre.png").toExternalForm());
            default -> null;
        };
    }
}
