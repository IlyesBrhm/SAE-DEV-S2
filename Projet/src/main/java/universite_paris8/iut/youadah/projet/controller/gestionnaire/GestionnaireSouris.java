package universite_paris8.iut.youadah.projet.controller.gestionnaire;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.objet.Bloc;
import universite_paris8.iut.youadah.projet.modele.systeme.CaseInventaire;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;

public class GestionnaireSouris {

    private static final int TAILLE_TUILE = 32;

    private final Player joueur;
    private final Inventaire inventaire;

    private ActionSouris actionAttaqueEpee;
    private ActionTir actionTirArc;
    private ActionUtilisationObjet actionUtilisationObjet;

    public GestionnaireSouris(Player joueur, Inventaire inventaire) {
        this.joueur = joueur;
        this.inventaire = inventaire;
    }

    public void definirActionAttaqueEpee(ActionSouris action) {
        this.actionAttaqueEpee = action;
    }

    public void definirActionTirArc(ActionTir action) {
        this.actionTirArc = action;
    }

    public void definirActionUtilisationObjet(ActionUtilisationObjet action) {
        this.actionUtilisationObjet = action;
    }

    public void traiterClic(MouseEvent evenement) {
        if (evenement.getButton() != MouseButton.PRIMARY) {
            return;
        }

        double positionX = evenement.getX();
        double positionY = evenement.getY();

        if (actionAttaqueEpee != null) {
            actionAttaqueEpee.executer();
        }

        if (joueur.getObjetPossede() != null) {
            CaseInventaire caseReelle = inventaire.trouverCase(joueur.getObjetPossede());

            if (caseReelle != null) {
                if (caseReelle.getObjet() instanceof Arc) {
                    if (actionTirArc != null) {
                        actionTirArc.executer(positionX, positionY);
                    }
                } else {
                    if (actionUtilisationObjet != null) {
                        int tuileX = (int) (positionX / TAILLE_TUILE);
                        int tuileY = (int) (positionY / TAILLE_TUILE);
                        actionUtilisationObjet.executer(caseReelle, tuileX, tuileY);
                    }
                }
            }
        }
    }

    public boolean doitConsommerObjet(CaseInventaire caseUtilisee) {
        return caseUtilisee.getObjet() instanceof Bloc ||
                caseUtilisee.getObjet().getConsomable();
    }

    public interface ActionSouris {
        void executer();
    }

    public interface ActionTir {
        void executer(double cibleX, double cibleY);
    }

    public interface ActionUtilisationObjet {
        void executer(CaseInventaire caseObjet, int tuileX, int tuileY);
    }
}