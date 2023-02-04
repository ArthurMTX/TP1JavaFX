package mtx.dev.tp1javafx;

/**
 * Classe quadrilatère de type rectangle
 * @see Quadrilatere
 */
public class Rectangle extends Quadrilatere {
    /**
     * Longueur du rectangle
     */
    private final double longueur;

    /**
     * Largeur du rectangle
     */
    private final double largeur;

    /**
     * Tableau des sommets du rectangle
     */
    private final InterPoint[] sommet;

    /**
     * Constructeur de la classe Rectangle
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public Rectangle(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur
        longueur = point1.calculerDistance(point2);

        // Calculer la largeur
        largeur = point1.calculerDistance(point4);
    }

    /**
     * Vérifie si le rectangle est valide en vérifiant que les côtés correspondants sont égaux et que angles sont droits
     * @return true si le rectangle est valide, false sinon
     */
    public boolean isValidShape(){
        double side1 = sommet[0].calculerDistance(sommet[1]);
        double side2 = sommet[1].calculerDistance(sommet[2]);
        double side3 = sommet[2].calculerDistance(sommet[3]);
        double side4 = sommet[3].calculerDistance(sommet[0]);

        double angle1 = InterPoint.angle(sommet[0], sommet[1], sommet[2]);
        double angle2 = InterPoint.angle(sommet[1], sommet[2], sommet[3]);
        double angle3 = InterPoint.angle(sommet[2], sommet[3], sommet[0]);
        double angle4 = InterPoint.angle(sommet[3], sommet[0], sommet[1]);

        return side1 == side3 && side2 == side4
                && angle1 == 90 && angle2 == 90 && angle3 == 90 && angle4 == 90;
    }

    /**
     * Retourne le type du rectangle
     * @return Le type du rectangle
     */
    @Override
    public String type() {
        return "Rectangle";
    }

    /**
     * Retourne un descriptif du rectangle, avec ses sommets et ses dimensions et son type
     * @return La descriptif du rectangle
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes quatre angles sont droits. J'ai une longueur de " + longueur +
                " et une largeur de " + largeur + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}
