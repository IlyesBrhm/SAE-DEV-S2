package universite_paris8.iut.youadah.projet.modele;


import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Bloc extends Objet {
    private Map carte;
    private MapVue carteVue;
    private Player joueur;
    private int id;

    public Bloc(String nom, int rarete, boolean consomable, Map carte, MapVue carteVue, Player joueur, int id){
        super( nom, rarete, consomable );
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.id = id;
    }

    public void utiliser(int x, int y) {
        Poser poser = new Poser(carte,carteVue,joueur);
        poser.poserBloc(x, y, id);
    }
}
