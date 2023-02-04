package mtx.dev.tp1javafx;

/**
 * Classe quadrilatère de type carré
 * @see Quadrilatere
 */
public class Carre extends Quadrilatere {
    /**
     * Tableau des sommets du carré.
     */
    private final InterPoint[] sommet;

    /**
     * Constructeur de la classe Carre
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public Carre(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;
    }

    /**
     * Vérifie si le carré est valide en vérifiant que les côtés sont égaux et que les angles sont de 90°
     * @return true si le carré est valide, false sinon
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

        return side1 == side2 && side2 == side3 && side3 == side4
                && angleA == 90 && angleB == 90 && angleC == 90 && angleD == 90;
    }

    /**
     * Retourne le type du carré
     * @return La chaîne de caractères "Carré"
     */
    @Override
    public String type() {
        return "Carré";
    }

    /**
     * Retourne un descriptif du carré, avec ses sommets et ses dimensions et son type
     * @return Le descriptif du carré
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Mes quatre côtés ont tous la même longueur de " + sommet[0].calculerDistance(sommet[1]) +
                ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}