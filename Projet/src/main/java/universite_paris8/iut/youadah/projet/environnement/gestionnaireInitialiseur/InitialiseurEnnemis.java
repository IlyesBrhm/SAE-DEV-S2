package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.entite.Ennemie;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.vue.BarreDeVieVue;
import universite_paris8.iut.youadah.projet.vue.EnnemieVue;

public class InitialiseurEnnemis {

    private static final int TAILLE_TUILE = 32;

    private Ennemie ennemie;
    private EnnemieVue ennemieVue;
    private BarreDeVieVue barreVieEnnemi;

    public void initialiser(GameMap carte, Player joueur, Pane calqueCouche) {
        ennemie = new Ennemie(19 * TAILLE_TUILE, 19 * TAILLE_TUILE, 1, joueur);
        ennemie.setCarte(carte);

        ennemieVue = new EnnemieVue(ennemie);
        barreVieEnnemi = new BarreDeVieVue(ennemie);

        calqueCouche.getChildren().addAll(ennemieVue.getNode(), barreVieEnnemi.getNode());
    }

    public Ennemie getEnnemie() {
        return ennemie;
    }

    public EnnemieVue getEnnemieVue() {
        return ennemieVue;
    }

    public BarreDeVieVue getBarreVieEnnemi() {
        return barreVieEnnemi;
    }
}