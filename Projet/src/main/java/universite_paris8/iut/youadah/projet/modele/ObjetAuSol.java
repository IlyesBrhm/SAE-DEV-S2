package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;

import java.util.ArrayList;

/**
 * Classe représentant un objet au sol dans le jeu.
 * Un objet au sol est créé à une position spécifique (x, y) et est associé à une vue d'objet.
 * Il peut être ramassé par le joueur et ajouté à son inventaire.
 */
public class ObjetAuSol {
    private final int TAILLE_TUILE = 32;
    private static final ArrayList<ObjetAuSol> objetsAuSol = new ArrayList<>();

    private int x, y;
    private ObjetVue objetVue;



    /**
     * Constructeur de la classe ObjetAuSol.
     * Crée un objet au sol à la position (x, y) dans le pane spécifié.
     *
     * @param x      La coordonnée x de l'objet au sol.
     * @param y      La coordonnée y de l'objet au sol.
     * @param pane   Le pane dans lequel l'objet sera affiché.
     * @param objet  L'objet associé à cet objet au sol.
     */
    public ObjetAuSol(int x, int y, Pane pane, Objet objet) {
        this.x = x;
        this.y = y;
        this.objetVue = new ObjetVue(objet);

        objetVue.getImageView().setFitWidth(26);
        objetVue.getImageView().setFitHeight(26);
        objetVue.getImageView().setLayoutX(x * TAILLE_TUILE);
        objetVue.getImageView().setLayoutY(y * TAILLE_TUILE);

        objetsAuSol.add(this);
        pane.getChildren().add(objetVue.getImageView());
    }


    /**
     * Retourne la liste des objets au sol.
     *
     * @return La liste des objets au sol.
     */
    public static boolean ramasser(Player joueur, Inventaire inventaire, Pane pane) {
        int joueurX = (int) (joueur.getX() / 32);
        int joueurY = (int) (joueur.getY() / 32);

        ArrayList<ObjetAuSol> aRamasser = new ArrayList<>();
        boolean auMoinsUnRamasse = false;

        for (ObjetAuSol o : new ArrayList<>(objetsAuSol)) {
            if (o.x == joueurX && o.y == joueurY) {
                boolean ajoute = inventaire.ajouterObjet(o.objetVue.getObjet());
                if (ajoute) {
                    pane.getChildren().remove(o.objetVue.getImageView());
                    aRamasser.add(o);
                    auMoinsUnRamasse = true;
                } else {
                    System.out.println("Inventaire plein : impossible de ramasser " + o.objetVue.getObjet().getNom());
                }
            }
        }

        objetsAuSol.removeAll(aRamasser);
        return auMoinsUnRamasse;
    }


/**
     * Dépose un objet au sol à la position du joueur.
     *
     * @param objet  L'objet à déposer.
     * @param joueur Le joueur qui dépose l'objet.
     * @param pane   Le pane dans lequel l'objet sera affiché.
     */
    public static void deposerJoueur(Objet objet, Player joueur, Pane pane) {
        int x = (int) (joueur.getX() / 32);
        int y = (int) (joueur.getY() / 32);
        new ObjetAuSol(x, y, pane, objet);
    }


    /**
     * Dépose un objet au sol à une position spécifique (x, y).
     *
     * @param objet L'objet à déposer.
     * @param x     La coordonnée x où l'objet sera déposé.
     * @param y     La coordonnée y où l'objet sera déposé.
     * @param pane  Le pane dans lequel l'objet sera affiché.
     */
    public static void deposer(Objet objet, int x, int y, Pane pane) {
        new ObjetAuSol(x, y, pane, objet);
    }
}
