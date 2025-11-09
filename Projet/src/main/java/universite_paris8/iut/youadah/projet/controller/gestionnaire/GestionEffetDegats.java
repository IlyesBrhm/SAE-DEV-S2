package universite_paris8.iut.youadah.projet.controller.gestionnaire;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Gère les effets visuels de dégâts (ex : clignotement rouge).
 * Implémenté comme un singleton pour garantir une instance unique.
 */
public class GestionEffetDegats {

    private static GestionEffetDegats instance;

    private Pane superpositionRouge;


    private GestionEffetDegats() {}


    public static GestionEffetDegats getInstance() {
        if (instance == null) {
            instance = new GestionEffetDegats();
        }
        return instance;
    }


    public void definirSuperposition(Pane overlay) {
        this.superpositionRouge = overlay;
    }


    public void declencherClignotementRouge() {
        if (superpositionRouge == null) return;

        superpositionRouge.setOpacity(0.6);
        superpositionRouge.setVisible(true);

        FadeTransition fade = new FadeTransition(Duration.millis(400), superpositionRouge);
        fade.setFromValue(0.6);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> superpositionRouge.setVisible(false));
        fade.play();
    }
}
