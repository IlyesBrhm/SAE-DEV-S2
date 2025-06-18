package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.GameMap;
import universite_paris8.iut.youadah.projet.modele.Personnage;
import universite_paris8.iut.youadah.projet.modele.actions.Tirer;

import java.util.List;

/**
 * Une flèche qui avance dans une direction donnée et inflige des dégâts à l’impact.
 */
public class Fleche {

    private final ImageView node;
    private double posX, posY;
    private final double vitesse = 10;
    private final double dirX, dirY;

    private AnimationTimer animation;

    // Nouveaux paramètres nécessaires pour collision/dégâts
    private final List<Personnage> cibles;
    private final Pane overlay;
    private final int degats;
    private GameMap carte;

    /**
     * Crée une flèche avec collisions.
     *
     * @param departX position X de départ
     * @param departY position Y de départ
     * @param cibleX position X visée
     * @param cibleY position Y visée
     * @param cibles liste des ennemis (Personnage)
     * @param overlay pane pour effet rouge
     * @param degats dégâts infligés à l’impact
     */
    public Fleche(double departX, double departY, double cibleX, double cibleY,
                  List<Personnage> cibles, Pane overlay, int degats, GameMap carte) {

        this.posX = departX;
        this.posY = departY;
        this.cibles = cibles;
        this.overlay = overlay;
        this.degats = degats;
        this.carte=carte;

        double dx = cibleX - departX;
        double dy = cibleY - departY;
        double longueur = Math.sqrt(dx * dx + dy * dy);
        this.dirX = dx / longueur;
        this.dirY = dy / longueur;

        Image imageFleche = new Image(getClass().getResource("/images/fleche.png").toExternalForm());
        node = new ImageView(imageFleche);
        node.setFitWidth(100);
        node.setFitHeight(30);
        node.setLayoutX(posX);
        node.setLayoutY(posY);
        node.setRotate(Math.toDegrees(Math.atan2(dy, dx)));

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

        // --- Détection de collision avec un bloc solide ---
        int tuileX = (int) (posX / 32);
        int tuileY = (int) (posY / 32);

        if (carte != null && tuileX >= 0 && tuileX < carte.getLargeur()
                && tuileY >= 0 && tuileY < carte.getHauteur()) {

            int bloc = carte.getTile(tuileY, tuileX);
            if (bloc != 0) { // 0 = vide ; tout autre bloc = obstacle
                detruire(); // détruit la flèche
                return; // on arrête tout ici
            }
        }

        // --- Collision avec un ennemi ---
        Tirer tirer = new Tirer();
        tirer.infligerDegatsSiCollision(posX, posY, cibles, overlay, degats);

        // --- Si la flèche sort de l’écran ---
        if (posX < 0 || posX > 32 * 58 || posY < 0 || posY > 32 * 32) {
            detruire();
        }
    }


    private void detruire() {
        animation.stop();
        if (node.getParent() != null) {
            ((Pane) node.getParent()).getChildren().remove(node);
        }
    }
}
