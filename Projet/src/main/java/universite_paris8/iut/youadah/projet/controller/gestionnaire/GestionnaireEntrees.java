package universite_paris8.iut.youadah.projet.controller.gestionnaire;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;

public class GestionnaireEntrees {

    private final GestionnaireTouches gestionnaireTouches;
    private final GestionnaireSouris gestionnaireSouris;

    public GestionnaireEntrees(Player joueur, Inventaire inventaire) {
        this.gestionnaireTouches = new GestionnaireTouches(joueur, inventaire);
        this.gestionnaireSouris = new GestionnaireSouris(joueur, inventaire);
    }

    public void traiterAppuiTouche(KeyEvent evenement) {
        gestionnaireTouches.traiterAppui(evenement);
    }

    public void traiterRelachementTouche(KeyEvent evenement) {
        gestionnaireTouches.traiterRelachement(evenement);
    }

    public void traiterClicSouris(MouseEvent evenement) {
        gestionnaireSouris.traiterClic(evenement);
    }

    public void appliquerMouvements() {
        gestionnaireTouches.appliquerMouvements();
    }

    public void reinitialiser() {
        gestionnaireTouches.reinitialiser();
    }

    public GestionnaireTouches getTouches() {
        return gestionnaireTouches;
    }

    public GestionnaireSouris getSouris() {
        return gestionnaireSouris;
    }
}