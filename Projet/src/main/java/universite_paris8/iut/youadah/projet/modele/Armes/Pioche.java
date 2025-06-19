package universite_paris8.iut.youadah.projet.modele.Armes;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.modele.actions.Casser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet {
    GameMap carte;
    Player joueur;
    ObjetAuSol objetAuSol;
    Pane playerLayer;

    public Pioche(String nom, int rarete, GameMap carte, Player joueur, ObjetAuSol objetAuSol, Pane playerLayer) {

        super(nom, rarete, false);
        this.carte = carte;
        this.joueur = joueur;
        this.objetAuSol = objetAuSol;
        this.playerLayer = playerLayer;
    }

    @Override
    public void utiliser(int x, int y, MapVue carteVue) {
        if (carteVue.getBloc(x,y) != "Vide") {
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, joueur, carte.getTile(y,x));
            Casser casseur = new Casser(carte, carteVue, joueur);
            casseur.casserBloc(x, y);
            if (casseur.isCasseValide())
                objetAuSol.deposer(bloc, x,y, playerLayer);
        }
        else
            System.out.println("aaaaaaaa");
    }

}
