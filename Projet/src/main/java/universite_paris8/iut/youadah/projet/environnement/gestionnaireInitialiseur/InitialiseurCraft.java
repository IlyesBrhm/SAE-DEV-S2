package universite_paris8.iut.youadah.projet.environnement.gestionnaireInitialiseur;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.environnement.Environnement;
import universite_paris8.iut.youadah.projet.modele.Armes.Arc;
import universite_paris8.iut.youadah.projet.modele.Armes.Pioche;
import universite_paris8.iut.youadah.projet.modele.Armes.Potion;
import universite_paris8.iut.youadah.projet.modele.entite.Player;
import universite_paris8.iut.youadah.projet.modele.monde.GameMap;
import universite_paris8.iut.youadah.projet.modele.objet.Bloc;
import universite_paris8.iut.youadah.projet.modele.systeme.CaseInventaire;
import universite_paris8.iut.youadah.projet.modele.systeme.Inventaire;
import universite_paris8.iut.youadah.projet.modele.systeme.Recette;
import universite_paris8.iut.youadah.projet.modele.systeme.TableCraft;
import universite_paris8.iut.youadah.projet.vue.InventaireVue;
import universite_paris8.iut.youadah.projet.vue.MapVue;
import universite_paris8.iut.youadah.projet.vue.TableCraftVue;

import java.util.List;

public class InitialiseurCraft {

    private TableCraft tableCraft;
    private TableCraftVue tableCraftVue;
    private Pane panneauCraft;
    private boolean visible = false;

    public void initialiser(Pane interfaceJeu, Inventaire inventaire, InventaireVue inventaireVue,
                            GameMap carte, MapVue carteVue, Player joueur,
                            Environnement environnement, Pane calqueCouche) {
        creerPanneauCraft(interfaceJeu);

        tableCraft = new TableCraft();
        ajouterRecettes(inventaire, carte, carteVue, joueur, environnement, calqueCouche);

        tableCraftVue = new TableCraftVue(panneauCraft, tableCraft, inventaire, inventaireVue, interfaceJeu);
    }

    private void creerPanneauCraft(Pane interfaceJeu) {
        panneauCraft = new Pane();
        panneauCraft.setVisible(false);
        panneauCraft.setLayoutX(300);
        panneauCraft.setLayoutY(150);
        panneauCraft.setPrefSize(400, 200);
        panneauCraft.setStyle("-fx-background-color: rgba(30,30,30,0.85); -fx-border-color: white; -fx-border-width: 2px;");
        interfaceJeu.getChildren().add(panneauCraft);
    }

    private void ajouterRecettes(Inventaire inventaire, GameMap carte, MapVue carteVue,
                                 Player joueur, Environnement environnement, Pane calqueCouche) {
        if (inventaire.getInventaire().size() < 4) {
            return;
        }

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        inventaire.getInventaire().get(2),
                        inventaire.getInventaire().get(3)
                ),
                new CaseInventaire(new Potion("potionVie", 5, joueur, "vie"))
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Pierre", 2, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Pioche("pioche", 1, carte, carteVue, joueur, environnement, calqueCouche))
        ));

        tableCraft.ajouterRecette(new Recette(
                List.of(
                        new CaseInventaire(new Bloc("Bois", 2, false, carte, carteVue, joueur, 2)),
                        new CaseInventaire(new Bloc("Pierre", 1, false, carte, carteVue, joueur, 3))
                ),
                new CaseInventaire(new Arc("Arc", 1, carte, carteVue, joueur, calqueCouche))
        ));
    }

    public void basculerVisibilite() {
        visible = !visible;
        panneauCraft.setVisible(visible);
        if (visible) {
            tableCraftVue.afficher();
        }
    }

    public boolean estVisible() {
        return visible;
    }

    public TableCraft getTableCraft() {
        return tableCraft;
    }

    public TableCraftVue getTableCraftVue() {
        return tableCraftVue;
    }

    public Pane getPanneauCraft() {
        return panneauCraft;
    }
}