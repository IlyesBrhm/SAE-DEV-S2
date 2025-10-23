// Taper.java
package universite_paris8.iut.youadah.projet.modele.actions;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.controller.GestionEffetDegats;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Personnage;
import universite_paris8.iut.youadah.projet.modele.Player;
import java.util.List;

public class Taper {

    private static final double PORTEE_EPEE = 40.0;
    private static final int DEGATS_EPEE = 1;

    public void attaquerAvecEpee(Player joueur, List<Personnage> cibles, Pane overlay) {
        if (!possedeEpee(joueur)) return;
        cibles.stream()
                .filter(cible -> estCibleValide(joueur, cible))
                .filter(cible -> estCibleTouchee(joueur, cible))
                .forEach(cible -> appliquerDegatsEtEffets(cible, overlay));
    }

    // Méthodes simples, chacune fait une seule chose

    private boolean possedeEpee(Player joueur) {
        return joueur.getObjetPossede() instanceof Epee;
    }

    private boolean estCibleValide(Player joueur, Personnage cible) {
        return cible != joueur;
    }

    private boolean estCibleTouchee(Player joueur, Personnage cible) {
        double posXJ = joueur.getX();
        double posYJ = joueur.getY();
        double posXC = cible.getX();
        double posYC = cible.getY();
        boolean droite = joueur.estsVersLaDroite();

        boolean dansPortee = droite
                ? posXC > posXJ && posXC < posXJ + PORTEE_EPEE
                : posXC < posXJ && posXC > posXJ - PORTEE_EPEE;

        return dansPortee && estMemeHauteur(posYJ, posYC);
    }

    private boolean estMemeHauteur(double yJoueur, double yCible) {
        return Math.abs(yCible - yJoueur) < 32;
    }

    private void appliquerDegatsEtEffets(Personnage cible, Pane overlay) {
        infligerDegats(cible);
        afficherEffetDegats(overlay);
    }

    private void infligerDegats(Personnage cible) {
        if (cible.getVie().getPvArmure() > 0)
            cible.getVie().decrementerPvArmure(DEGATS_EPEE);
        else
            cible.getVie().decrementerPv(DEGATS_EPEE);
    }

    private void afficherEffetDegats(Pane overlay) {
        GestionEffetDegats.definirSuperposition(overlay);
        GestionEffetDegats.declencherClignotementRouge();
    }
}



//


//public class Taper {
//
//    private static final double PORTEE_EPEE = 40.0;
//    private static final int DEGATS_EPEE = 1;
//
//    public Taper() {}
//
//    public void attaquerAvecEpee(Player joueur, List<Personnage> cibles, Pane overlay) {
//        Object objet = joueur.getObjetPossede();
//        if (objet == null || !(objet instanceof Epee)) return;
//
//        double positionXJoueur = joueur.getX();
//        double positionYJoueur = joueur.getY();
//        boolean regardeVersLaDroite = joueur.estsVersLaDroite();
//
//        for (Personnage cible : cibles) {
//            if (cible == joueur) continue;
//
//            double positionXCible = cible.getX();
//            double positionYCible = cible.getY();
//
//            boolean cibleDevant = regardeVersLaDroite
//                    ? positionXCible > positionXJoueur && positionXCible < positionXJoueur + PORTEE_EPEE
//                    : positionXCible < positionXJoueur && positionXCible > positionXJoueur - PORTEE_EPEE;
//
//            boolean memeHauteur = Math.abs(positionYCible - positionYJoueur) < 32;
//
//            if (cibleDevant && memeHauteur) {
//                System.out.println("TOUCHÉ !");
//                infligerDegats(cible);
//                GestionEffetDegats.definirSuperposition(overlay);
//                GestionEffetDegats.declencherClignotementRouge();
//            }
//
//        }
//    }
//
//    private void infligerDegats(Personnage cible) {
//        if (cible.getVie().getPvArmure() > 0) {
//            cible.getVie().decrementerPvArmure(DEGATS_EPEE);
//        } else {
//            cible.getVie().decrementerPv(DEGATS_EPEE);
//        }
//    }
//} // Fin Taper.java
