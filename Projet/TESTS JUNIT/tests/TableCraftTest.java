package universite_paris8.iut.youadah.projet.tests;

import org.junit.Before;
import org.junit.Test;
import universite_paris8.iut.youadah.projet.modele.*;
import universite_paris8.iut.youadah.projet.vue.InventaireVue;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TableCraftTest {

    private TableCraft tableCraft;
    private Inventaire inventaire;
    private Objet bois;
    private Objet pierre;
    private Objet pioche;

    @Before
    public void setUp() {
        tableCraft = new TableCraft();
        inventaire = new Inventaire();
        bois = new Bloc("Bois", 5, false, null, null, 1);
        pierre = new Bloc("Pierre", 3, false, null, null, 1);
        pioche = new Bloc("Pioche", 1, false, null, null, 1);
    }

    @Test
    public void testAjouterRecette() {
        Recette recette = new Recette(Arrays.asList(bois, pierre), pioche);
        tableCraft.ajouterRecette(recette);
        assertEquals(1, tableCraft.getRecettes().size());
        assertEquals(pioche.getNom(), tableCraft.getRecettes().get(0).getResultat().getNom());
    }

    @Test
    public void testCrafterAvecComposantsSuffisants() {
        Recette recette = new Recette(Arrays.asList(
                new Bloc("Bois", 1, false, null, null, 1),
                new Bloc("Pierre", 2, false, null, null, 1)
        ), pioche);

        tableCraft.ajouterRecette(recette);
        inventaire.ajouterObjet(new Bloc("Bois", 2, false, null, null, 1));
        inventaire.ajouterObjet(new Bloc("Pierre", 3, false, null, null, 1));

        tableCraft.crafter(recette, inventaire);

        boolean contientPioche = inventaire.getInventaire().stream()
                .anyMatch(o -> o.getNom().equals("Pioche"));
        assertTrue(contientPioche);
    }

    @Test
    public void testCrafterAvecComposantsInsuffisants() {
        Recette recette = new Recette(Arrays.asList(
                new Bloc("Bois", 1, false, null, null, 1),
                new Bloc("Pierre", 2, false, null, null, 1)
        ), pioche);

        inventaire.ajouterObjet(new Bloc("Bois", 1, false, null, null, 1));
        inventaire.ajouterObjet(new Bloc("Pierre", 1, false, null, null, 1)); // manquant 1 pierre

        int initialSize = inventaire.getInventaire().size();
        tableCraft.crafter(recette, inventaire);

        // Aucun objet ajouté si composants insuffisants
        boolean contientPioche = inventaire.getInventaire().stream()
                .anyMatch(o -> o.getNom().equals("Pioche"));
        assertFalse(contientPioche);
    }
}