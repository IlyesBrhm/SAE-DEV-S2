package universite_paris8.iut.youadah.projet.modele;

import universite_paris8.iut.youadah.projet.modele.actions.Poser;
import universite_paris8.iut.youadah.projet.vue.MapVue;

/**
 * Classe représentant un bloc dans le jeu.
 * Un bloc est un objet qui peut être placé dans le monde du jeu pour construire des structures.
 * Chaque bloc a un identifiant unique qui détermine son apparence et ses propriétés.
 */
public class Bloc extends Objet {
    // Références aux éléments du jeu nécessaires pour l'utilisation du bloc
    private GameMap carte;    // Carte du jeu où le bloc sera placé
    private MapVue carteVue;  // Vue de la carte pour l'affichage
    private Player joueur;    // Joueur qui possède le bloc

    // Identifiant unique du bloc qui détermine son type (terre, pierre, bois, etc.)
    private int id;

    /**
     * Constructeur de la classe Bloc.
     *
     * @param nom        Nom du bloc (ex: "Terre", "Pierre", "Bois")
     * @param rarete     Niveau de rareté du bloc
     * @param consomable Indique si le bloc est consommé lors de son utilisation
     * @param carte      Référence à la carte du jeu
     * @param carteVue   Référence à la vue de la carte pour l'affichage
     * @param joueur     Joueur qui possède le bloc
     * @param id         Identifiant unique du bloc qui détermine son type
     */
    public Bloc(String nom, int rarete, boolean consomable, GameMap carte, MapVue carteVue, Player joueur, int id){
        super(nom, rarete, consomable);  // Appelle le constructeur de la classe parente Objet
        this.carte = carte;
        this.carteVue = carteVue;
        this.joueur = joueur;
        this.id = id;
    }

    /**
     * Utilise le bloc en le plaçant dans le monde du jeu à la position spécifiée.
     * Cette méthode est appelée lorsque le joueur sélectionne le bloc dans son inventaire
     * et clique sur une position dans le monde pour le placer.
     *
     * @param x Coordonnée X de la position où placer le bloc
     * @param y Coordonnée Y de la position où placer le bloc
     */
    @Override
    public void utiliser(int x, int y) {
        // Crée une instance de l'action Poser et place le bloc à la position spécifiée
        Poser poser = new Poser(carte, carteVue, joueur);
        poser.poserBloc(x, y, id);
    }

    /**
     * Récupère l'identifiant unique du bloc.
     * Cet identifiant détermine le type du bloc (terre, pierre, bois, etc.).
     *
     * @return L'identifiant du bloc
     */
    public int getIdBloc() {
        return id;
    }

}
