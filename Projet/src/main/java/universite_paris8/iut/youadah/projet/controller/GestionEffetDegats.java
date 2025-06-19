package universite_paris8.iut.youadah.projet.controller;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
// GestionEffetDegats.java

public class GestionEffetDegats {


    private static Pane superpositionRouge;

    /**
     * Définit la superposition rouge utilisée pour les effets de dégâts.
     * @param overlay La superposition rouge à utiliser.
     */
    public static void definirSuperposition(Pane overlay) {
        superpositionRouge = overlay;
    }

    /**
     * Déclenche un clignotement rouge sur la superposition définie.
     * La superposition devient visible avec une opacité de 0.6, puis disparaît après une animation.
     */
    public static void declencherClignotementRouge() {
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