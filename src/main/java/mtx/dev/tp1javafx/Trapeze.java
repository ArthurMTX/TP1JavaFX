package mtx.dev.tp1javafx;

/**
 * Classe quadrilatère de type trapèze
 * @see Quadrilatere
 */
public class Trapeze extends Quadrilatere {
    /**
     * Longueur de la base 1
     */
    private final double longueurBase1;

    /**
     * Longueur de la base 2
     */
    private final double longueurBase2;

    /**
     * Hauteur du trapèze
     */
    private final double hauteur;

    /**
     * Tableau des sommets du trapèze
     */
    private final InterPoint[] sommet;

    /**
     * Constructeur de la classe trapèze
     * @param point1 Le point 1
     * @param point2 Le point 2
     * @param point3 Le point 3
     * @param point4 Le point 4
     */
    public Trapeze(InterPoint point1, InterPoint point2, InterPoint point3, InterPoint point4) {
        super(point1, point2, point3, point4);

        sommet = new InterPoint[4];
        sommet[0] = point1;
        sommet[1] = point2;
        sommet[2] = point3;
        sommet[3] = point4;

        // Calculer la longueur des bases
        longueurBase1 = point1.calculerDistance(point2);
        longueurBase2 = point3.calculerDistance(point4);

        // Calculer la hauteur
        hauteur = point1.calculerDistance(point4);
    }

    /**
     * Vérifie si le trapèze est valide en vérifiant que les côtés correspondants sont égaux et que angles sont égaux
     * @return true si le trapèze est valide, false sinon
     */
    public boolean isValidShape(){
        double side2 = sommet[1].calculerDistance(sommet[2]);
        double side4 = sommet[3].calculerDistance(sommet[0]);

        double angle1 = InterPoint.angle(sommet[0], sommet[1], sommet[2]);
        double angle2 = InterPoint.angle(sommet[1], sommet[2], sommet[3]);
        double angle3 = InterPoint.angle(sommet[2], sommet[3], sommet[0]);
        double angle4 = InterPoint.angle(sommet[3], sommet[0], sommet[1]);

        return side2 == side4 && angle1 == angle4 && angle2 == angle3;
    }

    /**
     * Retourne le type du trapèze
     * @return Le type du trapèze
     */
    @Override
    public String type() {
        return "Trapèze";
    }

    /**
     * Retourne un descriptif du trapèze, avec ses sommets et ses dimensions et son type
     * @return La descriptif du trapèze
     */
    @Override
    public String toString() {
        return "Je suis un " + type().toLowerCase() + ". J'ai une hauteur de " + hauteur +
                " et une longueur de base de " + longueurBase1 + " et " + longueurBase2 + ". Ces bases sont deux côtés opposés parallèles." +
                "Mes sommets ont pour coordonnées " + coordonnees();
    }
}

