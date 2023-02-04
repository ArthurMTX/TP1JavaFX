package mtx.dev.tp1javafx;

import javafx.scene.paint.Color;

/**
 * Classe représentant un quadrilatère quelconque
 */
public class Quadrilatere {
    /**
     * Tableau des sommets du quadrilatère
     */
    private final InterPoint[] sommet;

    /**
     * Couleur du quadrilatère
     */
    private Color color;

    /**
     * Constructeur de la classe Losange
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public Quadrilatere(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;
    }

    /**
     * Retourne les sommets du quadrilatère
     * @return Les sommets du quadrilatère
     */
    public InterPoint[] getPoints() {
        return sommet;
    }

    /**
     * Retourne la couleur du quadrilatère
     * @return La couleur du quadrilatère
     */
    public Color getColor() {
        return color;
    }

    /**
     * Définit la couleur du quadrilatère
     * @param color La couleur du quadrilatère
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retourne le type du quadrilatère
     * @return Le type du quadrilatère
     */
    public String type() {
        return "Quadrilatère quelconque";
    }

    /**
     * Vérifie si le quadrilatère est valide, un quadrilatère quelconque est toujours valide
     * @return true
     */
    public boolean isValidShape(){
        return true;
    }

    /**
     * Affiche les informations du quadrilatère
     */
    public void affiche() {
        System.out.println(this);
    }

    /**
     * Retourne les coordonnées des sommets du quadrilatère sous forme de chaîne de caractères : A : (x : 0 ; y : 0) / B : (x : 0 ; y : 0) / C : (x : 0 ; y : 0) / D : (x : 0 ; y : 0)
     * @return Les coordonnées des sommets du quadrilatère
     */
    public String coordonnees() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (InterPoint point : sommet) {
            sb.append((char) ('A' + i - 1)).append(" : ").append("(x : ").append(point.getX()).append(" ; ").append("y : ").append(point.getY()).append(") ");
            if (i != sommet.length) {
                sb.append("/ ");
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * Retourne un descriptif du quadrilatère, avec ses sommets et ses dimensions et son type
     * @return La descriptif du quadrilatère
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}
