package universite_paris8.iut.youadah.projet.controller.gestionnaire;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;

import java.util.HashSet;
import java.util.Set;

public class GestionnaireTouches {

    private final Set<KeyCode> touchesEnfoncees;
    private final Player joueur;
    private final Inventaire inventaire;

    private ActionClavier actionRamasser;
    private ActionClavier actionDeposer;
    private ActionClavier actionBasculerCraft;
    private ActionSelection actionSelection;

    public GestionnaireTouches(Player joueur, Inventaire inventaire) {
        this.touchesEnfoncees = new HashSet<>();
        this.joueur = joueur;
        this.inventaire = inventaire;
    }

    public void definirActionRamasser(ActionClavier action) {
        this.actionRamasser = action;
    }

    public void definirActionDeposer(ActionClavier action) {
        this.actionDeposer = action;
    }

    public void definirActionBasculerCraft(ActionClavier action) {
        this.actionBasculerCraft = action;
    }

    public void definirActionSelection(ActionSelection action) {
        this.actionSelection = action;
    }

    public void traiterAppui(KeyEvent evenement) {
        touchesEnfoncees.add(evenement.getCode());

        switch (evenement.getCode()) {
            case E -> {
                if (actionRamasser != null) {
                    actionRamasser.executer();
                }
            }
            case A -> {
                if (actionDeposer != null) {
                    actionDeposer.executer();
                }
            }
            case C -> {
                if (actionBasculerCraft != null) {
                    actionBasculerCraft.executer();
                }
            }
            case F1, F2, F3, F4, F5, F6 -> {
                if (actionSelection != null) {
                    actionSelection.executer(evenement.getCode());
                }
            }
        }
    }

    public void traiterRelachement(KeyEvent evenement) {
        touchesEnfoncees.remove(evenement.getCode());
    }

    public void appliquerMouvements() {
        boolean enMouvement = false;

        if (touchesEnfoncees.contains(KeyCode.Q) || touchesEnfoncees.contains(KeyCode.LEFT)) {
            joueur.allerGauche();
            enMouvement = true;
        }
        if (touchesEnfoncees.contains(KeyCode.D) || touchesEnfoncees.contains(KeyCode.RIGHT)) {
            joueur.allerDroite();
            enMouvement = true;
        }
        if (!enMouvement) {
            joueur.immobile();
        }

        joueur.deplacer();

        if (touchesEnfoncees.contains(KeyCode.Z) || touchesEnfoncees.contains(KeyCode.SPACE)) {
            joueur.sauter();
        }
    }

    public void reinitialiser() {
        touchesEnfoncees.clear();
    }



    public interface ActionClavier {
        void executer();
    }

    public interface ActionSelection {
        void executer(KeyCode touche);
    }
}