package universite_paris8.iut.youadah.projet.modele;

public class Casser {
    private Map map;
    private Player joueur;

    public Casser(Map m, Player p) {
        this.map = m;
        joueur = p;
    }

    public boolean casserBloc(int x, int y) {
        double joueurX = joueur.getX() / 32;  // conversion pixels → tuiles
        double joueurY = joueur.getY() / 32;

        double distanceX = Math.abs(x - joueurX);
        double distanceY = Math.abs(y - joueurY);

        if (distanceX <= 2 && distanceY <= 2) {
            map.setTuile(x, y, 0);  // Remplacer par ID de tuile vide
            return true;
        }

        return false;  // trop loin
    }
}
