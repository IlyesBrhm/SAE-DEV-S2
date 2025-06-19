package universite_paris8.iut.youadah.projet.modele;

/**
 * Classe abstraite représentant un objet dans le jeu.
 * Cette classe sert de base pour tous les objets qui peuvent être utilisés, collectés ou manipulés.
 */
public abstract class Objet {

    // Propriétés de base d'un objet
    private String nom;        // Nom de l'objet
    private int rarete;        // Niveau de rareté de l'objet (plus la valeur est élevée, plus l'objet est rare)
    private boolean consomable; // Indique si l'objet peut être consommé (utilisé une seule fois)
    private int quantite;      // Nombre d'exemplaires de cet objet

    /**
     * Constructeur pour créer un nouvel objet
     * @param nom Nom de l'objet
     * @param rarete Niveau de rareté de l'objet
     * @param consomable Indique si l'objet est consommable
     */
    public Objet(String nom, int rarete, boolean consomable) {
        this.nom = nom;
        this.rarete = rarete;
        this.consomable = consomable;
        this.quantite = 1; // par défaut 1
    }

    /**
     * Récupère le nom de l'objet
     * @return Le nom de l'objet
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le niveau de rareté de l'objet
     * @return La rareté de l'objet
     */
    public int getRarete() {
        return rarete;
    }

    /**
     * Vérifie si l'objet est consommable
     * @return true si l'objet est consommable, false sinon
     */
    public boolean getConsomable() {
        return consomable;
    }

    /**
     * Récupère la quantité actuelle de cet objet
     * @return La quantité de l'objet
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Définit la quantité de cet objet
     * @param quantite La nouvelle quantité à définir
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Augmente la quantité de l'objet
     * @param n Le nombre à ajouter à la quantité actuelle
     */
    public void incrementerQuantite(int n) {
        this.quantite += n;
    }

    /**
     * Diminue la quantité de l'objet
     * @param n Le nombre à soustraire de la quantité actuelle
     */
    public void decrementerQuantite(int n) {
        this.quantite -= n;
    }

    /**
     * Méthode abstraite pour utiliser l'objet à une position spécifique
     * Chaque type d'objet doit implémenter cette méthode selon son comportement
     * @param x Coordonnée X où l'objet est utilisé
     * @param y Coordonnée Y où l'objet est utilisé
     */
    public abstract void utiliser(int x, int y);

    /**
     * Représentation textuelle de l'objet
     * @return Une chaîne contenant le nom, la quantité et la rareté de l'objet
     */
    @Override
    public String toString() {
        return nom + " (x" + quantite + ", Rareté : " + rarete + ")";
    }

    /**
     * Vérifie si cet objet est égal à un autre objet
     * Deux objets sont considérés égaux s'ils ont le même nom
     * @param o L'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objet)) return false;
        Objet autre = (Objet) o;
        return nom.equals(autre.nom); // même nom = même type d’objet
    }

    /**
     * Calcule le code de hachage de l'objet basé sur son nom
     * @return Le code de hachage
     */
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
