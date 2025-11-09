package universite_paris8.iut.youadah.projet.modele.objet;

import universite_paris8.iut.youadah.projet.modele.actions.Poser;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Bloc extends Objet {
    private GameMap carte;
    private MapVue carteVue;
    private Player joueur;
    private int id;

    public Bloc(String nom, int rarete, boolean consomable, GameMap carte, MapVue carteVue, Player joueur, int id){
        super(nom, rarete, consomable);
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.id = id;
    }

    @Override
    public void utiliser(int x, int y) {
        Poser poser = new Poser(carte, carteVue, joueur, x, y, id);// jai modifier sa
        poser.executer();// jai modifier sa
    }

    public int getIdBloc() {
        return id;
    }

}