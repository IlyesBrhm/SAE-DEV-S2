package universite_paris8.iut.aclaudio.demo.world;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import universite_paris8.iut.aclaudio.demo.Player.Player;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;


public class MapDisplay extends Application {

    private static final int TILE_SIZE = 32;
    private Map map;

    private Player player;
    private final Set<KeyCode> pressedKeys = new HashSet<>();


    private Image imgHerbe;
    private Image imgTerre;
    private Image imgPierre;
    private Image imgNuage;
    private Image imgVide;
    private Image imgArbre;

    @Override
    public void start(Stage primaryStage) {
        map = new Map(80, 40);
        player = new Player(32 * 5, 32 * 10); // Apparait vers x=5, y=10


        int canvasWidth = map.getWidth() * TILE_SIZE;
        int canvasHeight = map.getHeight() * TILE_SIZE;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        imgHerbe = new Image(getClass().getResource("/images/Herbe.png").toExternalForm());
        imgTerre = new Image(getClass().getResource("/images/Terre.png").toExternalForm());
        imgPierre = new Image(getClass().getResource("/images/Pierre.png").toExternalForm());
        imgNuage = new Image(getClass().getResource("/images/Nuage.png").toExternalForm());
        imgVide = new Image(getClass().getResource("/images/Vide.png").toExternalForm());
        imgArbre = new Image(getClass().getResource("/images/Arbre.png").toExternalForm());

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Affichage de la Map");
        primaryStage.setScene(scene);

        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q -> player.moveLeft();     // gauche
                case D -> player.moveRight();    // droite
                case Z -> player.jump();         // saut
                case SPACE -> player.jump();     // saut
            }
        });

        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Déplacement gauche/droite/saut
                if (pressedKeys.contains(KeyCode.Q) || pressedKeys.contains(KeyCode.LEFT)) {
                    player.moveLeft();
                }
                if (pressedKeys.contains(KeyCode.D) || pressedKeys.contains(KeyCode.RIGHT)) {
                    player.moveRight();
                }
                if (pressedKeys.contains(KeyCode.Z) || pressedKeys.contains(KeyCode.SPACE)) {
                    player.jump();
                }

                // Met à jour la gravité, position...
                player.update(map);

                // Redessine la carte et le joueur
                drawMap(gc);
            }
        }.start();

    }

    private void drawMap(GraphicsContext gc) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                int tileType = map.getTile(x, y);

                // Couleur de fond
                gc.setFill(Color.SKYBLUE);
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                // Cas spécial arbre : ne pas redessiner derrière
                if (tileType == Map.Arbre) {
                    gc.drawImage(imgArbre, x * TILE_SIZE, (y - 9) * TILE_SIZE, TILE_SIZE, TILE_SIZE * 10);
                    continue; // saute le dessin standard
                }

                // Rendu normal pour les autres types
                Image img = switch (tileType) {
                    case Map.Terre -> imgTerre;
                    case Map.Pierre -> imgPierre;
                    case Map.Nuage -> imgNuage;
                    case Map.Herbe -> imgHerbe;
                    default -> null;
                };

                if (img != null) {
                    gc.drawImage(img, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Dessiner le joueur après la map
        gc.drawImage(player.getCurrentImage(), player.x, player.y, TILE_SIZE, TILE_SIZE);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
