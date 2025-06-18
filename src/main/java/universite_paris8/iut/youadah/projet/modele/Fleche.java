package universite_paris8.iut.youadah.projet.modele;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Fleche {

    private final ImageView node;
    private double posX, posY;
    private final double vitesse = 8; // pixels par frame (ajuste selon besoin)
    private double dirX, dirY;

    private AnimationTimer animation;

    public Fleche(double departX, double departY, double cibleX, double cibleY) {
        // Initialisation position départ
        this.posX = departX;
        this.posY = departY;

        // Calcul direction normalisée (vecteur unitaire)
        double dx = cibleX - departX;
        double dy = cibleY - departY;
        double longueur = Math.sqrt(dx * dx + dy * dy);
        dirX = dx / longueur;
        dirY = dy / longueur;

        // Image flèche
        Image imageFleche = new Image(getClass().getResource("/images/fleche.png").toExternalForm());
        node = new ImageView(imageFleche);
        node.setFitWidth(100);
        node.setFitHeight(30);
        node.setLayoutX(posX);
        node.setLayoutY(posY);

        // Rotation pour orienter la flèche vers la cible
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        node.setRotate(angle);

        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                avancer();
            }
        };
    }

    public ImageView getNode() {
        return node;
    }

    public void startAnimation() {
        animation.start();
    }

    private void avancer() {
        posX += dirX * vitesse;
        posY += dirY * vitesse;

        node.setLayoutX(posX);
        node.setLayoutY(posY);

        // Condition d’arrêt : hors écran ou trop loin, stoppe l'animation et supprime la flèche
        if (posX < 0 || posX > 32 * 58 || posY < 0 || posY > 32 * 32) { // adapte la taille de la carte
            animation.stop();
            if (node.getParent() != null) {
                ((Pane) node.getParent()).getChildren().remove(node);
            }
        }
    }
}
