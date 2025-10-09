package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Environnement {

    private static final int TAILLE_TUILE = 32;

    private final Pane pane;
    private final List<ObjetAuSol> objetsAuSol;

    public Environnement(Pane pane) {
        this.pane = pane;
        this.objetsAuSol = new ArrayList<>();
    }


    public ObjetAuSol deposer(Objet objet, int xTuile, int yTuile) {
        ObjetAuSol o = new ObjetAuSol(xTuile, yTuile, pane, objet);
        objetsAuSol.add(o);
        return o;
    }


    public ObjetAuSol deposerDepuisJoueur(Objet objet, Player joueur) {
        int x = (int) (joueur.getX() / TAILLE_TUILE);
        int y = (int) (joueur.getY() / TAILLE_TUILE);
        return deposer(objet, x, y);
    }



    public boolean ramasserSurTuile(int xTuile, int yTuile, Inventaire inventaire) {
        boolean ramasse = false;
        Iterator<ObjetAuSol> it = objetsAuSol.iterator();
        while (it.hasNext()) {
            ObjetAuSol o = it.next();
            if (o.getXTuile() == xTuile && o.getYTuile() == yTuile) {
                boolean ajoute = inventaire.ajouterObjet(new CaseInventaire(o.getObjet()));
                if (ajoute) {
                    pane.getChildren().remove(o.getObjetVue().getImageView());
                    it.remove();
                    ramasse = true;
                } else {
                    System.out.println("Inventaire plein : impossible de ramasser " + o.getObjet().getNom());
                }
            }
        }
        return ramasse;
    }


    public boolean ramasserAutourDuJoueur(Player joueur, Inventaire inventaire) {
        int x = (int) (joueur.getX() / TAILLE_TUILE);
        int y = (int) (joueur.getY() / TAILLE_TUILE);
        return ramasserSurTuile(x, y, inventaire);
    }
}
