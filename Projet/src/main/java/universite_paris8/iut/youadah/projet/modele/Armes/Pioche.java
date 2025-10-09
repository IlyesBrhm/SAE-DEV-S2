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
    private  Environnement env;

    public Pioche(String nom, int rarete, GameMap carte, MapVue carteVue, Player joueur, ObjetAuSol objetAuSol, Pane playerLayer) {

        super(nom, rarete, false);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.objetAuSol = objetAuSol;
        this.playerLayer = playerLayer;
        this.env=new Environnement(new Pane());
    }

    public void utiliser(int x, int y){
        if (carteVue.getBloc(x,y) != "Vide") {
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, carteVue, joueur, carte.getTile(y,x));
            Casser casseur = new Casser(carte, carteVue, joueur);
            if (casseur.casserBloc(x, y))
                joueur.deposer(bloc, env);
        }
        else
            System.out.println("aaaaaaaa");
    }

}
