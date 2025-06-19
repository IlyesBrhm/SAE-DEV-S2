package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.modele.actions.Poser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant un bloc dans le jeu.
 * Hérite de la classe Objet.
 */
public class Bloc extends Objet {
    private GameMap carte;
    private Player joueur;
    private int id;

    /**
     * Constructeur de la classe Bloc.
     * @param nom
     * @param rarete
     * @param consomable
     * @param carte
     * @param joueur
     * @param id
     */
    public Bloc(String nom, int rarete, boolean consomable, GameMap carte,  Player joueur, int id){
        super(nom, rarete, consomable);
        this.carte = carte;
        this.joueur = joueur;
        this.id = id;
    }


    /**
     * Retourne l'ID du bloc.
     * @return ID du bloc.
     */
    @Override
    public void utiliser(int x, int y, MapVue carteVue) {
        Poser poser = new Poser(carte, carteVue, joueur);
        poser.poserBloc(x, y, id);
    }


}
