package mtx.dev.tp1javafx;

/**
 * Classe quadrilatère de type cerf-volant
 * @see Quadrilatere
 */
public class CerfVolant extends Quadrilatere {
    /**
     * Tableau des sommets du cerf-volant
     */
    private final InterPoint[] sommet;

    /**
     * Constructeur de la classe CerfVolant
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public CerfVolant(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;
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

        InterPoint diagonal1Start = sommet[0];
        InterPoint diagonal1End = sommet[2];
        InterPoint diagonal2Start = sommet[1];
        InterPoint diagonal2End = sommet[3];

        double angle1 = Math.atan2(diagonal1End.getY() - diagonal1Start.getY(), diagonal1End.getX() - diagonal1Start.getX());
        double angle2 = Math.atan2(diagonal2End.getY() - diagonal2Start.getY(), diagonal2End.getX() - diagonal2Start.getX());
        double angle = Math.abs(angle1 - angle2);

        return side1 == side4 && side2 == side3
                && angle == Math.PI/2;
    }

    /**
     * Retourne le type du cerf-volant
     * @return La chaîne de caractères "Cerf-Volant"
     */
    @Override
    public String type() {
        return "Cerf-Volant";
    }

    /**
     * Retourne un descriptif du cerf-volant, avec ses sommets et ses dimensions et son type
     * @return Le descriptif du carré
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". Une de mes une des diagonales est mon axe de symétrie. J'ai une hauteur de " + sommet[0].calculerDistance(sommet[2]) +
                " et une largeur de " + sommet[1].calculerDistance(sommet[3]) + ". Mes sommets ont pour coordonnées " + coordonnees();
    }
}

