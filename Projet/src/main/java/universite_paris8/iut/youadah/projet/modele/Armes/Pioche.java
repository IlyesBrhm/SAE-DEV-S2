package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.modele.actions.Casser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet {
    GameMap carte;
    MapVue carteVue;
    Player joueur;
    ObjetAuSol objetAuSol;
    Pane playerLayer;

    public Pioche(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, ObjetAuSol objetAuSol, Pane playerLayer) {
        super(nom, rarete, false);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.objetAuSol = objetAuSol;
        this.playerLayer = playerLayer;
    }

    public void utiliser(int x, int y) {
        if (!carteVue.getBloc(x, y).equals("Vide")) {
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, carteVue, joueur, carte.getTile(y, x));
            Casser casseur = new Casser(carte, carteVue, joueur);
            if (casseur.casserBloc(x, y)) {
                // Utiliser une méthode de dépôt simplifiée au lieu d'Environnement
                deposerBlocAuSol(bloc, x, y);
            }
        } else {
            System.out.println("Bloc vide, rien à casser.");
        }
    }

    private void deposerBlocAuSol(Bloc bloc, int x, int y) {
        // Créer un ObjetAuSol directement et l'ajouter au Pane
        ObjetAuSol objetAuSol = new ObjetAuSol(x, y, playerLayer, bloc);
        // Pas besoin d'ajouter à une liste si ce n'est pas géré globalement
    }
}
