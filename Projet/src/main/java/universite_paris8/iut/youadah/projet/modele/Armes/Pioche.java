package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.environnement.Environnement;
import universite_paris8.iut.youadah.projet.modele.actions.Casser;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.objet.Bloc;
import universite_paris8.iut.youadah.projet.modele.objet.Objet;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet {
    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private Environnement environnement; // ✅ Changé de ObjetAuSol à Environnement
    private Pane playerLayer;

    public Pioche(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur,
                  Environnement environnement, Pane playerLayer) {
        super(nom, rarete, false);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.environnement = environnement;
        this.playerLayer = playerLayer;
    }

    @Override
    public void utiliser(int x, int y) {
        if (!carteVue.getBloc(x, y).equals("Vide")) {
            // Créer le bloc correspondant au type cassé
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, carteVue, joueur, carte.getTile(y, x));

            // Casser le bloc de la carte
            Casser casseur = new Casser(carte, carteVue, joueur);
            if (casseur.executer()) {
                environnement.deposer(bloc, x, y);
            }
        } else {
            System.out.println("Bloc vide, rien à casser.");
        }
    }
}