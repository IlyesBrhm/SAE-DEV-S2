package universite_paris8.iut.youadah.projet.modele;

public class Armes extends Objet {
    private int degats;
    private String effet; // poison, feu, etc.

    public Armes(String nom, int rarete, int degats, String effet) {
        super(nom, rarete);
        this.degats = degats;
        this.effet = effet;
    }

    @Override
    public void utiliser() {
        System.out.println("Tu attaques avec " + nom + " et infliges " + degats + " dégâts.");
    }

    public int getDegats() {
        return degats;
    }

    public String getEffet() {
        return effet;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public void setEffet(String effet) {
        this.effet = effet;
    }

}
