package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.TilePane;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class Potion extends Objet {
    Player joueur;
    String effet;

    public Potion(String nom, int rarete, Player joueur, String effet) {
        super(nom, rarete, true);
        this.effet = effet;
        this.joueur = joueur;
    }

    public void utiliser(int x, int y) {
            joueur.incrementerPv(getRarete());
        System.out.println("utilisé");
    }
}
