package universite_paris8.iut.youadah.projet.vue;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.youadah.projet.modele.Personnage;

public class BarreDeVieVue {

    private final Rectangle fond;
    private final Rectangle barre;
    private final Pane barreContainer;

    private final int pvMax;
    private final double largeurTotale = 40;

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

    public Pane getNode() {
        return barreContainer;
    }

    public void mettreAJourPv(int pv) {
        barre.setWidth(largeurTotale * ((double) pv / pvMax));
    }
}
