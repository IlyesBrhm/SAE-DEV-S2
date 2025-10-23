package universite_paris8.iut.youadah.projet.modele.Strategies;

import universite_paris8.iut.youadah.projet.modele.Ennemie;
import universite_paris8.iut.youadah.projet.modele.Player;

public class StrategieFuite implements Strategies {

    @Override
    public void deplacer(Ennemie ennemie, Player joueur) {
        if (ennemie.getX() < joueur.getX()) {
            ennemie.deplacerDroite();
        } else if (ennemie.getX() > joueur.getX()) {
            ennemie.deplacerGauche();
        }
    }
}
