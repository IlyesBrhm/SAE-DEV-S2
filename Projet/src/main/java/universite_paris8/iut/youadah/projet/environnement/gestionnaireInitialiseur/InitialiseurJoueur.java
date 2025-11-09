package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.vue.BouclierVue;
import universite_paris8.iut.youadah.projet.vue.CoeurVue;
import universite_paris8.iut.youadah.projet.vue.PlayerVue;

public class InitialiseurJoueur {

    private static final int TAILLE_TUILE = 32;

    private Player joueur;
    private PlayerVue joueurVue;
    private CoeurVue coeurVue;
    private BouclierVue bouclierVue;

    public void initialiser(GameMap carte, Pane calqueCouche, Pane interfaceJeu) {
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueur.setCarte(carte);

        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPv());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), interfaceJeu);
        bouclierVue.getBarreBouclier().setLayoutY(40);

        calqueCouche.getChildren().addAll(
                bouclierVue.getBarreBouclier(),
                joueurVue.getNode(),
                coeurVue.getBarreVie()
        );
    }

    public void reinitialiser(GameMap carte, Pane interfaceJeu) {
        joueur = new Player(5 * TAILLE_TUILE, 19 * TAILLE_TUILE);
        joueur.setCarte(carte);

        joueurVue = new PlayerVue(joueur);
        coeurVue = new CoeurVue(joueur.getVie().getPv());
        bouclierVue = new BouclierVue(joueur.getVie().getPvArmure(), interfaceJeu);
        bouclierVue.getBarreBouclier().setLayoutY(40);
    }

    public Player getJoueur() {
        return joueur;
    }

    public PlayerVue getJoueurVue() {
        return joueurVue;
    }

    public CoeurVue getCoeurVue() {
        return coeurVue;
    }

    public BouclierVue getBouclierVue() {
        return bouclierVue;
    }
}