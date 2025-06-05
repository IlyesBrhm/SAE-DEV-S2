package universite_paris8.iut.youadah.projet.modele;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private List<Objet> inventaire;
    public Inventaire(){
        inventaire = new ArrayList<>(6);
    }

    public void ajouterObjet(Objet objet){
        if (inventaire.size() == 6)
            System.out.println("Inventaire plein");
        else {
            inventaire.add(objet);
        }
    }

    public void retirerObjet(Objet objet){
        for (int i = 0; i < inventaire.size(); i++){
            if(inventaire.get(i).equals(objet)){
                inventaire.remove(objet);
            }
        }
    }

    public List<Objet> getInventaire() {
        return inventaire;
    }
}
