// Déclaration du package dans lequel se trouve cette classe
package universite_paris8.iut.youadah.projet.controller;

// Importation des classes nécessaires pour gérer les animations et les éléments graphiques JavaFX
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Classe utilitaire permettant de gérer un effet visuel de clignotement rouge à l’écran,
 * utilisé notamment pour signaler des dégâts ou un danger (par exemple lorsqu'un personnage est touché).
 */
public class GestionEffetDegats {

    // Pane statique représentant la superposition rouge utilisée pour l'effet visuel
    private static Pane superpositionRouge;

    /**
     * Définit la superposition (overlay) rouge à utiliser pour l'effet visuel.
     * Cette méthode doit être appelée une fois avec le pane concerné.
     *
     * @param overlay le pane représentant la superposition rouge
     */
    public static void definirSuperposition(Pane overlay) {
        superpositionRouge = overlay;
    }

    /**
     * Déclenche un effet de clignotement rouge à l’écran.
     * Si la superposition n’a pas été définie, l'effet est ignoré.
     * L’effet consiste à faire apparaître brièvement un rouge semi-transparent
     * qui s’estompe progressivement.
     */
    public static void declencherClignotementRouge() {
        // Si aucune superposition n'a été définie, on ne fait rien
        if (superpositionRouge == null) return;

        // On rend la superposition visible avec une opacité de 0.6 (semi-transparente)
        superpositionRouge.setOpacity(0.6);
        superpositionRouge.setVisible(true);

        // Création d'une animation de fondu (FadeTransition) qui dure 400 millisecondes
        FadeTransition fade = new FadeTransition(Duration.millis(400), superpositionRouge);
        fade.setFromValue(0.6); // Opacité de départ
        fade.setToValue(0.0);   // Opacité finale (complètement transparent)

        // Une fois l'animation terminée, on cache à nouveau la superposition
        fade.setOnFinished(e -> superpositionRouge.setVisible(false));

        // Démarrage de l'animation
        fade.play();
    }
}
