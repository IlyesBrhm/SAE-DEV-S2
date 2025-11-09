package universite_paris8.iut.youadah.projet.modele.actions;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.modele.entite.Personnage;

import java.util.List;

public class Tirer {

    private final GestionEffetDegats effetDegats = GestionEffetDegats.getInstance();

    public void infligerDegatsSiCollision(double xFleche, double yFleche,
                                          List<Personnage> ennemis, Pane overlay, int degats) {

        for (Personnage cible : ennemis) {
            if (cible.getVie().estMort()) continue;

            double distance = Math.hypot(cible.getX() - xFleche, cible.getY() - yFleche);
            if (distance < 25) {
                if (cible.getVie().getPvArmure() > 0)
                    cible.getVie().decrementerPvArmure(degats);
                else
                    cible.getVie().decrementerPv(degats);

                effetDegats.definirSuperposition(overlay);
                effetDegats.declencherClignotementRouge();
                System.out.println("🏹 Flèche touchée !");
                break;
            }
        }
    }
}
