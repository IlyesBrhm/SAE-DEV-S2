
package universite_paris8.iut.youadah.projet.modele.Strategies;

import universite_paris8.iut.youadah.projet.modele.entite.Ennemie;
import universite_paris8.iut.youadah.projet.modele.entite.Player;

public interface Strategies {

    void deplacer(Ennemie ennemie, Player joueur);

}
