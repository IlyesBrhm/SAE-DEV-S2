package universite_paris8.iut.youadah.projet.modele.actions;

import universite_paris8.iut.youadah.projet.controller.gestionnaire.GestionEffetDegats;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.entite.Personnage;
import universite_paris8.iut.youadah.projet.modele.entite.Player;

import java.util.List;

public class Taper implements ActionStrategies {

    private static final double PORTEE_EPEE = 40.0;
    private static final int DEGATS_EPEE = 1;

    private final Player joueur;
    private final List<Personnage> cibles;
    private final Pane overlay;
    private final GestionEffetDegats degats ;

    public Taper(Player joueur, List<Personnage> cibles, Pane overlay) {
        this.joueur = joueur;
        this.cibles = cibles;
        this.overlay = overlay;
        this.degats = GestionEffetDegats.getInstance();
    }



    @Override
    public boolean executer() {
        Object objet = joueur.getObjetPossede();
        if (!(objet instanceof Epee)) return false;

        double xJoueur = joueur.getX();
        double yJoueur = joueur.getY();
        boolean regardeDroite = joueur.estsVersLaDroite();

        for (Personnage cible : cibles) {
            if (cible == joueur || cible.getVie().estMort()) continue;

            double xCible = cible.getX();
            double yCible = cible.getY();

            boolean cibleDevant = regardeDroite
                    ? xCible > xJoueur && xCible < xJoueur + PORTEE_EPEE
                    : xCible < xJoueur && xCible > xJoueur - PORTEE_EPEE;

            boolean memeHauteur = Math.abs(yCible - yJoueur) < 32;

            if (cibleDevant && memeHauteur) {
                infligerDegats(cible);
                degats.definirSuperposition(overlay);
                degats.declencherClignotementRouge();
                System.out.println("💥 Touché à l’épée !");
            }
        }
        return true;
    }

    private void infligerDegats(Personnage cible) {
        if (cible.getVie().getPvArmure() > 0)
            cible.getVie().decrementerPvArmure(DEGATS_EPEE);
        else
            cible.getVie().decrementerPv(DEGATS_EPEE);
    }
}
