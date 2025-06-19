package universite_paris8.iut.youadah.projet.vue;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.youadah.projet.modele.Personnage;

/**
 * Classe responsable de l'affichage de la barre de vie d'un personnage dans l'interface graphique.
 * Affiche une barre verte qui diminue proportionnellement aux points de vie perdus.
 */
public class BarreDeVieVue {

    // Rectangle gris représentant le fond de la barre de vie
    private final Rectangle fond;
    // Rectangle vert représentant la vie actuelle
    private final Rectangle barre;
    // Conteneur pour les deux rectangles
    private final Pane barreContainer;

    // Points de vie maximum du personnage
    private final int pvMax;
    // Largeur totale de la barre de vie en pixels
    private final double largeurTotale = 40;

    /**
     * Constructeur de la classe BarreDeVieVue.
     * Initialise la barre de vie et la lie à la position du personnage.
     * 
     * @param personnage Le personnage dont on affiche la barre de vie
     */
    public BarreDeVieVue(Personnage personnage) {
        // Récupération des points de vie maximum du personnage
        this.pvMax = personnage.getPv();

        // Création du rectangle de fond gris
        fond = new Rectangle(largeurTotale, 6, Color.GRAY);
        // Création du rectangle vert représentant la vie actuelle
        barre = new Rectangle(largeurTotale, 6, Color.LIMEGREEN);

        // Création du conteneur avec les deux rectangles
        barreContainer = new Pane(fond, barre);
        // Positionnement initial de la barre au-dessus du personnage
        barreContainer.setTranslateX(personnage.getX());
        barreContainer.setTranslateY(personnage.getY() - 10);

        // Liaison position X/Y avec le personnage pour que la barre suive le personnage
        barreContainer.translateXProperty().bind(personnage.xProperty().subtract(4));
        barreContainer.translateYProperty().bind(personnage.yProperty().subtract(12));

        // Liaison de la largeur de la barre à la vie du personnage
        DoubleBinding proportionVie = new DoubleBinding() {
            {
                // On observe les changements de position qui peuvent indiquer des changements d'état
                super.bind(personnage.xProperty(), personnage.yProperty());
            }

            @Override
            protected double computeValue() {
                // Calcul de la largeur de la barre proportionnellement aux points de vie
                double pv = personnage.getPv();
                return (pv / (double) pvMax) * largeurTotale;
            }
        };

        // Initialisation de l'affichage avec les points de vie actuels
        mettreAJourPv(personnage.getPv());
    }

    /**
     * Récupère le conteneur de la barre de vie pour l'affichage dans l'interface.
     * 
     * @return Le panneau contenant la barre de vie
     */
    public Pane getNode() {
        return barreContainer;
    }

    /**
     * Met à jour la largeur de la barre de vie en fonction des points de vie actuels.
     * 
     * @param pv Points de vie actuels du personnage
     */
    public void mettreAJourPv(int pv) {
        // Calcul de la largeur proportionnelle aux points de vie
        barre.setWidth(largeurTotale * ((double) pv / pvMax));
    }
}
