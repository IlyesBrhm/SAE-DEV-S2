package universite_paris8.iut.youadah.projet.modele.actions;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.modele.Personnage;

import java.util.List;

/**
 * Classe représentant l'action de tir à l'arc.
 * Détecte la collision d'une flèche avec des ennemis et applique les dégâts.
 */
public class Tirer {

    public Tirer() {}

    /**
     * Inflige des dégâts si une flèche entre en collision avec un ennemi.
     * @param xFleche position X de la flèche
     * @param yFleche position Y de la flèche
     * @param ennemis liste des cibles potentielles (Personnage)
     * @param overlay effet visuel (superposition rouge)
     * @param degats  quantité de dégâts infligés
     */
    public void infligerDegatsSiCollision(double xFleche, double yFleche, List<Personnage> ennemis, Pane overlay, int degats) {
        for (Personnage cible : ennemis) {
            // ⛔ On ignore les morts
            if (cible.estMort()) continue;

            double distance = Math.hypot(cible.getX() - xFleche, cible.getY() - yFleche);
            if (distance < 20) {
                if (cible.getPvArmure() > 0) {
                    cible.decrementerPvArmure(degats);
                } else {
                    cible.decrementerPv(degats);
                }

                // ✅ Effet de dégât visuel uniquement si l’ennemi est encore vivant
                GestionEffetDegats.definirSuperposition(overlay);
                GestionEffetDegats.declencherClignotementRouge();
                break; // Touche une seule cible
            }
        }
    }

}
