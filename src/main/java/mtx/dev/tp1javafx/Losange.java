package mtx.dev.tp1javafx;

/**
 * @author Maxime MONET
 * @author Arthur PALY
 */

/**
 * Classe quadrilatère de type losange
 * @see Quadrilatere
 */
public class Losange extends Quadrilatere {
    /**
     * Longueur du côté du losange
     */
    private final double longueurCote;

    /**
     * Tableau des sommets du losange
     */
    private final InterPoint[] sommet;

    /**
     * Constructeur de la classe Losange
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public Losange(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur du côté
        longueurCote = point1.calculerDistance(point2);
    }

    /**
     * Vérifie si le cerf-volant est valide en vérifiant que les côtés correspondants sont égaux et que les diagonales sont perpendiculaires
     * @return true si le cerf-volant est valide, false sinon
     */
    public boolean isValidShape(){
        double side1 = sommet[0].calculerDistance(sommet[1]);
        double side2 = sommet[1].calculerDistance(sommet[2]);
        double side3 = sommet[2].calculerDistance(sommet[3]);
        double side4 = sommet[3].calculerDistance(sommet[0]);

        double angleA = InterPoint.angle(sommet[3], sommet[0], sommet[1]);
        double angleB = InterPoint.angle(sommet[0], sommet[1], sommet[2]);
        double angleC = InterPoint.angle(sommet[1], sommet[2], sommet[3]);
        double angleD = InterPoint.angle(sommet[2], sommet[3], sommet[0]);

        return side1 == side3
                && side2 == side4
                && angleA == angleC
                && angleB == angleD;
    }

    /**
     * Retourne le type du losange
     * @return La chaîne de caractères "Losange"
     */
    @Override
    public String type() {
        return "Losange";
    }

    /**
     * Retourne un descriptif du losange, avec ses sommets et ses dimensions et son type
     * @return La descriptif du losange
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes les côtés ont tous la même longueur de " + longueurCote +
                ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}
