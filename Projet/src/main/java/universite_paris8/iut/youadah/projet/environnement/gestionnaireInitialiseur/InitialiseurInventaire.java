package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.environnement.Environnement;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Armes.Epee;
import universite_paris8.iut.youadah.projet.modele.Armes.Pioche;
import universite_paris8.iut.youadah.projet.modele.Armes.Potion;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.objet.Bloc;
import universite_paris8.iut.youadah.projet.modele.systeme.CaseInventaire;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;
import universite_paris8.iut.youadah.projet.vue.InventaireVue;
import universite_paris8.iut.youadah.projet.vue.MapVue;

public class InitialiseurInventaire {

    private Inventaire inventaire;
    private InventaireVue inventaireVue;
    private ImageView selectionInventaire;

    public void initialiser(Pane interfaceJeu, GameMap carte, MapVue carteVue,
                            Player joueur, Environnement environnement, Pane calqueCouche) {
        inventaire = new Inventaire();

        ajouterObjetsDepart(carte, carteVue, joueur, environnement, calqueCouche);

        inventaireVue = new InventaireVue(interfaceJeu, inventaire);
        inventaireVue.afficherInventaire();
        inventaireVue.maj();

        creerSelectionVisuelle();
        deposerObjetsAuSol(environnement, carte, carteVue, joueur, calqueCouche);
    }

    private void ajouterObjetsDepart(GameMap carte, MapVue carteVue, Player joueur,
                                     Environnement environnement, Pane calqueCouche) {
        inventaire.ajouterObjet(new CaseInventaire(
                new Pioche("pioche", 1, carte, carteVue, joueur, environnement, calqueCouche)));
        inventaire.ajouterObjet(new CaseInventaire(
                new Potion("potionVie", 1, joueur, "vie")));
        inventaire.ajouterObjet(new CaseInventaire(
                new Bloc("Terre", 1, false, carte, carteVue, joueur, 2)));
        inventaire.ajouterObjet(new CaseInventaire(
                new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3)));
        inventaire.ajouterObjet(new CaseInventaire(
                new Epee("Epee", 1, carte, carteVue, joueur, null)));
        inventaire.ajouterObjet(new CaseInventaire(
                new Arc("Arc", 1, carte, carteVue, joueur, calqueCouche)));
    }

    private void creerSelectionVisuelle() {
        Image image = new Image(getClass().getResource("/images/inventory selected.png").toExternalForm());
        selectionInventaire = new ImageView(image);
        selectionInventaire.setFitHeight(64);
        selectionInventaire.setFitWidth(64);
    }

    private void deposerObjetsAuSol(Environnement environnement, GameMap carte,
                                    MapVue carteVue, Player joueur, Pane calqueCouche) {
        environnement.deposer(
                new Pioche("pioche", 1, carte, carteVue, joueur, environnement, calqueCouche),
                5, 19
        );
    }

    public void reinitialiser(GameMap carte, MapVue carteVue, Player joueur,
                              Environnement environnement, Pane calqueCouche) {
        inventaire.getInventaire().clear();
        ajouterObjetsDepart(carte, carteVue, joueur, environnement, calqueCouche);
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public InventaireVue getInventaireVue() {
        return inventaireVue;
    }

    public ImageView getSelectionInventaire() {
        return selectionInventaire;
    }
}