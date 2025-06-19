package universite_paris8.iut.youadah.projet.vue;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.youadah.projet.modele.Personnage;

/**
 * Classe représentant la vue de la barre de vie d'un personnage.
 * Affiche une barre de vie qui se met à jour en fonction des points de vie du personnage.
 */
public class BarreDeVieVue {

    private final Rectangle fond;
    private final Rectangle barre;
    private final Pane barreContainer;

    private final int pvMax;
    private final double largeurTotale = 40;


    /**
     * Constructeur de la barre de vie.
     * @param personnage Le personnage dont on veut afficher la barre de vie.
     */
    public BarreDeVieVue(Personnage personnage) {
        this.pvMax = personnage.getPv();

        fond = new Rectangle(largeurTotale, 6, Color.GRAY);
        barre = new Rectangle(largeurTotale, 6, Color.LIMEGREEN);

        barreContainer = new Pane(fond, barre);
        barreContainer.setTranslateX(personnage.getX());
        barreContainer.setTranslateY(personnage.getY() - 10);

        // Liaison position X/Y avec le personnage
        barreContainer.translateXProperty().bind(personnage.xProperty().subtract(4));
        barreContainer.translateYProperty().bind(personnage.yProperty().subtract(12));

        // Liaison de la largeur de la barre à la vie
        DoubleBinding proportionVie = new DoubleBinding() {
            {
                super.bind(personnage.xProperty(), personnage.yProperty());
            }

            @Override
            protected double computeValue() {
                double pv = personnage.getPv();
                return (pv / (double) pvMax) * largeurTotale;
            }
        };

        mettreAJourPv(personnage.getPv());

    }

    /**
     * Retourne le nœud de la barre de vie, pour l'ajouter à la scène.
     * @return Le pane contenant la barre de vie.
     *
     */
    public Pane getNode() {
        return barreContainer;
    }

    /**
     * Met à jour la largeur de la barre de vie en fonction des points de vie actuels.
     * @param pv Le nombre de points de vie actuel du personnage.
     */
    public void mettreAJourPv(int pv) {
        barre.setWidth(largeurTotale * ((double) pv / pvMax));
    }
}
