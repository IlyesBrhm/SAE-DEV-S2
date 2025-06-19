package universite_paris8.iut.youadah.projet.modele.Armes;

import universite_paris8.iut.youadah.projet.modele.Objet;
import universite_paris8.iut.youadah.projet.modele.Player;

/**
 * Classe représentant une potion dans le jeu.
 * Une potion est un objet consommable qui peut être utilisé pour restaurer les points de vie du joueur.
 */
public class Potion extends Objet {
    // Référence au joueur qui utilisera la potion
    Player joueur;
    // Type d'effet que la potion produit
    String effet;

    /**
     * Constructeur de la classe Potion.
     * 
     * @param nom Le nom de la potion
     * @param rarete La rareté de la potion qui détermine son efficacité
     * @param joueur Le joueur qui utilisera la potion
     * @param effet Le type d'effet que la potion produit
     */
    public Potion(String nom, int rarete, Player joueur, String effet) {
        super(nom, rarete, true);
        this.effet = effet;
        this.joueur = joueur;
    }

    /**
     * Méthode pour utiliser la potion.
     * Augmente les points de vie du joueur en fonction de la rareté de la potion.
     * 
     * @param x Coordonnée x (non utilisée dans cette implémentation)
     * @param y Coordonnée y (non utilisée dans cette implémentation)
     */
    public void utiliser(int x, int y) {
            joueur.incrementerPv(getRarete());
        System.out.println("utilisé");
    }
}
