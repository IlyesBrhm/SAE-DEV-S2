package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Elle avance automatiquement dans une direction donnée jusqu'à sortir de la carte.
 */
public class Fleche {

    private final ImageView node;     // ImageView représentant visuellement la flèche
    private double posX, posY;        // Position actuelle de la flèche (en pixels)
    private final double vitesse = 10; // Vitesse de déplacement (pixels par frame)
    private double dirX, dirY;        // Direction normalisée (vecteur unitaire)

    private AnimationTimer animation; // Timer JavaFX pour animer la flèche à chaque frame

    /**
     * Constructeur : initialise une flèche entre un point de départ et un point cible.
     * @param departX Coordonnée X de départ
     * @param departY Coordonnée Y de départ
     * @param cibleX Coordonnée X de la cible
     * @param cibleY Coordonnée Y de la cible
     */
    public Fleche(double departX, double departY, double cibleX, double cibleY) {
        // Position de départ
        this.posX = departX;
        this.posY = departY;

        // Calcul du vecteur direction (différence entre cible et départ)
        double dx = cibleX - departX;
        double dy = cibleY - departY;

        // Normalisation pour obtenir un vecteur unitaire
        double longueur = Math.sqrt(dx * dx + dy * dy);
        dirX = dx / longueur;
        dirY = dy / longueur;

        // Chargement de l’image de la flèche
        Image imageFleche = new Image(getClass().getResource("/images/fleche.png").toExternalForm());
        node = new ImageView(imageFleche);
        node.setFitWidth(100);   // Largeur de la flèche à l’écran
        node.setFitHeight(30);   // Hauteur de la flèche

        // Position initiale de l’image
        node.setLayoutX(posX);
        node.setLayoutY(posY);

        // Rotation de l’image pour l’orienter vers la cible
        double angle = Math.toDegrees(Math.atan2(dy, dx)); // atan2 donne l'angle en radians
        node.setRotate(angle);

        // Création du timer d’animation
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                avancer(); // Appelé à chaque frame (~60 fois par seconde)
            }
        };
    }

    /**
     * Accès au nœud graphique de la flèche.
     * @return ImageView représentant la flèche
     */
    public ImageView getNode() {
        return node;
    }

    //Lance l’animation de la flèche.
    public void startAnimation() {
        animation.start();
    }

    /**
     * Fait avancer la flèche dans sa direction à chaque frame.
     * Si elle sort de l’écran, elle est supprimée.
     */
    private void avancer() {
        // Mise à jour de la position
        posX += dirX * vitesse;
        posY += dirY * vitesse;

        // Application à l'image affichée
        node.setLayoutX(posX);
        node.setLayoutY(posY);

        // Condition d’arrêt : si la flèche sort de la carte, on arrête l'animation
        if (posX < 0 || posX > 32 * 58 || posY < 0 || posY > 32 * 32) {
            animation.stop(); // Stoppe le déplacement

            // Supprime le nœud graphique de la scène s’il est encore affiché
            if (node.getParent() != null) {
                ((Pane) node.getParent()).getChildren().remove(node);
            }
        }
    }
}