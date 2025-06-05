package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Pioche extends Objet{
    Map carte;
    MapVue carteVue;
    Player joueur;
    ObjetAuSol objetAuSol;
    Pane playerLayer;
    
    public Pioche(String nom, int rarete, Map carte, MapVue carteVue, Player joueur, ObjetAuSol objetAuSol, Pane playerLayer) {
        super(nom, rarete, false);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.objetAuSol = objetAuSol;
        this.playerLayer = playerLayer;
    }

    public void utiliser(int x, int y){
        if (carteVue.getBloc(x,y) != "Vide") {
            Bloc bloc = new Bloc(carteVue.getBloc(x, y), 1, false, carte, carteVue, joueur, carte.getTile(y,x));
            Casser casseur = new Casser(carte, carteVue, joueur);
            casseur.casserBloc(x, y);
            objetAuSol.deposer(bloc, x,y, playerLayer);
        }
        else
            System.out.println("aaaaaaaa");
    }

}
