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
 * Cette classe gère le comportement d'une flèche tirée par un arc, incluant son mouvement,
 * ses collisions avec les ennemis et les blocs du jeu, et les dégâts qu'elle inflige.
 */
public class Fleche {

    // Représentation visuelle de la flèche dans l'interface
    private final ImageView node;
    // Position actuelle de la flèche
    private double posX, posY;
    // Vitesse de déplacement de la flèche
    private final double vitesse = 10;
    // Direction normalisée du mouvement (vecteur unitaire)
    private final double dirX, dirY;

    // Timer qui gère l'animation et le déplacement de la flèche
    private AnimationTimer animation;

    // Paramètres nécessaires pour la gestion des collisions et des dégâts
    // Liste des personnages pouvant être touchés par la flèche
    private final List<Personnage> cibles;
    // Panneau pour afficher l'effet visuel lors d'un impact
    private final Pane overlay;
    // Quantité de dégâts infligés lors d'un impact
    private final int degats;
    // Référence à la carte du jeu pour détecter les collisions avec les blocs
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
     * @param carte référence à la carte du jeu pour détecter les collisions avec les blocs
     */
    public Fleche(double departX, double departY, double cibleX, double cibleY,
                  List<Personnage> cibles, Pane overlay, int degats, GameMap carte) {

        // Initialisation des propriétés de base
        this.posX = departX;
        this.posY = departY;
        this.cibles = cibles;
        this.overlay = overlay;
        this.degats = degats;
        this.carte=carte;

        // Calcul du vecteur de direction normalisé
        double dx = cibleX - departX;
        double dy = cibleY - departY;
        double longueur = Math.sqrt(dx * dx + dy * dy);
        this.dirX = dx / longueur;
        this.dirY = dy / longueur;

        // Création et configuration de la représentation visuelle de la flèche
        Image imageFleche = new Image(getClass().getResource("/images/fleche.png").toExternalForm());
        node = new ImageView(imageFleche);
        node.setFitWidth(100);
        node.setFitHeight(30);
        node.setLayoutX(posX);
        node.setLayoutY(posY);
        // Rotation de l'image pour qu'elle pointe dans la direction du mouvement
        node.setRotate(Math.toDegrees(Math.atan2(dy, dx)));

        // Création du timer d'animation qui appellera la méthode avancer() à chaque frame
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                avancer();
            }
        };
    }

    /**
     * Récupère la représentation visuelle de la flèche.
     * 
     * @return L'ImageView représentant la flèche
     */
    public ImageView getNode() {
        return node;
    }

    /**
     * Démarre l'animation de la flèche.
     * Cette méthode active le timer qui fait avancer la flèche à chaque frame.
     */
    public void startAnimation() {
        animation.start();
    }

    /**
     * Fait avancer la flèche dans sa direction et gère les collisions.
     * Cette méthode est appelée à chaque frame par l'AnimationTimer.
     */
    private void avancer() {
        // Mise à jour de la position selon le vecteur de direction et la vitesse
        posX += dirX * vitesse;
        posY += dirY * vitesse;
        node.setLayoutX(posX);
        node.setLayoutY(posY);

        // --- Détection de collision avec un bloc solide ---
        // Conversion des coordonnées en indices de tuiles de la carte
        int tuileX = (int) (posX / 32);
        int tuileY = (int) (posY / 32);

        // Vérification que la position est dans les limites de la carte
        if (carte != null && tuileX >= 0 && tuileX < carte.getLargeur()
                && tuileY >= 0 && tuileY < carte.getHauteur()) {

            // Récupération du type de bloc à cette position
            int bloc = carte.getTile(tuileY, tuileX);
            if (bloc != 0) { // 0 = vide ; tout autre bloc = obstacle
                detruire(); // détruit la flèche en cas de collision avec un bloc
                return; // on arrête tout ici
            }
        }

        // --- Collision avec un ennemi ---
        // Utilisation de la classe Tirer pour gérer les dégâts aux ennemis
        Tirer tirer = new Tirer();
        tirer.infligerDegatsSiCollision(posX, posY, cibles, overlay, degats);

        // --- Si la flèche sort de l’écran ---
        // Destruction de la flèche si elle sort des limites du jeu
        if (posX < 0 || posX > 32 * 58 || posY < 0 || posY > 32 * 32) {
            detruire();
        }
    }

    /**
     * Détruit la flèche en arrêtant son animation et en la retirant de l'interface.
     * Cette méthode est appelée quand la flèche touche un ennemi, un bloc ou sort de l'écran.
     */
    private void detruire() {
        animation.stop();
        if (node.getParent() != null) {
            ((Pane) node.getParent()).getChildren().remove(node);
        }
    }
}
