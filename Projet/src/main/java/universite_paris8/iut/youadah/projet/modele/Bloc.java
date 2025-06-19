package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.modele.actions.Poser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Bloc extends Objet {
    private GameMap carte;
    private Player joueur;
    private int id;

    public Bloc(String nom, int rarete, boolean consomable, GameMap carte,  Player joueur, int id){
        super(nom, rarete, consomable);
        this.carte = carte;
        this.joueur = joueur;
        this.id = id;
    }

    @Override
    public void utiliser(int x, int y, MapVue carteVue) {
        Poser poser = new Poser(carte, carteVue, joueur);
        poser.poserBloc(x, y, id);
    }

    public int getIdBloc() {
        return id;
    }

}
