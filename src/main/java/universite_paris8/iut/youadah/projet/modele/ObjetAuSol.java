package universite_paris8.iut.youadah.projet.modele;


import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;
import java.util.ArrayList;


public class ObjetAuSol {
    private  final int TAILLE_TUILE = 32;
    private  final ArrayList<ObjetAuSol> objetsAuSol = new ArrayList<>();
    private ObjetVue objetVue;
    private int x, y;

    public ObjetAuSol(ObjetVue objetVue, int x, int y, Pane pane) {
        this.objetVue = objetVue;
        this.x = x;
        this.y = y;

        this.objetVue.getImageView().setFitWidth(TAILLE_TUILE);
        this.objetVue.getImageView().setFitHeight(TAILLE_TUILE);
        this.objetVue.getImageView().setLayoutX(x * TAILLE_TUILE);
        this.objetVue.getImageView().setLayoutY(y * TAILLE_TUILE);

        objetsAuSol.add(this);
        pane.getChildren().add(objetVue.getImageView());
    }

    public  void ramasser(Player joueur, Inventaire inventaire, Pane pane) {
        int joueurX = (int) (joueur.getX() / TAILLE_TUILE);
        int joueurY = (int) (joueur.getY() / TAILLE_TUILE);

        ArrayList<ObjetAuSol> aRamasser = new ArrayList<>();

        for (ObjetAuSol o : objetsAuSol) {
            if (o.x == joueurX && o.y == joueurY) {
                inventaire.ajouterObjet(o.objetVue.getObjet());
                pane.getChildren().remove(o.objetVue.getImageView());
                aRamasser.add(o);
            }
        }
        objetsAuSol.removeAll(aRamasser);
    }

    public  void deposer(Objet objet, Player joueur, Pane pane) {
        int x = (int) (joueur.getX() / TAILLE_TUILE);
        int y = (int) (joueur.getY() / TAILLE_TUILE);

        ObjetVue vue = new ObjetVue(objet);
        ObjetAuSol nouveau = new ObjetAuSol(vue, x, y, pane);
        objetsAuSol.add(nouveau);
    }
}
